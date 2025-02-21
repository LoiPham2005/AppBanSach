import { View, Text, StyleSheet, TouchableOpacity, TextInput, Alert } from 'react-native';
import React, { useState, useEffect } from 'react';
import { Feather } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import { useTheme } from '../../../../context/ThemeContext';
import { walletService } from '../../../../services/WalletService';
import LoadingOverlay from '../../../../components/LoadingOverlay';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useTranslation } from 'react-i18next';

export default function RechargeScreen() {
  const navigation = useNavigation();
  const { theme } = useTheme();
  const { t } = useTranslation();
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
      Alert.alert(t('common.error'), t('auth.login.loginRequired'));
      return;
    }

    if (!amount || isNaN(amount) || parseInt(amount) <= 0) {
      Alert.alert(t('common.error'), t('wallet.recharge.invalidAmount'));
      return;
    }

    Alert.alert(
      t('wallet.recharge.confirm'),
      t('wallet.recharge.message', { amount: parseInt(amount).toLocaleString('vi-VN') }),
      [
        {
          text: t('common.cancel'),
          style: 'cancel'
        },
        {
          text: t('wallet.recharge.title'),
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
        <Text style={[styles.headerTitle, { color: theme.textColor }]}>{t('wallet.recharge.title')}</Text>
        <View style={{ width: 24 }} />
      </View>

      {loading ? (
        <LoadingOverlay />
      ) : (
        <View style={styles.content}>
          <Text style={[styles.label, { color: theme.textColor }]}>{t('wallet.recharge.amount')}</Text>
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
            <Text style={styles.rechargeButtonText}>{t('wallet.recharge.confirm')}</Text>
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