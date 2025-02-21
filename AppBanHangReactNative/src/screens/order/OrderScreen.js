import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator, RefreshControl, Alert, TouchableOpacity } from 'react-native';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../../context/ThemeContext';
import { TabView, TabBar } from 'react-native-tab-view';
import { Dimensions } from 'react-native';
import { orderService } from '../../services/OrderService';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';

const OrderScreen = () => {
  const navigation = useNavigation();
  const { t } = useTranslation();
  const { theme } = useTheme();
  const [loading, setLoading] = useState(true);
  const [orders, setOrders] = useState([]);
  const [userId, setUserId] = useState(null);
  const [index, setIndex] = useState(0);
  const [refreshing, setRefreshing] = useState(false);

  const [routes] = useState([
    { key: 'pending', title: 'Chờ xác nhận' },
    { key: 'confirmed', title: 'Đã xác nhận' },
    { key: 'shipping', title: 'Đang giao' }, 
    { key: 'delivered', title: 'Đã giao' },
    { key: 'return_requested', title: 'Yêu cầu trả' },
    { key: 'returned', title: 'Đã trả hàng' },
    { key: 'cancelled', title: 'Đã hủy' }
  ]);

  const fetchUserInfo = async () => {
    try {
      const userId = await AsyncStorage.getItem('userId');
      if (userId) {
        setUserId(userId);
        fetchOrders(userId);
      } else {
        console.log('No userId found');
      }
    } catch (error) {
      console.error('Error getting user info:', error);
    }
  };

  const fetchOrders = async (id) => {
    if (!id) {
      console.log('No user ID provided');
      return;
    }
    
    try {
      setLoading(true);
      const response = await orderService.getUserOrders(id);
      if (response.status === 200) {
        const sortedOrders = response.data.sort((a, b) => 
          new Date(b.createdAt) - new Date(a.createdAt)
        );
        setOrders(sortedOrders);
      }
    } catch (error) {
      console.error('Error fetching orders:', error);
      Alert.alert('Lỗi', 'Không thể tải danh sách đơn hàng');
    } finally {
      setLoading(false);
    }
  };

  const onRefresh = async () => {
    if (!userId) return;
    setRefreshing(true);
    await fetchOrders(userId);
    setRefreshing(false);
  };

  useEffect(() => {
    fetchUserInfo();
  }, []);

  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      if (userId) {
        fetchOrders(userId);
      }
    });

    return unsubscribe;
  }, [navigation, userId]);

  const getStatusColor = (status) => {
    switch (status) {
      case 'pending':
        return '#FFA500';
      case 'confirmed':
        return '#2196F3';
      case 'shipping': 
        return '#9C27B0';
      case 'delivered':
        return '#4CAF50';
      case 'cancelled':
        return '#F44336';
      default:
        return '#000';
    }
  };

  const getStatusText = (status) => {
    switch (status) {
      case 'pending':
        return 'Chờ xác nhận';
      case 'confirmed':
        return 'Đã xác nhận';
      case 'shipping':
        return 'Đang giao';
      case 'delivered':
        return 'Đã giao';
      case 'cancelled':
        return 'Đã hủy';
      default:
        return status;
    }
  };

  const handleRequestReturn = async (orderId) => {
    Alert.alert(
      'Yêu cầu trả hàng',
      'Bạn có chắc chắn muốn yêu cầu trả hàng?',
      [
        { text: 'Hủy', style: 'cancel' },
        {
          text: 'Xác nhận',
          onPress: async () => {
            try {
              const response = await orderService.updateOrderStatus(orderId, 'return_requested');
              
              if (response.status === 200) {
                Alert.alert('Thành công', 'Đã gửi yêu cầu trả hàng');
                fetchOrders(userId);
              } else {
                Alert.alert('Lỗi', 'Không thể gửi yêu cầu trả hàng');
              }
            } catch (error) {
              console.error('Error requesting return:', error);
              Alert.alert('Lỗi', 'Không thể xử lý yêu cầu trả hàng');
            }
          }
        }
      ]
    );
  };

  const renderOrderItem = ({ item }) => (
    <TouchableOpacity 
      onPress={() => navigation.navigate('OrderDetail', { order: item })}
      style={[styles.orderCard, { backgroundColor: theme.backgroundColor }]}
    >
      <View style={styles.orderHeader}>
        <View>
          <Text style={[styles.orderId, { color: theme.textColor }]}>
            {t('order.details.orderId', { id: item._id.slice(-6) })}
          </Text>
          <Text style={[styles.orderDate, { color: theme.textColor }]}>
            {new Date(item.createdAt).toLocaleString('vi-VN', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit',
              hour: '2-digit',
              minute: '2-digit'
            })}
          </Text>
        </View>
        <View style={[styles.statusBadge, { backgroundColor: getStatusColor(item.status) }]}>
          <Text style={styles.statusText}>{getStatusText(item.status)}</Text>
        </View>
      </View>

      <View style={styles.orderInfo}>
        <Text style={[styles.orderTotal, { color: theme.textColor }]}>
          {t('order.details.total', { amount: item.finalTotal.toLocaleString('vi-VN') })}
        </Text>
        <Text style={[styles.orderAddress, { color: theme.textColor }]}>
          Địa chỉ: {item.shippingAddress}
        </Text>
        <Text style={[styles.paymentMethod, { color: theme.textColor }]}>
          Phương thức: {item.paymentMethod === 'wallet' ? 'Ví MoMo' : 'Tiền mặt'}
        </Text>
      </View>

      {/* Only show return request button for delivered orders that haven't been requested for return */}
      {item.status === 'delivered' && (
        <View style={styles.actionButtons}>
          <TouchableOpacity
            style={[styles.actionButton, styles.returnButton]}
            onPress={() => handleRequestReturn(item._id)}
          >
            <Text style={styles.buttonText}>Yêu cầu trả hàng</Text>
          </TouchableOpacity>
        </View>
      )}
    </TouchableOpacity>
  );

  // Add pull-to-refresh functionality
  const renderScene = ({ route }) => {
    if (loading) {
      return (
        <View style={styles.loadingContainer}>
          <ActivityIndicator size="large" color="#0000ff" />
        </View>
      );
    }

    const filteredOrders = orders.filter(order => order.status === route.key);
    
    return (
      <FlatList
        data={filteredOrders}
        renderItem={renderOrderItem}
        keyExtractor={(item) => item._id}
        contentContainerStyle={styles.listContainer}
        ListEmptyComponent={() => (
          <Text style={[styles.emptyText, { color: theme.textColor }]}>
            Không có đơn hàng nào
          </Text>
        )}
        refreshControl={
          <RefreshControl
            refreshing={refreshing}
            onRefresh={onRefresh}
            colors={['#2196F3']}
            tintColor={theme.textColor}
          />
        }
      />
    );
  };

  const renderTabBar = props => (
    <TabBar
      {...props}
      scrollEnabled
      indicatorStyle={{ backgroundColor: '#2196F3' }}
      style={{ backgroundColor: theme.backgroundColor }}
      labelStyle={{ fontSize: 12 }}
      activeColor="#2196F3"
      inactiveColor={theme.textColor}
    />
  );

  return (
    <TabView
      navigationState={{ index, routes }}
      renderScene={renderScene}
      onIndexChange={setIndex}
      initialLayout={{ width: Dimensions.get('window').width }}
      renderTabBar={renderTabBar}
    />
  );
};

