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
        default: 'pending' 
    },
    voucherId: { 
        type: Schema.Types.ObjectId, 
        ref: 'Voucher',
        required: false 
    }
}, { timestamps: true }); // Thêm timestamps

module.exports = mongoose.model('Order', OrderSchema);