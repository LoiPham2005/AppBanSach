import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet, TouchableOpacity, Image, Alert, ScrollView, ActivityIndicator } from 'react-native';
import { useTranslation } from 'react-i18next';
import * as ImagePicker from 'expo-image-picker';
import RNPickerSelect from 'react-native-picker-select';
import { categoryService } from '../../../../services/CategoryService';
import { productService } from '../../../../services/productService';
import { useNavigation } from '@react-navigation/native';
import { API_URL } from '../../../../services/URL_API'; // Add this import
import ImageWithDelete from '../../../../components/ImageWithDelete';
import { Feather } from '@expo/vector-icons';
import LoadingOverlay from '../../../../components/LoadingOverlay';
import AsyncStorage from '@react-native-async-storage/async-storage';

const AddProductScreen = () => {
  const { t } = useTranslation();
  const navigation = useNavigation();

  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [price, setPrice] = useState('');
  const [description, setDescription] = useState('');
  const [quality, setQuality] = useState('');
  const [category, setCategory] = useState('');
  const [status, setStatus] = useState('');
  const [categories, setCategories] = useState([]);
  // Thêm state mới để quản lý media
  const [mediaFiles, setMediaFiles] = useState([]); // [{type: 'image/video', uri: '...'}]
  const [isLoading, setIsLoading] = useState(false); // Add this state
  const [userId, setUserId] = useState(null); // Thêm state để lưu id_user

  // Fetch categories when component mounts
  useEffect(() => {
    fetchCategories();
  }, []);

  // Thêm useEffect để lấy userId khi component mount
  useEffect(() => {
    const getUserId = async () => {
      try {
        const id = await AsyncStorage.getItem('userId');
        setUserId(id);
      } catch (error) {
        console.error('Error fetching user ID:', error);
      }
    };
    getUserId();
  }, []);

  // Fetch categories
  const fetchCategories = async () => {
    try {
      const result = await categoryService.getCategory(); // Sử dụng categoryService
      if (result.success) {
        // Transform data for RNPickerSelect format
        const formattedCategories = result.data.map(cat => ({
          label: cat.name,
          value: cat._id
        }));
        setCategories(formattedCategories);
      } else {
        console.error(result.message);
      }
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const statusOptions = [
<<<<<<< HEAD
    { label: t('active'), value: 'active' },
    { label: t('outOfStock'), value: 'out of stock' },
    { label: t('importingGoods'), value: 'importing goods' },
    { label: t('stopSelling'), value: 'stop selling' },
=======
    { label: t('product.active'), value: 'active' },
    { label: t('product.outOfStock'), value: 'out of stock' },
    { label: t('product.importingGoods'), value: 'importing goods' },
    { label: t('product.stopSelling'), value: 'stop selling' },
>>>>>>> origin/dev
  ];

  // Hàm chọn ảnh từ thư viện
  //  Thêm hàm chọn video
  const handleVideoPick = async () => {
    try {
      const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
      if (status !== 'granted') {
        Alert.alert(t('common.error'), t('addProduct.permissionDenied'));
        return;
      }

      const result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Videos,
        allowsMultipleSelection: true, // Cho phép chọn nhiều
        quality: 1,
      });

      if (!result.canceled) {
        const selectedVideos = result.assets ? result.assets : [result];
        const newMediaFiles = selectedVideos.map(video => ({
          type: 'video',
          uri: video.uri
        }));
        setMediaFiles(prev => [...prev, ...newMediaFiles]);
      }
    } catch (error) {
      console.error('Error picking video:', error);
      Alert.alert(t('common.error'), t('addProduct.errorSelectingVideo'));
    }
  };

  // Sửa lại hàm uploadImage thành uploadMedia
  const uploadMedia = async (file) => {
    try {
      const formData = new FormData();
      const fileName = file.uri.split('/').pop();

      formData.append('media', {
        uri: file.uri,
        type: file.type === 'video' ? 'video/mp4' : 'image/jpeg',
        name: fileName
      });

      const response = await fetch(`${API_URL}/api/upload/media`, {
        method: 'POST',
        body: formData,
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'multipart/form-data',
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      return data.mediaUrls[0];
    } catch (error) {
      console.error('Upload error:', error);
      Alert.alert('Error', 'Media upload failed: ' + error.message);
      return null;
    }
  };

  // Sửa lại hàm handleSubmit
  const handleSubmit = async () => {
<<<<<<< HEAD
    if (!title || !author || !price || !description || !category || !status || mediaFiles.length === 0) {
      Alert.alert(t('common.error'), t('addProduct.fillAllFields'));
      return;
    }

    Alert.alert(
      t('addProduct.confirm'),
      t('addProduct.confirmMessage'),
=======
    if (!title.trim()) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.titleRequired'));
      return;
    }
    if (!author.trim()) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.authorRequired'));
      return;
    }
    if (!price.trim() || isNaN(price) || parseFloat(price) <= 0) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.priceInvalid'));
      return;
    }
    if (!quality.trim() || isNaN(quality) || parseInt(quality) < 0) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.qualityInvalid'));
      return;
    }
    if (!description.trim()) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.descriptionRequired'));
      return;
    }
    if (!category) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.categoryRequired'));
      return;
    }
    if (!status) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.statusRequired'));
      return;
    }
    if (mediaFiles.length === 0) {
      Alert.alert(t('common.error'), t('productManagement.addProduct.mediaRequired'));
      return;
    }
  
    // Tiếp tục xử lý nếu hợp lệ
    Alert.alert(
      t('productManagement.addProduct.confirm'),
      t('productManagement.addProduct.confirmMessage'),
>>>>>>> origin/dev
      [
        {
          text: t('common.cancel'),
          style: 'cancel'
        },
        {
          text: t('common.confirm'),
          onPress: async () => {
            try {
              setIsLoading(true);
<<<<<<< HEAD

=======
>>>>>>> origin/dev
              const uploadedMedia = [];
              await Promise.all(mediaFiles.map(async (file) => {
                if (!file.uri.startsWith('http')) {
                  const uploadResult = await uploadMedia(file);
                  if (uploadResult) {
                    uploadedMedia.push({
                      type: file.type,
                      url: uploadResult.url
                    });
                  }
                }
              }));
<<<<<<< HEAD

=======
  
>>>>>>> origin/dev
              const productData = {
                title,
                publishing_house: author,
                price: parseFloat(price),
                description,
                stock_quantity: parseInt(quality),
                id_category: category,
                status,
                media: uploadedMedia,
<<<<<<< HEAD
                id_user: userId // Thêm id_user vào dữ liệu gửi đi
              };

              const result = await productService.addProduct(productData);

              if (result.status === 200) {
                Alert.alert(
                  t('common.success'),
                  t('addProduct.success'),
                  [{ text: 'OK', onPress: () => navigation.goBack() }]
                );
              } else {
                Alert.alert(t('common.error'), result.message || t('addProduct.error'));
              }
            } catch (error) {
              console.error("Error in handleSubmit:", error);
              Alert.alert(t('common.error'), t('addProduct.error'));
=======
                id_user: userId
              };
  
              const result = await productService.addProduct(productData);
              if (result.status === 200) {
                Alert.alert(
                  t('common.success'),
                  t('productManagement.addProduct.success'),
                  [{ text: 'OK', onPress: () => navigation.goBack() }]
                );
              } else {
                Alert.alert(t('common.error'), result.message || t('productManagement.addProduct.error'));
              }
            } catch (error) {
              console.error("Error in handleSubmit:", error);
              Alert.alert(t('common.error'), t('productManagement.addProduct.error'));
>>>>>>> origin/dev
            } finally {
              setIsLoading(false);
            }
          }
        }
      ]
    );
  };
