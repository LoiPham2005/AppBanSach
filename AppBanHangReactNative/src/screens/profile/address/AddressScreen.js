import { View, Text, StyleSheet, TouchableOpacity, FlatList, Alert, Modal } from 'react-native'
import React, { useState, useEffect } from 'react'
import { Feather } from '@expo/vector-icons'
<<<<<<< HEAD
import { useNavigation, useRoute } from '@react-navigation/native'; // Thêm useRoute
=======
import { useNavigation, useRoute } from '@react-navigation/native'
>>>>>>> origin/dev
import { useTheme } from '../../../context/ThemeContext'
import { addressService } from '../../../services/AddressService'
import LoadingOverlay from '../../../components/LoadingOverlay'
import EditAddress from './edit_address/EditAddress'
<<<<<<< HEAD

export default function AddressScreen() {
=======
import { useTranslation } from 'react-i18next'
// Thêm import AsyncStorage
import AsyncStorage from '@react-native-async-storage/async-storage'

export default function AddressScreen() {
  const { t } = useTranslation();
>>>>>>> origin/dev
  const navigation = useNavigation()
  const route = useRoute(); // Thêm dòng này
  const { theme } = useTheme()
  const [addresses, setAddresses] = useState([])
  const [loading, setLoading] = useState(false)
<<<<<<< HEAD
    const [selectedItem, setSelectedItem] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);

  useEffect(() => {
    fetchAddresses()
    
=======
  const [selectedItem, setSelectedItem] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const getUserId = async () => {
      try {
        const id = await AsyncStorage.getItem('userId');
        setUserId(id);
      } catch (error) {
        console.error('Error getting userId:', error);
      }
    };
    getUserId();
  }, []);

  useEffect(() => {
    if (userId) {
      fetchAddresses();
    }
  }, [userId]);

  useEffect(() => {
    fetchAddresses()

>>>>>>> origin/dev
    // Refresh list when returning from AddEditAddress
    const unsubscribe = navigation.addListener('focus', () => {
      fetchAddresses()
    })
<<<<<<< HEAD
    
    return unsubscribe
  }, [])

  const fetchAddresses = async () => {
    try {
      setLoading(true)
      const response = await addressService.getAddress()
=======

    return unsubscribe
  }, [])

  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      // Kiểm tra params có shouldRefresh không
      const shouldRefresh = route.params?.shouldRefresh;
      if (shouldRefresh && userId) {
        fetchAddresses();
        // Reset param
        navigation.setParams({ shouldRefresh: false });
      }
    });

    return unsubscribe;
  }, [navigation, route.params?.shouldRefresh, userId]);

  const fetchAddresses = async () => {
    if (!userId) return;

    try {
      setLoading(true)
      const response = await addressService.getAddress(userId)
>>>>>>> origin/dev
      if (response.success) {
        setAddresses(response.data)
      }
    } catch (error) {
      console.error("Error fetching addresses:", error)
    } finally {
      setLoading(false)
    }
  }

