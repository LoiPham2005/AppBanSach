import { API_URL } from './URL_API';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const authService = {
  login: async (email, password) => {
    try {
      const response = await fetch(`${API_URL}/api/users/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (response.ok) {
        if (data.user && data.user._id) {
          // Lưu đầy đủ thông tin user
          const userInfo = {
            _id: data.user._id,
            name: data.user.username,
            username: data.user.username,
            email: data.user.email,
            avatar: data.user.avatar,
            role: data.user.role,
            sex: data.user.sex || '', // Add default value
            phone: data.user.phone || '', // Add default value 
            birth_date: data.user.birth_date || new Date().toISOString() // Add default value
          };

          console.log('Saving user info:', userInfo); // Debug log

          await AsyncStorage.setItem('userInfo', JSON.stringify(userInfo));
          await AsyncStorage.setItem('userId', data.user._id);
          await AsyncStorage.setItem('userRole', data.user.role);
          await AsyncStorage.setItem('accessToken', data.accessToken); // Thêm dòng này
          await AsyncStorage.setItem('refreshToken', data.refreshToken); // Thêm dòng này

          return {
            success: true,
            data: data
          };
        }
      }
      return {
        success: false,
        message: data.message || 'Login failed'
      };
    } catch (error) {
      console.error('Login error:', error);
      return {
        success: false,
        message: error.message
      };
    }
  },

  register: async (userData) => {
    try {
      const response = await fetch(`${API_URL}/api/users/reg`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      const data = await response.json();

      if (response.ok) {
        return {
          success: true,
          data: data
        };
      } else {
        return {
          success: false,
          message: data.message || 'Registration failed'
        };
      }
    } catch (error) {
      console.error('Registration error:', error);
      return {
        success: false,
        message: error.message
      };
    }
  },

  logout: async () => {
    try {
      const token = await AsyncStorage.getItem('accessToken');
      const response = await fetch(`${API_URL}/api/users/logout`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      await AsyncStorage.removeItem('accessToken');
      await AsyncStorage.removeItem('refreshToken');

      return response.ok;
    } catch (error) {
      console.error('Logout error:', error);
      return false;
    }
  }
};