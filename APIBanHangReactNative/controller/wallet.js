const express = require('express');
const router = express.Router();
const modelWallet = require('../models/model_e-wallet');

module.exports = {
    // Add new wallet
    add: async (req, res) => {
        try {
            const model = new modelWallet(req.body);
            const result = await model.save();
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Thêm ví thành công",
                    "data": result
                });
            } else {
                res.json({
                    "status": 400,
                    "message": "Thêm ví thất bại",
                    "data": []
                });
            }
        } catch (err) {
            console.error("Error while saving wallet:", err);
            res.status(500).send({ error: 'An error occurred while saving data' });
        }
    },

    // Get all wallets
    list: async (req, res) => {
        try {
            const result = await modelWallet.find();
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Danh sách ví",
                    "data": result
                });
            } else {
                res.json({
                    "status": 400,
                    "message": "Lỗi lấy danh sách",
                    "data": []
                });
            }
        } catch (err) {
            console.error("Error while fetching wallets:", err);
            res.status(500).send({ error: 'An error occurred while fetching data' });
        }
    },

    // Get wallet by ID
    getbyid: async (req, res) => {
        try {
            const result = await modelWallet.findById(req.params.id);
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Tìm thấy ví",
                    "data": result
                });
            } else {
                res.json({
                    "status": 400,
                    "message": "Không tìm thấy ví",
                    "data": []
                });
            }
        } catch (error) {
            if (error.name === 'CastError') {
                res.status(404).send('Invalid ID format');
            } else {
                res.status(500).send('Server error');
            }
        }
    },

    // Update wallet
    edit: async (req, res) => {
        try {
            const result = await modelWallet.findByIdAndUpdate(
                req.params.id,
                req.body,
                { new: true }
            );
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Cập nhật ví thành công",
                    "data": result
                });
            } else {
                res.json({
                    "status": 400,
                    "message": "Cập nhật ví thất bại",
                    "data": []
                });
            }
        } catch (error) {
            console.error("Error updating wallet:", error);
            res.status(500).send({ error: 'An error occurred while updating' });
        }
    },

    // Delete wallet
    delete: async (req, res) => {
        try {
            const result = await modelWallet.findByIdAndDelete(req.params.id);
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Xóa ví thành công",
                    "data": result
                });
            } else {
                res.json({
                    "status": 400,
                    "message": "Xóa ví thất bại",
                    "data": []
                });
            }
        } catch (error) {
            console.error("Error deleting wallet:", error);
            res.status(500).send({ error: 'An error occurred while deleting' });
        }
    },

    // Search wallets
    search: async (req, res) => {
        try {
            const key = req.query.key;
            const result = await modelWallet.find({
                $or: [
                    { 'userId': { $regex: key, $options: 'i' } },
                    { 'transactions.description': { $regex: key, $options: 'i' } }
                ]
            });
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Kết quả tìm kiếm",
                    "data": result
                });
            } else {
                res.json({
                    "status": 400,
                    "message": "Không tìm thấy kết quả",
                    "data": []
                });
            }
        } catch (err) {
            console.error("Error searching wallets:", err);
            res.status(500).send({ error: 'An error occurred while searching' });
        }
    }
};