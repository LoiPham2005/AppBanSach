import { View, Text, StyleSheet, TouchableOpacity, TextInput, ScrollView, Alert } from 'react-native'
import React, { useState, useEffect } from 'react'
import { Feather } from '@expo/vector-icons'
import { useNavigation } from '@react-navigation/native'
import { useTheme } from '../../../../context/ThemeContext'
import { Picker } from '@react-native-picker/picker'
import { addressService } from '../../../../services/AddressService'
import LoadingOverlay from '../../../../components/LoadingOverlay'
import AsyncStorage from '@react-native-async-storage/async-storage'
<<<<<<< HEAD
=======
import { useTranslation } from 'react-i18next';
>>>>>>> origin/dev

export default function AddAddress() {
  const navigation = useNavigation()
  const { theme } = useTheme()
  const [loading, setLoading] = useState(false)
<<<<<<< HEAD
  
=======
  const { t } = useTranslation(); // Add this line

>>>>>>> origin/dev
  const [formData, setFormData] = useState({
    fullName: '',
    phone: '',
    receivingAddress: '',
    province: '',
    district: '',
    commune: '',
<<<<<<< HEAD
    chooseDefault: false
  })

  const [provinces, setProvinces] = useState([])
  const [districts, setDistricts] = useState([])
  const [communes, setCommunes] = useState([])
  const [userId, setUserId] = useState(null)

  useEffect(() => {
    fetchProvinces()
  }, [])

  useEffect(() => {
    const getUserId = async () => {
      try {
        const id = await AsyncStorage.getItem('userId');
        setUserId(id);
      } catch (error) {
        console.error('Error getting userId:', error);
=======
  });

  const [userId, setUserId] = useState(null)

  useEffect(() => {
    const getUserId = async () => {
      try {
        const id = await AsyncStorage.getItem('userId');
        console.log('Retrieved userId:', id); // Debug log
        if (id) {
          setUserId(id);
        } else {
          console.log('No userId found in AsyncStorage');
          Alert.alert('Lỗi', 'Vui lòng đăng nhập lại');
          navigation.goBack();
        }
      } catch (error) {
        console.error('Error getting userId:', error);
        Alert.alert('Lỗi', 'Không thể lấy thông tin người dùng');
>>>>>>> origin/dev
      }
    };
    getUserId();
  }, []);

<<<<<<< HEAD
  const fetchProvinces = async () => {
    try {
      const response = await fetch('https://provinces.open-api.vn/api/p/')
      const data = await response.json()
      setProvinces(data)
    } catch (error) {
      console.error('Error fetching provinces:', error)
    }
  }

  const fetchDistricts = async (provinceCode) => {
    try {
      const response = await fetch(`https://provinces.open-api.vn/api/p/${provinceCode}?depth=2`)
      const data = await response.json()
      setDistricts(data.districts || [])
    } catch (error) {
      console.error('Error fetching districts:', error)
    }
  }

  const fetchCommunes = async (districtCode) => {
    try {
      const response = await fetch(`https://provinces.open-api.vn/api/d/${districtCode}?depth=2`)
      const data = await response.json()
      setCommunes(data.wards || [])
    } catch (error) {
      console.error('Error fetching communes:', error)
    }
  }

  const handleSubmit = async () => {
    if (!formData.fullName || !formData.phone || !formData.receivingAddress ||
        !formData.province || !formData.district || !formData.commune || !userId) {
=======
  const handleSubmit = async () => {
    if (!userId) {
      Alert.alert('Thông báo', 'Vui lòng đăng nhập lại');
      return;
    }
    if (!formData.fullName || !formData.phone || !formData.receivingAddress ||
      !formData.province || !formData.district || !formData.commune) {
>>>>>>> origin/dev
      Alert.alert('Thông báo', 'Vui lòng điền đầy đủ thông tin hoặc đăng nhập lại')
      return
    }

    Alert.alert(
      'Xác nhận',
      'Bạn có muốn thêm địa chỉ này không?',
      [
        { text: 'Hủy', style: 'cancel' },
        {
          text: 'Thêm',
          onPress: async () => {
            try {
              setLoading(true)
              const response = await addressService.addAddress({
                ...formData,
                phone: Number(formData.phone),
                id_user: userId
              })
              if (response.status === 200) {
                Alert.alert('Thành công', 'Thêm địa chỉ thành công', [
<<<<<<< HEAD
                  { text: 'OK', onPress: () => navigation.goBack() }
                ])
=======
                  {
                    text: 'OK',
                    onPress: () => {
                      // Set param để trigger reload
                      navigation.navigate('AddressScreen', { shouldRefresh: true });
                    }
                  }
                ]);
>>>>>>> origin/dev
              } else {
                Alert.alert('Lỗi', 'Không thể thêm địa chỉ')
              }
            } catch (error) {
              console.error('Add address error:', error)
              Alert.alert('Lỗi', 'Không thể thêm địa chỉ')
            } finally {
              setLoading(false)
            }
          }
        }
      ]
    )
  }

  return (
    <View style={[styles.container, { backgroundColor: theme.backgroundColor }]}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Feather name="arrow-left" size={24} color={theme.textColor} />
        </TouchableOpacity>
<<<<<<< HEAD
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>Thêm địa chỉ mới</Text>
=======
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>
          {t('address.add.title')}
        </Text>
>>>>>>> origin/dev
        <View style={{ width: 24 }} />
      </View>

      {loading ? (
        <LoadingOverlay />
      ) : (
        <ScrollView style={styles.form}>
          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder="Họ và tên"
            placeholderTextColor={theme.textColor}
            value={formData.fullName}
            onChangeText={(text) => setFormData({ ...formData, fullName: text })}
          />

          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder="Số điện thoại"
            placeholderTextColor={theme.textColor}
            keyboardType="phone-pad"
            value={formData.phone}
            onChangeText={(text) => setFormData({ ...formData, phone: text })}
          />

<<<<<<< HEAD
          <Picker
            selectedValue={formData.province}
            onValueChange={(itemValue, itemIndex) => {
              const province = provinces[itemIndex - 1]
              if (province) {
                setFormData({ ...formData, province: province.name, district: '', commune: '' })
                fetchDistricts(province.code)
              }
            }}
            style={styles.picker}>
            <Picker.Item label="Chọn tỉnh/thành" value="" />
            {provinces.map((province) => (
              <Picker.Item key={province.code} label={province.name} value={province.name} />
            ))}
          </Picker>

          <Picker
            selectedValue={formData.district}
            onValueChange={(itemValue, itemIndex) => {
              const district = districts[itemIndex - 1]
              if (district) {
                setFormData({ ...formData, district: district.name, commune: '' })
                fetchCommunes(district.code)
              }
            }}
            style={styles.picker}
            enabled={!!formData.province}>
            <Picker.Item label="Chọn quận/huyện" value="" />
            {districts.map((district) => (
              <Picker.Item key={district.code} label={district.name} value={district.name} />
            ))}
          </Picker>

          <Picker
            selectedValue={formData.commune}
            onValueChange={(itemValue) => setFormData({ ...formData, commune: itemValue })}
            style={styles.picker}
            enabled={!!formData.district}>
            <Picker.Item label="Chọn phường/xã" value="" />
            {communes.map((commune) => (
              <Picker.Item key={commune.code} label={commune.name} value={commune.name} />
            ))}
          </Picker>
=======
          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder={t('address.add.selectProvince')}
            placeholderTextColor={theme.textColor}
            value={formData.province}
            onChangeText={(text) => setFormData({ ...formData, province: text })}
          />

          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder={t('address.add.selectDistrict')}
            placeholderTextColor={theme.textColor}
            value={formData.district}
            onChangeText={(text) => setFormData({ ...formData, district: text })}
          />

          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder={t('address.add.selectWard')}
            placeholderTextColor={theme.textColor}
            value={formData.commune}
            onChangeText={(text) => setFormData({ ...formData, commune: text })}
          />
>>>>>>> origin/dev

          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder="Địa chỉ cụ thể"
            placeholderTextColor={theme.textColor}
            value={formData.receivingAddress}
            onChangeText={(text) => setFormData({ ...formData, receivingAddress: text })}
          />

          <TouchableOpacity
            style={styles.submitButton}
            onPress={handleSubmit}>
            <Text style={styles.submitButtonText}>Thêm địa chỉ</Text>
          </TouchableOpacity>
        </ScrollView>
      )}
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
  form: {
    padding: 16,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 8,
    padding: 12,
    marginBottom: 16,
  },
  picker: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 8,
    marginBottom: 16,
  },
  submitButton: {
    backgroundColor: '#2196F3',
    padding: 16,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 24,
  },
  submitButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
  },
})