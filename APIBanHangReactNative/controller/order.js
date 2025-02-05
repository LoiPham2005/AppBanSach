const express = require('express');
const router = express.Router();
const modelOrder = require('../models/model_order');
const modelWallet = require('../models/model_e-wallet');
const modelProduct = require('../models/model_products');

module.exports = {
    // Create new order
    add: async (req, res) => {
        try {
            // Validate payment method and process payment
            if (req.body.paymentMethod === 'wallet') {
                const wallet = await modelWallet.findOne({ userId: req.body.id_user });
                if (!wallet || wallet.balance < req.body.finalTotal) {
                    return res.status(400).json({
                        status: 400,
                        message: "Insufficient wallet balance"
                    });
                }

                // Process wallet payment
                wallet.balance -= req.body.finalTotal;
                wallet.transactions.push({
                    transactionId: new Date().getTime().toString(), // Thêm transactionId
                    type: 'debit',
                    amount: req.body.finalTotal,
                    description: 'Order payment'
                });
                await wallet.save();
            }

            // Validate order data
            if (!req.body.items || !Array.isArray(req.body.items)) {
                return res.status(400).json({
                    status: 400,
                    message: "Invalid items data"
                });
            }

            // Create order
            const order = new modelOrder(req.body);
            const result = await order.save();

            // Update product quantities
            for (const item of req.body.items) {
                await modelProduct.findByIdAndUpdate(
                    item.id_product,
                    { $inc: { stock_quantity: -item.purchaseQuantity } }
                );
            }

            res.json({
                status: 200,
                message: "Order created successfully",
                data: result
            });
        } catch (err) {
            console.error("Error creating order:", err);
            res.status(500).json({
                status: 500,
                message: "Error creating order",
                error: err.message
            });
        }
    },

    // Get all orders
    list: async (req, res) => {
        try {
            const result = await modelOrder.find()
                .populate('id_user', 'username email')
                .populate('items.id_product', 'title price');

            res.json({
                status: 200,
                message: "Orders retrieved successfully",
                data: result
            });
        } catch (err) {
            console.error("Error fetching orders:", err);
            res.status(500).json({
                status: 500,
                message: "Error fetching orders",
                error: err.message
            });
        }
    },

    // Get order by ID
    getbyid: async (req, res) => {
        try {
            const result = await modelOrder.findById(req.params.id)
                .populate('id_user', 'username email')
                .populate('items.id_product', 'title price');

            if (!result) {
                return res.status(404).json({
                    status: 404,
                    message: "Order not found"
                });
            }

            res.json({
                status: 200,
                message: "Order found",
                data: result
            });
        } catch (err) {
            console.error("Error fetching order:", err);
            res.status(500).json({
                status: 500,
                message: "Error fetching order",
                error: err.message
            });
        }
    },

    // Update order status
    // updateStatus: async (req, res) => {
    //     try {
    //         const { status } = req.body;
    //         const result = await modelOrder.findByIdAndUpdate(
    //             req.params.id,
    //             { status },
    //             { new: true }
    //         );

    //         if (!result) {
    //             return res.status(404).json({
    //                 status: 404,
    //                 message: "Order not found"
    //             });
    //         }

    //         res.json({
    //             status: 200,
    //             message: "Order status updated successfully",
    //             data: result
    //         });
    //     } catch (err) {
    //         console.error("Error updating order status:", err);
    //         res.status(500).json({
    //             status: 500,
    //             message: "Error updating order status",
    //             error: err.message
    //         });
    //     }
    // },

        // Trong file controller/order.js
        updateStatus: async (req, res) => {
            try {
              const { id } = req.params;
              const { status, returnDate } = req.body;
          
              const order = await modelOrder.findByIdAndUpdate(
                id,
                { 
                  status,
                  returnDate: returnDate || new Date()
                },
                { new: true }
              );
          
              if (!order) {
                return res.status(404).json({
                  status: 404,
                  message: "Không tìm thấy đơn hàng"
                });
              }
          
              res.json({
                status: 200,
                message: "Cập nhật trạng thái thành công",
                data: order
              });
          
            } catch (error) {
              console.error("Error updating order status:", error);
              res.status(500).json({
                status: 500,
                message: "Lỗi khi cập nhật trạng thái đơn hàng"
              });
            }
          },

    // Get user's orders
    getUserOrders: async (req, res) => {
        try {
            const result = await modelOrder.find({ id_user: req.params.userId })
                .populate('id_user', 'username email')
                .populate('items.id_product', 'title price')
                .sort({ createdAt: -1 });

            res.json({
                status: 200,
                message: "User orders retrieved successfully",
                data: result
            });
        } catch (err) {
            console.error("Error fetching user orders:", err);
            res.status(500).json({
                status: 500,
                message: "Error fetching user orders",
                error: err.message
            });
        }
    }
};