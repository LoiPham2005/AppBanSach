// src/components/LoadingOverlay.js
import React from 'react';
import { View, ActivityIndicator, Text, StyleSheet } from 'react-native';

const LoadingOverlay = ({ visible, message }) => {
  if (!visible) return null;

  return (
    <View style={styles.container}>
      <View style={styles.loadingBox}>
        <ActivityIndicator size="large" color="#ffffff" />
        <Text style={styles.message}>{message}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.7)',
    alignItems: 'center',
    justifyContent: 'center',
    zIndex: 999,
  },
  loadingBox: {
    backgroundColor: 'rgba(0, 0, 0, 0.8)',
    padding: 20,
    borderRadius: 10,
    alignItems: 'center',
  },
  message: {
    color: '#ffffff',
    marginTop: 10,
    fontSize: 16,
  },
});

export default LoadingOverlay;