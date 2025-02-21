const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const OrderSchema = new Schema({
    id_user: {
        type: Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    items: [{
        id_product: {
            type: Schema.Types.ObjectId,
            ref: 'products',
            required: true
        },
        purchaseQuantity: {
            type: Number,
            required: true
        },
        price: {
            type: Number,
            required: true
        }
    }],
    totalPrice: {
        type: Number,
        required: true
    },
    shippingFee: {
        type: Number,
        required: true
    },
    discount: {
        type: Number,
        default: 0
    },
    finalTotal: {
        type: Number,
        required: true
    },
    paymentMethod: {
        type: String,
        enum: ['cod', 'wallet'],
        required: true
    },
    shippingAddress: {
        type: String,
        required: true
    },
    status: {
        type: String,
        enum: ['pending', 'confirmed', 'shipping', 'delivered', 'cancelled', 'return_requested', 'returned'],
        // enum: [
        //     'pending',           // Chờ xác nhận
        //     'confirmed',         // Đã xác nhận
        //     'shipping',          // Đang giao hàng
        //     'delivered',         // Đã giao hàng
        //     'cancelled',         // Đã hủy
        //     'return_requested',  // Yêu cầu trả hàng
        //     'return_approved',   // Đã chấp nhận trả hàng
        //     'return_rejected',   // Từ chối trả hàng
        //     'returned'           // Đã hoàn trả
        // ],
        default: 'pending'
    },
    voucherId: {
        type: Schema.Types.ObjectId,
        ref: 'Voucher',
        required: false
    }

    // Thêm liên kết đến đánh giá
    // reviews: [{
    //     type: Schema.Types.ObjectId,
    //     ref: 'Review'
    // }],
    // // Thêm liên kết đến yêu cầu trả hàng
    // returnRequest: {
    //     type: Schema.Types.ObjectId,
    //     ref: 'ReturnRequest'
    // }
}, { timestamps: true }); // Thêm timestamps

// Middleware để populate reviews và returnRequest khi cần
// OrderSchema.pre('find', function(next) {
//     this.populate('reviews');
//     this.populate('returnRequest');
//     next();
// });

module.exports = mongoose.model('Order', OrderSchema);