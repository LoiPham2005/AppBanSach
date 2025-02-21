import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { Feather } from '@expo/vector-icons';
import { useTheme } from '../context/ThemeContext';

const NetworkAlert = () => {
<<<<<<< HEAD
  const { theme } = useTheme();
  
=======
>>>>>>> origin/dev
  return (
    <View style={styles.overlay}>
      <View style={styles.container}>
        <Feather name="wifi-off" size={50} color="#666" />
<<<<<<< HEAD
        <Text style={styles.title}>Không có kết nối mạng</Text>
        <Text style={styles.message}>
          Vui lòng kiểm tra kết nối internet của bạn và thử lại
        </Text>
=======
        <Text style={styles.title}>{t('network.noConnection')}</Text>
        <Text style={styles.message}>{t('network.checkConnection')}</Text>
>>>>>>> origin/dev
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: '#fff',
    zIndex: 999,
    justifyContent: 'center',
    alignItems: 'center'
  },
  container: {
    alignItems: 'center',
    padding: 20
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#666',
    marginTop: 20,
    marginBottom: 10
  },
  message: {
    fontSize: 14,
    color: '#999',
    textAlign: 'center'
  }
});

export default NetworkAlert;