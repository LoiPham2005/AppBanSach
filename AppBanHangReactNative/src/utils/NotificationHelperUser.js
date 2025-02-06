import * as Notifications from 'expo-notifications';
import * as Device from 'expo-device';
import AsyncStorage from '@react-native-async-storage/async-storage';

// Cấu hình notification handler cho user
Notifications.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: true,
    shouldSetBadge: true,
    priority: Notifications.AndroidNotificationPriority.MAX
  })
});

export const NotificationHelperUser = {
  registerForPushNotificationsAsync: async () => {
    let token;
    if (Device.isDevice) {
      const { status: existingStatus } = await Notifications.getPermissionsAsync();
      let finalStatus = existingStatus;
      if (existingStatus !== 'granted') {
        const { status } = await Notifications.requestPermissionsAsync();
        finalStatus = status;
      }
      if (finalStatus !== 'granted') {
        alert('Không thể nhận thông báo vì quyền thông báo bị từ chối!');
        return;
      }
      token = (await Notifications.getExpoPushTokenAsync()).data;
    } else {
      alert('Phải sử dụng thiết bị vật lý để nhận thông báo!');
    }
    return token;
  },
  
  showLocalNotificationForUser: async (title, message, data, targetUserId) => {
    try {
      const currentUserId = await AsyncStorage.getItem('userId');
      const userRole = await AsyncStorage.getItem('userRole');
      
      console.log('Current User:', currentUserId, 'Target User:', targetUserId, 'Role:', userRole); // Debug log

      if (userRole === 'user' && currentUserId === targetUserId) {
        console.log('Showing notification for user:', targetUserId); // Debug log
        
        await Notifications.scheduleNotificationAsync({
          content: {
            title,
            body: message,
            data: data || {},
            sound: true,
            priority: Notifications.AndroidNotificationPriority.MAX,
            channelId: 'orders'
          },
          trigger: null
        });
      }
    } catch (error) {
      console.error('Error in showLocalNotificationForUser:', error);
    }
  }
};