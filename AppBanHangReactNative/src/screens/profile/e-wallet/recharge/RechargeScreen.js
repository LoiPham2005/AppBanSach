import { View, Text, StyleSheet, TouchableOpacity, TextInput, Alert } from 'react-native';
import React, { useState, useEffect } from 'react';
import { Feather } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import { useTheme } from '../../../../context/ThemeContext';
import { walletService } from '../../../../services/WalletService';
import LoadingOverlay from '../../../../components/LoadingOverlay';
import AsyncStorage from '@react-native-async-storage/async-storage';
<<<<<<< HEAD
=======
import { useTranslation } from 'react-i18next';
>>>>>>> origin/dev

export default function RechargeScreen() {
  const navigation = useNavigation();
  const { theme } = useTheme();
<<<<<<< HEAD
=======
  const { t } = useTranslation();
>>>>>>> origin/dev
  const [amount, setAmount] = useState('');
  const [loading, setLoading] = useState(false);
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

  const handleRecharge = () => {
    if (!userId) {
<<<<<<< HEAD
      Alert.alert('Lỗi', 'Vui lòng đăng nhập lại');
=======
      Alert.alert(t('common.error'), t('auth.login.loginRequired'));
>>>>>>> origin/dev
      return;
    }

    if (!amount || isNaN(amount) || parseInt(amount) <= 0) {
<<<<<<< HEAD
      Alert.alert('Lỗi', 'Vui lòng nhập số tiền hợp lệ');
=======
      Alert.alert(t('common.error'), t('wallet.recharge.invalidAmount'));
>>>>>>> origin/dev
      return;
    }

    Alert.alert(
<<<<<<< HEAD
      'Xác nhận nạp tiền',
      `Bạn có muốn nạp ${parseInt(amount).toLocaleString('vi-VN')} VND không?`,
      [
        {
          text: 'Hủy',
          style: 'cancel'
        },
        {
          text: 'Nạp',
=======
      t('wallet.recharge.confirm'),
      t('wallet.recharge.message', { amount: parseInt(amount).toLocaleString('vi-VN') }),
      [
        {
          text: t('common.cancel'),
          style: 'cancel'
        },
        {
          text: t('wallet.recharge.title'),
>>>>>>> origin/dev
          onPress: async () => {
            try {
              setLoading(true);
              const response = await walletService.updateWallet(userId, {
                amount: parseInt(amount),
                type: 'credit',
                description: 'Nạp tiền vào ví'
              });

              if (response.status === 200) {
                Alert.alert('Thành công', 'Nạp tiền thành công', [
                  { text: 'OK', onPress: () => navigation.goBack() }
                ]);
              } else {
                Alert.alert('Lỗi', 'Không thể nạp tiền. Vui lòng thử lại sau.');
              }
            } catch (error) {
              console.error('Recharge error:', error);
              Alert.alert('Lỗi', 'Không thể nạp tiền. Vui lòng thử lại sau.');
            } finally {
              setLoading(false);
            }
          }
        }
      ]
    );
  };

  return (
    <View style={[styles.container, { backgroundColor: theme.backgroundColor }]}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Feather name="arrow-left" size={24} color={theme.textColor} />
        </TouchableOpacity>
<<<<<<< HEAD
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>Nạp tiền</Text>
=======
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>{t('wallet.recharge.title')}</Text>
>>>>>>> origin/dev
        <View style={{ width: 24 }} />
      </View>

      {loading ? (
        <LoadingOverlay />
      ) : (
        <View style={styles.content}>
<<<<<<< HEAD
          <Text style={[styles.label, { color: theme.textColor }]}>Nhập số tiền muốn nạp:</Text>
=======
          <Text style={[styles.label, { color: theme.textColor }]}>{t('wallet.recharge.amount')}</Text>
>>>>>>> origin/dev
          <TextInput
            style={[styles.input, { color: theme.textColor, borderColor: theme.borderColor }]}
            placeholder="Nhập số tiền"
            placeholderTextColor="#999"
            keyboardType="numeric"
            value={amount}
            onChangeText={setAmount}
          />
          <TouchableOpacity 
            style={styles.rechargeButton}
            onPress={handleRecharge}
          >
<<<<<<< HEAD
            <Text style={styles.rechargeButtonText}>Nạp tiền</Text>
=======
            <Text style={styles.rechargeButtonText}>{t('wallet.recharge.confirm')}</Text>
>>>>>>> origin/dev
          </TouchableOpacity>
        </View>
      )}
    </View>
  );
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
  content: {
    padding: 16,
  },
  label: {
    fontSize: 16,
    marginBottom: 8,
  },
  input: {
    borderWidth: 1,
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    marginBottom: 20,
  },
  rechargeButton: {
    backgroundColor: '#E53935',
    padding: 16,
    borderRadius: 8,
    alignItems: 'center',
  },
  rechargeButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
  },
});