<<<<<<< HEAD
    const renderItem = ({ item }) => (
=======
  const renderItem = ({ item }) => (
>>>>>>> origin/dev
    <View style={[styles.addressCard, { backgroundColor: theme.backgroundColor }]}>
      <View style={styles.addressContent}>
        <View style={styles.addressInfo}>
          <Text style={[styles.name, { color: theme.textColor }]}>{item.fullName}</Text>
          <Text style={[styles.address, { color: theme.textColor }]}>
            {`${item.receivingAddress}, ${item.commune}, ${item.district}, ${item.province}`}
          </Text>
          <Text style={[styles.phone, { color: theme.textColor }]}>{item.phone}</Text>
<<<<<<< HEAD
          <TouchableOpacity 
=======
          <TouchableOpacity
>>>>>>> origin/dev
            style={styles.useButton}
            onPress={() => handleUseAddress(item)}
          >
            <Text style={styles.useButtonText}>Sử dụng</Text>
          </TouchableOpacity>
        </View>
<<<<<<< HEAD
        <TouchableOpacity 
        onPress={() => handleMorePress(item)}>
=======
        <TouchableOpacity
          onPress={() => handleMorePress(item)}>
>>>>>>> origin/dev
          <Feather name="more-vertical" size={24} color={theme.textColor} />
        </TouchableOpacity>
      </View>
      {item.chooseDefault && (
        <View style={styles.defaultBadge}>
          <Text style={styles.defaultText}>Mặc định</Text>
        </View>
      )}
    </View>
  );

  const handleMorePress = (item) => {
    setSelectedItem(item);
    setIsModalVisible(true);
  };

  const handleSetDefault = () => {
    setIsModalVisible(false);
    Alert.alert(
<<<<<<< HEAD
      'Xác nhận',
      'Bạn có muốn đặt địa chỉ này làm mặc định không?',
      [
        {
          text: 'Hủy',
          style: 'cancel'
        },
        {
          text: 'Đồng ý',
=======
      t('common.confirm'),
      t('address.setDefault.confirm'),
      [
        {
          text: t('common.cancel'),
          style: 'cancel'
        },
        {
          text: t('common.confirm'),
>>>>>>> origin/dev
          onPress: async () => {
            try {
              setLoading(true);
              const updatedAddress = {
                ...selectedItem,
                chooseDefault: true
              };
              const response = await addressService.updateAddress(selectedItem._id, updatedAddress);
              if (response.status === 200) {
                fetchAddresses(); // Refresh list
              }
            } catch (error) {
              Alert.alert('Lỗi', 'Không thể cập nhật địa chỉ mặc định');
            } finally {
              setLoading(false);
            }
          }
        }
      ]
    );
  };

  const handleEdit = () => {
    setIsModalVisible(false);
    navigation.navigate('EditAddress', { address: selectedItem, isEditing: true });
  };

  const handleDelete = () => {
    setIsModalVisible(false);
    Alert.alert(
<<<<<<< HEAD
      'Xác nhận xóa',
      'Bạn có chắc chắn muốn xóa địa chỉ này không?',
      [
        {
          text: 'Hủy',
          style: 'cancel'
        },
        {
          text: 'Xóa',
=======
      t('address.delete.title'),
      t('address.delete.message'),
      [
        {
          text: t('common.cancel'),
          style: 'cancel'
        },
        {
          text: t('address.delete.confirm'),
>>>>>>> origin/dev
          style: 'destructive',
          onPress: async () => {
            try {
              setLoading(true);
              const response = await addressService.deleteAddress(selectedItem._id);
              if (response.status === 200) {
                Alert.alert('Thành công', 'Xóa địa chỉ thành công');
                fetchAddresses(); // Refresh list
              }
            } catch (error) {
              console.error('Delete error:', error);
              Alert.alert('Lỗi', 'Không thể xóa địa chỉ');
            } finally {
              setLoading(false);
            }
          }
        }
      ]
    );
  };

  const handleUseAddress = (address) => {
    const fullAddress = `${address.fullName}, ${address.phone}, ${address.receivingAddress}, ${address.commune}, ${address.district}, ${address.province}`;
    const returnParams = route.params?.returnParams || {};
<<<<<<< HEAD
    
    navigation.navigate('OrderPayment', { 
=======

    navigation.navigate('OrderPayment', {
>>>>>>> origin/dev
      selectedAddress: fullAddress,
      // Trả về các params đã lưu
      ...returnParams
    });
  };

  return (
    <View style={[styles.container, { backgroundColor: theme.backgroundColor }]}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Feather name="arrow-left" size={24} color={theme.textColor} />
        </TouchableOpacity>
<<<<<<< HEAD
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>Địa chỉ</Text>
=======
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>{t('address.title')}</Text>
>>>>>>> origin/dev
        <TouchableOpacity onPress={() => navigation.navigate('AddAddress')}>
          <Feather name="plus" size={24} color={theme.textColor} />
        </TouchableOpacity>
      </View>

      {/* Address List */}
      {loading ? (
        <LoadingOverlay />
      ) : (
        <FlatList
          data={addresses}
          renderItem={renderItem}
          keyExtractor={(item) => item._id}
          contentContainerStyle={styles.listContainer}
        />
      )}

      {/* Modal Options */}
      <Modal
        visible={isModalVisible}
        transparent={true}
        animationType="fade"
        onRequestClose={() => setIsModalVisible(false)}
      >
        <TouchableOpacity
          style={styles.modalOverlay}
          activeOpacity={1}
          onPress={() => setIsModalVisible(false)}
        >
          <View style={styles.modalContent}>
            <TouchableOpacity
              style={styles.modalItem}
<<<<<<< HEAD
              onPress={handleSetDefault}
            >
              <Feather name="star" size={20} color="#000" />
              <Text style={styles.modalText}>Đặt làm mặc định</Text>
            </TouchableOpacity>
            
            <TouchableOpacity
              style={styles.modalItem}
=======
>>>>>>> origin/dev
              onPress={handleEdit}
            >
              <Feather name="edit" size={20} color="#000" />
              <Text style={styles.modalText}>Sửa</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.modalItem}
              onPress={handleDelete}
            >
              <Feather name="trash-2" size={20} color="red" />
<<<<<<< HEAD
              <Text style={[styles.modalText, { color: 'red' }]}>Xóa</Text>
=======
              <Text style={[styles.modalText, { color: 'red' }]}>{t('address.delete.title')}</Text>
>>>>>>> origin/dev
            </TouchableOpacity>
          </View>
        </TouchableOpacity>
      </Modal>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 30
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  listContainer: {
    padding: 16,
  },
  addressCard: {
    padding: 16,
    borderRadius: 8,
    marginBottom: 16,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  address: {
    fontSize: 14,
    marginBottom: 4,
  },
  phone: {
    fontSize: 14,
  },
  defaultBadge: {
    position: 'absolute',
    top: 8,
    right: 50,
    backgroundColor: '#4CAF50',
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 4,
  },
  defaultText: {
    color: 'white',
    fontSize: 12,
  },
  addressContent: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  addressInfo: {
    flex: 1,
    marginRight: 10,
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.5)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalContent: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    minWidth: 200,
  },
  modalItem: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 10,
  },
  modalText: {
    marginLeft: 10,
    fontSize: 16,
  },
  useButton: {
    backgroundColor: '#2196F3',
    padding: 8,
    borderRadius: 5,
    marginTop: 8,
    alignSelf: 'flex-start',
  },
  useButtonText: {
    color: 'white',
    fontSize: 14,
    fontWeight: 'bold',
  },
})