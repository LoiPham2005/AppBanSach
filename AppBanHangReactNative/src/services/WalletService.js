import { API_URL } from './URL_API';

export const walletService = {
  // Lấy danh sách ví
  getWallet: async () => {
    try {
      const response = await fetch(`${API_URL}/api/wallet/list`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      if (data.status === 200) {
        return {
          success: true,
          data: data.data
        };
      } else {
        return {
          success: false,
          message: data.message
        };
      }
    } catch (error) {
      console.error("Error fetching wallet:", error);
      return {
        success: false,
        message: error.message
      };
    }
  },

  // Thêm ví mới
  addWallet: async (walletData) => {
    try {
      const response = await fetch(`${API_URL}/api/wallet/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(walletData)
      });
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error adding wallet:", error);
      throw error;
    }
  },

  // Cập nhật ví
  updateWallet: async (userId, walletData) => {
    try {
      const response = await fetch(`${API_URL}/api/wallet/list`);
      const data = await response.json();
      let wallet = data.data.find(w => w.userId === userId);
      
      if (!wallet) {
        // Tạo ví mới nếu chưa có
        const createResponse = await fetch(`${API_URL}/api/wallet/add`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            userId: userId,
            balance: walletData.amount,
            transactions: [{
              transactionId: Date.now().toString(), // Sử dụng timestamp làm ID
              type: walletData.type,
              amount: walletData.amount,
              description: walletData.description
            }]
          })
        });
        return createResponse.json();
      } else {
        // Cập nhật ví hiện có
        const updateResponse = await fetch(`${API_URL}/api/wallet/edit/${wallet._id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            balance: wallet.balance + walletData.amount,
            $push: {
              transactions: {
                transactionId: Date.now().toString(), // Sử dụng timestamp làm ID
                type: walletData.type,
                amount: walletData.amount,
                description: walletData.description
              }
            }
          })
        });
        return updateResponse.json();
      }
    } catch (error) {
      console.error("Error updating wallet:", error);
      throw error;
    }
  }
};