// Helper function to create notification
const createNotification = async (userId, title, message, type = 'order') => {
  try {
    await notificationService.createNotification({
      userId,
      title,
      message,
      type,
      isRead: false,
      data: {}
    });
  } catch (error) {
    console.error('Error creating notification:', error);
  }
};

const styles = StyleSheet.create({
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  listContainer: {
    padding: 16,
  },
  orderCard: {
    marginBottom: 16,
    padding: 16,
    borderRadius: 8,
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
  },
  orderHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 12,
  },
  orderId: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  orderDate: {
    fontSize: 12,
    color: '#666',
  },
  statusBadge: {
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 12,
  },
  statusText: {
    color: 'white',
    fontSize: 12,
    fontWeight: 'bold',
  },
  orderInfo: {
    marginTop: 8,
  },
  orderTotal: {
    fontSize: 14,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  orderAddress: {
    fontSize: 13,
    marginBottom: 4,
  },
  paymentMethod: {
    fontSize: 13,
  },
  emptyText: {
    textAlign: 'center',
    marginTop: 20,
  },
  actionButtons: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    marginTop: 12,
  },
  actionButton: {
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 4,
    marginLeft: 8,
  },
  returnButton: {
    backgroundColor: '#FF9800',
  },
  buttonText: {
    color: 'white',
    fontSize: 12,
    fontWeight: 'bold',
  },
});

export default OrderScreen;