import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Alert } from 'react-native';
import { useTheme } from '../../../context/ThemeContext';
import { Feather } from '@expo/vector-icons';
import Input from '../../../components/Input';
import LoadingOverlay from '../../../components/LoadingOverlay';
import { API_URL } from '../../../services/URL_API';
import { forgotPassService } from '../../../services/ForgotPassService';

export default function EnterEmail({ navigation }) {
  const [email, setEmail] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const { theme } = useTheme();

  const validateEmail = (email) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  };

  const handleSubmit = async () => {
    if (!email || !validateEmail(email)) {
      Alert.alert('Lỗi', 'Vui lòng nhập email hợp lệ');
      return;
    }

    try {
      setIsLoading(true);
      const response = await forgotPassService.sendOTP(email);

      if (response.status === 200) {
        navigation.navigate('EnterOTP', { email });
      } else {
        Alert.alert('Lỗi', response.message || 'Email không tồn tại');
      }
    } catch (error) {
      console.error('Error:', error);
      Alert.alert('Lỗi', 'Không thể gửi mã OTP');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <View style={[styles.container, { backgroundColor: theme.backgroundColor }]}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Feather name="arrow-left" size={24} color={theme.textColor} />
        </TouchableOpacity>
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>
          Quên mật khẩu
        </Text>
        <View style={{ width: 24 }} />
      </View>

      <View style={styles.content}>
        <Text style={[styles.description, { color: theme.textColor }]}>
          Nhập email của bạn để nhận mã xác thực
        </Text>
        
        <Input
          value={email}
          onChangeText={setEmail}
          placeholder="Email"
          keyboardType="email-address"
        />

        <TouchableOpacity style={styles.submitButton} onPress={handleSubmit}>
          <Text style={styles.submitButtonText}>Gửi mã xác thực</Text>
        </TouchableOpacity>
      </View>

      <LoadingOverlay visible={isLoading} message="Đang gửi mã..." />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 16,
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  content: {
    padding: 20,
  },
  description: {
    fontSize: 16,
    marginBottom: 20,
  },
  submitButton: {
    backgroundColor: '#2196F3',
    padding: 15,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 20,
  },
  submitButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
  },
});