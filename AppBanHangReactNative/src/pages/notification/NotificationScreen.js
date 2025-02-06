import React, { useState, useEffect } from 'react';
import { 
  View, 
  Text, 
  StyleSheet, 
  FlatList, 
  TouchableOpacity,
  ActivityIndicator,
  RefreshControl 
} from 'react-native';
import { useTheme } from '../../context/ThemeContext';
import { notificationService } from '../../services/NotificationService';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Feather } from '@expo/vector-icons';

const NotificationScreen = ({ navigation }) => {
  const { theme } = useTheme();
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const fetchUserInfo = async () => {
    try {
      const id = await AsyncStorage.getItem('userId');
      setUserId(id);
      if (id) fetchNotifications(id);
    } catch (error) {
      console.error('Error getting user info:', error);
    }
  };

  const fetchNotifications = async (id) => {
    try {
      setLoading(true);
      const response = await notificationService.getNotifications(id);
      if (response.success) {
        setNotifications(response.data);
      }
    } catch (error) {
      console.error('Error fetching notifications:', error);
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  const handleMarkAsRead = async (notificationId) => {
    try {
      const response = await notificationService.markAsRead(notificationId);
      if (response.success) {
        setNotifications(notifications.map(notif => 
          notif._id === notificationId ? { ...notif, isRead: true } : notif
        ));
      }
    } catch (error) {
      console.error('Error marking notification as read:', error);
    }
  };

  const renderNotification = ({ item }) => (
    <TouchableOpacity 
      style={[
        styles.notificationItem, 
        { backgroundColor: item.isRead ? theme.backgroundColor : '#E3F2FD' }
      ]}
      onPress={() => handleMarkAsRead(item._id)}
    >
      <View style={styles.notificationContent}>
        <Text style={[styles.notificationTitle, { color: theme.textColor }]}>
          {item.title}
        </Text>
        <Text style={[styles.notificationMessage, { color: theme.textColor }]}>
          {item.message}
        </Text>
        <Text style={styles.notificationTime}>
          {new Date(item.createdAt).toLocaleString()}
        </Text>
      </View>
      {!item.isRead && <View style={styles.unreadDot} />}
    </TouchableOpacity>
  );

  return (
    <View style={[styles.container, { backgroundColor: theme.backgroundColor }]}>
      <Text style ={styles.textHeader}>Thông báo</Text>
      {loading ? (
        <ActivityIndicator size="large" color="#0000ff" />
      ) : (
        <FlatList
          data={notifications}
          renderItem={renderNotification}
          keyExtractor={item => item._id}
          refreshControl={
            <RefreshControl
              refreshing={refreshing}
              onRefresh={() => {
                setRefreshing(true);
                fetchNotifications(userId);
              }}
            />
          }
          ListEmptyComponent={() => (
            <Text style={[styles.emptyText, { color: theme.textColor }]}>
              Không có thông báo nào
            </Text>
          )}
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 40
  },
  textHeader:{
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 10,
  },
  notificationItem: {
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
    flexDirection: 'row',
    alignItems: 'center',
  },
  notificationContent: {
    flex: 1,
  },
  notificationTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  notificationMessage: {
    fontSize: 14,
    marginBottom: 4,
  },
  notificationTime: {
    fontSize: 12,
    color: '#666',
  },
  unreadDot: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#2196F3',
    marginLeft: 8,
  },
  emptyText: {
    textAlign: 'center',
    marginTop: 20,
    fontSize: 16,
  }
});

export default NotificationScreen;