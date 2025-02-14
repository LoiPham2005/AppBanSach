import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import React from 'react';
import { useNavigation, CommonActions } from '@react-navigation/native';
import { Feather } from '@expo/vector-icons';
import { useTheme } from '../../context/ThemeContext';

export default function SuccessfulPayment() {
  const navigation = useNavigation();
  const { theme } = useTheme();

  const handleContinueShopping = () => {
    // Reset navigation stack and go to Home
    navigation.dispatch(
      CommonActions.reset({
        index: 0,
        routes: [{ name: 'Drawer' }],
      })
    );
  };

  return (
    <View style={[styles.container, { backgroundColor: theme.backgroundColor }]}>
      <Feather name="check-circle" size={80} color="#4CAF50" />
      <Text style={[styles.title, { color: theme.textColor }]}>
        Thanh toán thành công!
      </Text>
      <Text style={[styles.subtitle, { color: theme.textColor }]}>
        Cảm ơn bạn đã mua hàng
      </Text>
      
      <TouchableOpacity 
        style={styles.button}
        onPress={handleContinueShopping}
      >
        <Text style={styles.buttonText}>Tiếp tục mua hàng</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginTop: 20,
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 16,
    marginBottom: 30,
  },
  button: {
    backgroundColor: '#E53935',
    paddingHorizontal: 30,
    paddingVertical: 15,
    borderRadius: 8,
    marginTop: 20,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  }
});