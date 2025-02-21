import { View, Text, StyleSheet, TouchableOpacity, TextInput, ScrollView, Alert } from 'react-native'
import React, { useState, useEffect } from 'react'
import { Feather } from '@expo/vector-icons'
import { useNavigation, useRoute } from '@react-navigation/native'
import { useTheme } from '../../../../context/ThemeContext'
import { addressService } from '../../../../services/AddressService'
<<<<<<< HEAD
import { Picker } from '@react-native-picker/picker'
import LoadingOverlay from '../../../../components/LoadingOverlay'

export default function EditAddress() {
=======
import LoadingOverlay from '../../../../components/LoadingOverlay'
import { useTranslation } from 'react-i18next';  // Thêm import này

export default function EditAddress() {
  const { t } = useTranslation();  // Thêm hook này
>>>>>>> origin/dev
  const navigation = useNavigation()
  const route = useRoute()
  const { theme } = useTheme()
  const [loading, setLoading] = useState(false)
  const { address } = route.params
<<<<<<< HEAD
  
=======

>>>>>>> origin/dev
  const [formData, setFormData] = useState({
    fullName: address.fullName,
    phone: address.phone.toString(),
    receivingAddress: address.receivingAddress,
    province: address.province,
    district: address.district,
<<<<<<< HEAD
    commune: address.commune,
    chooseDefault: address.chooseDefault
  })

  const [provinces, setProvinces] = useState([])
  const [districts, setDistricts] = useState([])
  const [communes, setCommunes] = useState([])

  useEffect(() => {
    const loadInitialData = async () => {
      try {
        // Fetch provinces first
        const provinceResponse = await fetch('https://provinces.open-api.vn/api/p/');
        const provinceData = await provinceResponse.json();
        setProvinces(provinceData);

        // Find the province with matching name
        const province = provinceData.find(p => p.name === formData.province);
        
        if (province) {
          // Fetch districts
          const districtResponse = await fetch(`https://provinces.open-api.vn/api/p/${province.code}?depth=2`);
          const districtData = await districtResponse.json();
          setDistricts(districtData.districts);

          // Find the district with matching name
          const district = districtData.districts.find(d => d.name === formData.district);
          
          if (district) {
            // Fetch communes
            const communeResponse = await fetch(`https://provinces.open-api.vn/api/d/${district.code}?depth=2`);
            const communeData = await communeResponse.json();
            setCommunes(communeData.wards);
          }
        }
      } catch (error) {
        console.error('Error loading initial data:', error);
      }
    };

    loadInitialData();
  }, []); // Remove provinces from dependencies

  const fetchProvinces = async () => {
    try {
      const response = await fetch('https://provinces.open-api.vn/api/p/');
      const data = await response.json();
      setProvinces(data);
    } catch (error) {
      console.error('Error fetching provinces:', error);
    }
  };

  const fetchDistricts = async (provinceCode) => {
    try {
      const response = await fetch(`https://provinces.open-api.vn/api/p/${provinceCode}?depth=2`);
      const data = await response.json();
      setDistricts(data.districts);
    } catch (error) {
      console.error('Error fetching districts:', error);
    }
  };

  const fetchCommunes = async (districtCode) => {
    try {
      const response = await fetch(`https://provinces.open-api.vn/api/d/${districtCode}?depth=2`);
      const data = await response.json();
      setCommunes(data.wards);
    } catch (error) {
      console.error('Error fetching communes:', error);
    }
  };

  const handleSubmit = async () => {
    if (!formData.fullName || !formData.phone || !formData.receivingAddress || 
        !formData.province || !formData.district || !formData.commune) {
=======
    commune: address.commune
  })

  const handleSubmit = async () => {
    if (!formData.fullName || !formData.phone || !formData.receivingAddress ||
      !formData.province || !formData.district || !formData.commune) {
>>>>>>> origin/dev
      Alert.alert('Thông báo', 'Vui lòng điền đầy đủ thông tin')
      return
    }

    Alert.alert(
      'Xác nhận',
      'Bạn có muốn cập nhật địa chỉ này không?',
      [
        { text: 'Hủy', style: 'cancel' },
        {
          text: 'Cập nhật',
          onPress: async () => {
            try {
              setLoading(true)
              const response = await addressService.updateAddress(address._id, {
                ...formData,
                phone: Number(formData.phone)
              })
              if (response.status === 200) {
                Alert.alert('Thành công', 'Cập nhật địa chỉ thành công', [
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
                Alert.alert('Lỗi', 'Không thể cập nhật địa chỉ')
              }
            } catch (error) {
              Alert.alert('Lỗi', 'Không thể cập nhật địa chỉ')
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
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>Sửa địa chỉ</Text>
=======
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>{t('address.edit.title')}</Text>
>>>>>>> origin/dev
        <View style={{ width: 24 }} />
      </View>

      {loading ? (
        <LoadingOverlay />
      ) : (
        <ScrollView style={styles.form}>
          <TextInput
            style={[styles.input, { color: theme.textColor }]}
<<<<<<< HEAD
            placeholder="Họ và tên"
=======
            placeholder={t('address.add.fullName')}
>>>>>>> origin/dev
            placeholderTextColor={theme.textColor}
            value={formData.fullName}
            onChangeText={(text) => setFormData({ ...formData, fullName: text })}
          />

          <TextInput
            style={[styles.input, { color: theme.textColor }]}
<<<<<<< HEAD
            placeholder="Số điện thoại"
=======
            placeholder={t('address.add.phone')}
>>>>>>> origin/dev
            placeholderTextColor={theme.textColor}
            value={formData.phone}
            keyboardType="numeric"
            onChangeText={(text) => setFormData({ ...formData, phone: text })}
          />

<<<<<<< HEAD
          <Picker
            selectedValue={formData.province}
            onValueChange={(itemValue, itemIndex) => {
              const province = provinces[itemIndex - 1];
              if (province) {
                setFormData({ 
                  ...formData, 
                  province: province.name, 
                  district: '', 
                  commune: '' 
                });
                fetchDistricts(province.code);
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
              const district = districts[itemIndex - 1];
              if (district) {
                setFormData({ 
                  ...formData, 
                  district: district.name, 
                  commune: '' 
                });
                fetchCommunes(district.code);
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
            onValueChange={(itemValue) => {
              setFormData({ ...formData, commune: itemValue });
            }}
            style={styles.picker}
            enabled={!!formData.district}>
            <Picker.Item label="Chọn phường/xã" value="" />
            {communes.map((commune) => (
              <Picker.Item key={commune.code} label={commune.name} value={commune.name} />
            ))}
          </Picker>
          
          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder="Địa chỉ cụ thể"
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

          <TextInput
            style={[styles.input, { color: theme.textColor }]}
            placeholder={t('address.add.detail')}
>>>>>>> origin/dev
            placeholderTextColor={theme.textColor}
            value={formData.receivingAddress}
            onChangeText={(text) => setFormData({ ...formData, receivingAddress: text })}
          />

          <TouchableOpacity
            style={styles.submitButton}
            onPress={handleSubmit}>
<<<<<<< HEAD
            <Text style={styles.submitButtonText}>Cập nhật địa chỉ</Text>
=======
            <Text style={styles.submitButtonText}>{t('address.edit.submit')}</Text>
>>>>>>> origin/dev
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
<<<<<<< HEAD
  picker: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 8,
    marginBottom: 16,
    backgroundColor: 'white', // Thêm màu nền cho picker
    color: '#000', // Đảm bảo text có thể đọc được
  },
=======
>>>>>>> origin/dev
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
<<<<<<< HEAD
  
=======

>>>>>>> origin/dev
})