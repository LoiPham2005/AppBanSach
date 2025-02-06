import { API_URL } from './URL_API';

export const userService = {
    updateProfile: async (userId, userData) => {
        try {
            const response = await fetch(`${API_URL}/api/users/edit/${userId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });
    
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
    
            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Error updating profile:", error);
            throw error;
        }
    },

  uploadMedia: async (formData) => {
    try {
      const response = await fetch(`${API_URL}/api/upload/media`, {
        method: 'POST',
        body: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      });

      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error uploading media:", error);
      throw error;
    }
  }
};