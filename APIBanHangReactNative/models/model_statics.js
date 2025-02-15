const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const StatisticsSchema = new Schema({
    date: {
        type: Date,
        required: true,
        default: Date.now
    },
    totalRevenue: {
        type: Number,
        required: true,
        default: 0
    },
    totalOrders: {
        type: Number,
        required: true,
        default: 0
    },
    totalProductsSold: {
        type: Number,
        required: true,
        default: 0
    },
    revenueByCategory: [{
        categoryId: {
            type: Schema.Types.ObjectId,
            ref: 'category'
        },
        revenue: Number
    }],
    topSellingProducts: [{
        productId: {
            type: Schema.Types.ObjectId,
            ref: 'products'
        },
        quantity: Number,
        revenue: Number
    }],
    paymentMethodStats: {
        cod: {
            count: Number,
            amount: Number
        },
        wallet: {
            count: Number,
            amount: Number
        }
    }
}, { timestamps: true });

module.exports = mongoose.model('Statistics', StatisticsSchema);