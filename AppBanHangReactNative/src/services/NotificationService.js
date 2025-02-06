import { API_URL } from './URL_API';
import io from 'socket.io-client';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { NotificationHelper } from '../utils/NotificationHelper';

let socket = null;

export const notificationService = {
  initializeSocket: async () => {
    if (!socket) {
      socket = io(API_URL);
      const userId = await AsyncStorage.getItem('userId');
      
      if (userId) {
        socket.emit('join notification room', userId);
      }

      socket.on('notification received', async (notification) => {
        // Chỉ hiển thị thông báo nền nếu là thông báo cho admin
        if (notification.data && notification.data.isAdmin) {
          await NotificationHelper.showLocalNotification(
            notification.title,
            notification.message,
            notification.data
          );
        }
      });
    }
    return socket;
  },

  getSocket: () => {
    return socket;
  },

  createNotification: async (notificationData) => {
    try {
      if (!notificationData.userId) {
        throw new Error('userId is required');
      }

      if (!socket) {
        await notificationService.initializeSocket();
      }

      socket.emit('new notification', notificationData);
      
      return {
        success: true,
        message: 'Notification sent'
      };
    } catch (error) {
      console.error("Error creating notification:", error);
      throw error;
    }
  },

  getNotifications: async (userId) => {
    try {
      const response = await fetch(`${API_URL}/api/notifications/user/${userId}`);
      const data = await response.json();
      return {
        success: data.status === 200,
        data: data.data || [],
        message: data.message
      };
    } catch (error) {
      console.error("Error fetching notifications:", error);
      return {
        success: false,
        data: [],
        message: error.message
      };
    }
  },

  markAsRead: async (notificationId) => {
    try {
      const response = await fetch(`${API_URL}/api/notifications/read/${notificationId}`, {
        method: 'PUT'
      });
      const data = await response.json();
      return {
        success: data.status === 200,
        data: data.data,
        message: data.message
      };
    } catch (error) {
      console.error("Error marking notification as read:", error);
      return {
        success: false,
        message: error.message
      };
    }
  }
};