<<<<<<< HEAD
=======
  
>>>>>>> origin/dev

  const handleImagePick = async () => {
    try {
      const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
      if (status !== 'granted') {
<<<<<<< HEAD
        Alert.alert(t('common.error'), t('addProduct.permissionDenied'));
=======
        Alert.alert(t('common.error'), t('productManagement.addProduct.permissionDenied'));
>>>>>>> origin/dev
        return;
      }
  
      const result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Images,
        allowsMultipleSelection: true, // Cho phép chọn nhiều ảnh
        selectionLimit: 10, // Giới hạn số lượng ảnh có thể chọn, có thể điều chỉnh
        aspect: [4, 3],
        quality: 1,
      });
  
      if (!result.canceled) {
        // Xử lý khi người dùng chọn nhiều ảnh
        const selectedImages = result.assets ? result.assets : [result];
        const newMediaFiles = selectedImages.map(image => ({
          type: 'image',
          uri: image.uri
        }));
        
        setMediaFiles(prev => [...prev, ...newMediaFiles]);
      }
    } catch (error) {
      console.error('Error picking images:', error);
<<<<<<< HEAD
      Alert.alert(t('common.error'), t('addProduct.errorSelectingImage'));
=======
      Alert.alert(t('common.error'), t('productManagement.addProduct.errorSelectingImage'));
>>>>>>> origin/dev
    }
  };

  const handleDeleteMedia = (index) => {
    Alert.alert(
      "Xác nhận xóa",
      "Bạn có chắc chắn muốn xóa media này không?",
      [
        {
          text: "Hủy",
          style: "cancel"
        },
        {
          text: "Xóa",
          onPress: () => {
            setMediaFiles(prev => prev.filter((_, i) => i !== index));
          },
          style: "destructive"
        }
      ]
    );
  };

  return (
    <>
      <ScrollView
        keyboardShouldPersistTaps="handled"
        contentContainerStyle={{ flexGrow: 1, paddingBottom: 20 }}
      >
        <View style={styles.container}>
<<<<<<< HEAD
          <Text style={styles.title}>{t('addNewProduct')}</Text>
=======
          <Text style={styles.title}>{t('productManagement.addProduct.title')}</Text>
>>>>>>> origin/dev

          {mediaFiles.map((media, index) => (
            <View key={index}>
              {media.type === 'image' ? (
                <ImageWithDelete
                  uri={media.uri}
                  onDelete={() => handleDeleteMedia(index)}
                />
              ) : (
                <View style={styles.videoContainer}>
                  <View style={styles.videoInfo}>
                    <Feather name="video" size={24} color="#666" />
                    <Text style={styles.videoText}>
                      {t('videoSelected')} #{index + 1}
                    </Text>
                  </View>
                  <TouchableOpacity
                    style={styles.deleteVideoBtn}
                    onPress={() => handleDeleteMedia(index)}
                  >
                    <Text style={styles.deleteButton}>{t('delete')}</Text>
                  </TouchableOpacity>
                </View>
              )}
            </View>
          ))}

          {/* Chỉ giữ lại phần buttons và mediaFiles mapping */}


          <View style={styles.buttonStyle}>
<<<<<<< HEAD
            <Button title={t('chooseImage')} onPress={handleImagePick} />
          </View>

          <View>
            <Button title={t('chooseVideo')} onPress={handleVideoPick} />
=======
            <Button title={t('form.chooseImage')} onPress={handleImagePick} />
          </View>

          <View>
            <Button title={t('form.chooseVideo')} onPress={handleVideoPick} />
>>>>>>> origin/dev
          </View>

          <TextInput
            style={styles.input}
<<<<<<< HEAD
            placeholder={t('title')}
=======
            placeholder={t('form.title')}
>>>>>>> origin/dev
            value={title}
            onChangeText={setTitle}
          />
          <TextInput
            style={styles.input}
<<<<<<< HEAD
            placeholder={t('author')}
=======
            placeholder={t('form.author')}
>>>>>>> origin/dev
            value={author}
            onChangeText={setAuthor}
          />
          <TextInput
            style={styles.input}
<<<<<<< HEAD
            placeholder={t('price')}
=======
            placeholder={t('form.price')}
>>>>>>> origin/dev
            value={price}
            onChangeText={setPrice}
            keyboardType="numeric"
          />
          <TextInput
            style={styles.input}
<<<<<<< HEAD
            placeholder={t('description')}
=======
            placeholder={t('form.description')}
>>>>>>> origin/dev
            value={description}
            onChangeText={setDescription}
          />
          <TextInput
            style={styles.input}
<<<<<<< HEAD
            placeholder={t('quality')}
=======
            placeholder={t('form.quality')}
>>>>>>> origin/dev
            value={quality}
            onChangeText={setQuality}
          />
          <RNPickerSelect
<<<<<<< HEAD
            placeholder={{ label: t('selectCategory'), value: null }}
=======
            placeholder={{ label: t('form.selectCategory'), value: null }}
>>>>>>> origin/dev
            items={categories}
            onValueChange={setCategory}
            value={category}
            style={{
              inputIOS: styles.inputIOS,
              inputAndroid: styles.inputAndroid
            }}
          />

          <RNPickerSelect
<<<<<<< HEAD
            placeholder={{ label: t('selectStatus'), value: null }}
=======
            placeholder={{ label: t('form.selectStatus'), value: null }}
>>>>>>> origin/dev
            items={statusOptions}
            onValueChange={setStatus}
            value={status}
            style={{
              inputIOS: styles.inputIOS,
              inputAndroid: styles.inputAndroid
            }}
          />
<<<<<<< HEAD
          <Button title={t('submit')} onPress={handleSubmit} />
=======
          <Button title={t('form.submit')} onPress={handleSubmit} />
>>>>>>> origin/dev

          {/* Loading indicator */}
          {isLoading && (
            <View style={styles.loadingContainer}>
              <ActivityIndicator size="large" color="#0000ff" />
              <Text>{t('addProduct.uploadingMedia')}</Text>
            </View>
          )}
        </View>
      </ScrollView>

      <LoadingOverlay
        visible={isLoading}
<<<<<<< HEAD
        message={t('addProduct.uploadingMedia')}
=======
        message={t('productManagement.addProduct.uploadingMedia')}
>>>>>>> origin/dev
      />
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 20,
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    height: 40,
    borderColor: '#ccc',
    borderWidth: 1,
    // marginBottom: 10,
    paddingLeft: 10,
    marginTop: 20
  },
  imagePreview: {
    width: '100%',
    height: 200,
    resizeMode: 'contain',
    marginVertical: 10,
  },
  inputIOS: {
    height: 50, // Đặt chiều cao
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    paddingVertical: 12, // Đảm bảo không bị lẹm
    fontSize: 16,
    color: '#000', // Đặt màu chữ
    marginTop: 20 // Add this to match your other inputs
  },
  inputAndroid: {
    height: 50,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    paddingVertical: 12,
    fontSize: 16,
    color: '#000',
    marginTop: 20 // Add this to match your other inputs
  },

  buttonStyle: {
    marginVertical: 20
  },

  videoContainer: {
    backgroundColor: '#f0f0f0',
    padding: 10,
    marginVertical: 5,
    borderRadius: 5,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  deleteButton: {
    color: 'red',
    fontSize: 16,
  },
  videoInfo: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  videoText: {
    marginLeft: 10,
    fontSize: 16,
    color: '#666',
  },
  deleteVideoBtn: {
    padding: 5,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default AddProductScreen;
