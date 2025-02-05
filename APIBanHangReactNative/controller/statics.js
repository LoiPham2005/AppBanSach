const express = require('express');
const router = express.Router();
const modelStatistics = require('../models/model_statics');
const modelOrder = require('../models/model_order');
const modelProducts = require('../models/model_products');

module.exports = {
    generateDailyReport: async (req, res) => {
        try {
            const { startDate, endDate } = req.query;
            if (!startDate || !endDate) {
                return res.status(400).json({
                    status: 400,
                    message: "Missing startDate or endDate"
                });
            }
            
            const start = new Date(startDate);
            const end = new Date(endDate);
            
            // Populate items.id_product với đầy đủ thông tin
            const orders = await modelOrder.find({
                createdAt: {
                    $gte: start,
                    $lte: end
                }
            }).populate({
                path: 'items.id_product',
                select: '_id title price publishing_house' // Thêm các trường cần thiết
            });

            let totalRevenue = 0;
            let totalProductsSold = 0;
            const productSales = {};
            const paymentStats = {
                cod: { count: 0, amount: 0 },
                wallet: { count: 0, amount: 0 }
            };
            const dailyData = {};

            orders.forEach(order => {
                const orderDate = order.createdAt.toISOString().split('T')[0];
                
                // Cập nhật doanh thu theo ngày
                if (!dailyData[orderDate]) {
                    dailyData[orderDate] = 0;
                }
                dailyData[orderDate] += order.finalTotal;

                // Cập nhật tổng doanh thu
                totalRevenue += order.finalTotal;
                
                // Cập nhật thống kê thanh toán
                if (order.paymentMethod === 'cod') {
                    paymentStats.cod.count++;
                    paymentStats.cod.amount += order.finalTotal;
                } else {
                    paymentStats.wallet.count++;
                    paymentStats.wallet.amount += order.finalTotal;
                }

                // Xử lý từng sản phẩm trong đơn hàng
                if (order.items && Array.isArray(order.items)) {
                    order.items.forEach(item => {
                        if (item.id_product) {
                            totalProductsSold += item.purchaseQuantity;
                            
                            const productId = item.id_product._id;
                            
                            if (!productSales[productId]) {
                                productSales[productId] = {
                                    productId: productId,
                                    name: item.id_product.title,
                                    author: item.id_product.publishing_house,
                                    quantity: 0,
                                    revenue: 0
                                };
                            }
                            productSales[productId].quantity += item.purchaseQuantity;
                            productSales[productId].revenue += item.price * item.purchaseQuantity;
                        }
                    });
                }
            });

            // Chuyển đổi dữ liệu theo ngày thành mảng và sắp xếp
            const revenueHistory = Object.entries(dailyData)
                .map(([date, amount]) => ({ date, amount }))
                .sort((a, b) => new Date(a.date) - new Date(b.date));

            // Lấy top sản phẩm bán chạy
            const topSellingProducts = Object.values(productSales)
                .sort((a, b) => b.quantity - a.quantity)
                .slice(0, 10);

            res.json({
                status: 200,
                message: "Statistics retrieved successfully",
                data: {
                    totalRevenue,
                    totalOrders: orders.length,
                    totalProductsSold,
                    paymentMethodStats: paymentStats,
                    topSellingProducts,
                    revenueHistory,
                    startDate: start,
                    endDate: end
                }
            });

        } catch (error) {
            console.error("Error generating statistics:", error);
            res.status(500).json({
                status: 500,
                message: "Error generating statistics",
                error: error.message
            });
        }
    }
};