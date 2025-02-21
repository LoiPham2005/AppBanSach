import React, { useState, useEffect } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet, Alert } from 'react-native';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { useTranslation } from 'react-i18next';
import { Ionicons } from '@expo/vector-icons';
import SimpleLineIcons from '@expo/vector-icons/SimpleLineIcons';
import Feather from '@expo/vector-icons/Feather';
import { DrawerContentScrollView, DrawerItemList, DrawerItem } from '@react-navigation/drawer';
import HomeScreen from '../screens/home/HomeScreen';
import CartScreen from '../screens/cart/CartScreen';
import OrderScreen from '../screens/order/OrderScreen';
import ProfileScreen from '../screens/profile/ProfileScreen';
import TabNavigator from './TabNavigator';
import AccountScreen from '../screens/account/AccountScreen';
import ProductScreen from '../screens/product/ProductScreen';
import StatisticsScreen from '../screens/RevenueStatistics/StatisticsScreen';
import SettingsScreen from '../screens/settings/SettingsScreen';
import SupportScreen from '../screens/support/SupportScreen';
import AboutUsScreen from '../screens/about/AboutUsScreen';
import LogoutScreen from '../screens/login/LoginScreen';
import { useTheme } from '../context/ThemeContext';
import OrderManagerScreen from '../screens/orderMana/OrderManager';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Drawer = createDrawerNavigator();

const CustomDrawerContent = (props) => {
  const { t, i18n } = useTranslation();
  const [currentLanguage, setCurrentLanguage] = useState(i18n.language);
  const { isDarkMode, setIsDarkMode, theme } = useTheme();
  const [userInfo, setUserInfo] = useState({
    name: '',
    email: '',
    avatar: null,
    role: 'user' // Add role
  });

  const fetchUserInfo = async () => {
    try {
      const userInfoString = await AsyncStorage.getItem('userInfo');
      if (userInfoString) {
        const info = JSON.parse(userInfoString);
        console.log('DrawerNavigator userInfo:', info); // Debug log
        setUserInfo({
          name: info.name,
          email: info.email,
          avatar: info.avatar,
          role: info.role || 'user' // Get role from storage
        });
      }
    } catch (error) {
      console.error('Error getting user info:', error);
    }
  };

  // Add focus listener
  useEffect(() => {
    const unsubscribe = props.navigation.addListener('focus', fetchUserInfo);
    return unsubscribe;
  }, [props.navigation]);

  // Initial fetch
  useEffect(() => {
    fetchUserInfo();
  }, []);

  const handleLogout = () => {
    Alert.alert(
<<<<<<< HEAD
      t('logout'),  // "Đăng xuất"
      t('logoutConfirmMessage'), // Add this to translations: "Bạn có chắc chắn muốn đăng xuất?"
=======
      t('drawer.logout'),  // "Đăng xuất"
      t('drawer.logoutConfirmMessage'), // Add this to translations: "Bạn có chắc chắn muốn đăng xuất?"
>>>>>>> origin/dev
      [
        {
          text: t('common.cancel'), // "Hủy"
          style: 'cancel'
        },
        {
          text: t('logout'), // "Đăng xuất"
          style: 'destructive',
          onPress: async () => {
            try {
              await AsyncStorage.clear();
              props.navigation.reset({
                index: 0,
                routes: [{ name: 'Login' }],
              });
            } catch (error) {
              console.error('Logout error:', error);
              Alert.alert(t('common.error'), t('logoutError')); // Add to translations: "Có lỗi xảy ra khi đăng xuất"
            }
          }
        }
      ]
    );
  };

  // Common menu items for all users
  const commonMenuItems = () => (
    <>
      <DrawerItem
<<<<<<< HEAD
        label={t('home')}
=======
        label={t('drawer.home')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="home" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Home')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('profileName')}
=======
        label={t('drawer.profile')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="user" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Profile')}
      />
    </>
  );

  // Menu items for normal users
  const userMenuItems = () => (
    <>
     <DrawerItem
<<<<<<< HEAD
        label={t('cartName')}
=======
        label={t('drawer.cart')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="shopping-cart" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Cart')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('orderName')}
=======
        label={t('drawer.orders')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="list" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Order')}
      />
    </>
  );

  // Menu items for admin users
  const adminMenuItems = () => (
    <>
      <DrawerItem
<<<<<<< HEAD
        label={t('orderMana')}
=======
        label={t('drawer.orderManagement')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="list" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('OrderMana')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('account')}
=======
        label={t('drawer.accountManagement')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="users" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Account')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('productMana')}
=======
        label={t('drawer.productManagement')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="box" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Product')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('statistics')}
=======
        label={t('drawer.statistics')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="bar-chart-2" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Statistics')}
      />
    </>
  );

  // Settings items common to all users
  const settingsItems = () => (
    <>
      <DrawerItem
<<<<<<< HEAD
        label={t('settings')}
=======
        label={t('drawer.settings')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="settings" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Settings')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('support')}
=======
        label={t('drawer.support')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="help-circle" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('Support')}
      />
      <DrawerItem
<<<<<<< HEAD
        label={t('aboutUs')}
=======
        label={t('drawer.aboutUs')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="info" size={size} color={theme.textColor} />}
        onPress={() => props.navigation.navigate('AboutUs')}
      />
    </>
  );

  return (
    <DrawerContentScrollView
      {...props}
      style={{ backgroundColor: theme.backgroundColor }}
    >
      <View style={[styles.headerContainer, { backgroundColor: isDarkMode ? '#333' : '#f6f6f6' }]}>
        {userInfo.avatar ? (
          <Image
            source={{ uri: userInfo.avatar }}
            style={styles.avatar}
          />
        ) : (
          <View style={[styles.avatarPlaceholder, { backgroundColor: isDarkMode ? '#444' : '#ddd' }]}>
            <Feather name="user" size={30} color={isDarkMode ? '#666' : '#999'} />
          </View>
        )}
        <Text style={[styles.name, { color: theme.textColor }]}>{userInfo.name || 'User'}</Text>
        <Text style={[styles.email, { color: isDarkMode ? '#ccc' : 'gray' }]}>{userInfo.email || 'email@example.com'}</Text>
      </View>

      {/* hiển thị toàn bộ list nhanh nhất nhưng muốn custom thì không dùng được */}
      {/* <DrawerItemList {...props} /> */}

      {/* Common Menu Items */}
      {commonMenuItems()}

      {/* Role-based Menu Items */}
      {userInfo.role === 'admin' ? adminMenuItems() : userMenuItems()}

      {/* Section divider */}
      <View style={[styles.sectionDivider, { backgroundColor: isDarkMode ? '#333' : '#f6f6f6' }]}>
<<<<<<< HEAD
        <Text style={[styles.sectionText, { color: theme.textColor }]}>{t('other')}</Text>
=======
        <Text style={[styles.sectionText, { color: theme.textColor }]}>{t('drawer.other')}</Text>
>>>>>>> origin/dev
      </View>

      {/* Common Settings Items */}
      {settingsItems()}

<<<<<<< HEAD
      {/* đổi ngôn ngữ */}
      <View style={styles.darkModeContainer}>
        <View style={styles.darkModeRow}>
          <Feather name="globe" size={20} color={theme.textColor} />
          <Text style={[styles.darkModeText, { color: theme.textColor }]}>{t('language')}</Text>
          <Text style={[styles.languageText, { color: theme.textColor }]}>VI</Text>
          <TouchableOpacity 
            style={[
              styles.switchContainer,
              { backgroundColor: currentLanguage === 'vi' ? '#f4f4f4' : '#2d201c' }
            ]}
            onPress={() => {
              const newLang = currentLanguage === 'vi' ? 'en' : 'vi';
              i18n.changeLanguage(newLang);
              setCurrentLanguage(newLang);
            }}
          >
            <View style={[
              styles.switchKnob,
              { transform: [{ translateX: currentLanguage === 'vi' ? 0 : 28 }] }
            ]} />
          </TouchableOpacity>
          <Text style={[styles.languageText, { color: theme.textColor }]}>EN</Text>
        </View>
      </View>

      {/* đổi màu nền */}
      <View style={styles.darkModeContainer}>
        <View style={styles.darkModeRow}>
          <Feather name={isDarkMode ? "moon" : "sun"} size={20} color={theme.textColor} />
          <Text style={[styles.darkModeText, { color: theme.textColor }]}>{t('mode')}</Text>
          <Text style={[styles.languageText, { color: theme.textColor }]}>{t('lightMode')}</Text>
          <TouchableOpacity
            style={[
              styles.switchContainer,
              { backgroundColor: isDarkMode ? '#2d201c' : '#f4f4f4' }
            ]}
            onPress={() => setIsDarkMode(!isDarkMode)}
          >
            <View style={[
              styles.switchKnob,
              { transform: [{ translateX: isDarkMode ? 28 : 0 }] }
            ]} />
          </TouchableOpacity>
          <Text style={[styles.languageText, { color: theme.textColor }]}>{t('darkMode')}</Text>
        </View>
      </View>

      {/* Section divider */}
      <View style={[styles.sectionDivider, { backgroundColor: isDarkMode ? '#333' : '#f6f6f6' }]}>
        <Text style={[styles.sectionText, { color: theme.textColor }]}>{t('exit')}</Text>
      </View>

      <DrawerItem
        label={t('logout')}
=======
      {/* Section divider */}
      <View style={[styles.sectionDivider, { backgroundColor: isDarkMode ? '#333' : '#f6f6f6' }]}>
        <Text style={[styles.sectionText, { color: theme.textColor }]}>{t('drawer.exit')}</Text>
      </View>

      <DrawerItem
        label={t('drawer.logout')}
>>>>>>> origin/dev
        labelStyle={{ color: theme.textColor }}
        icon={({ size }) => <Feather name="log-out" size={size} color={theme.textColor} />}
        onPress={handleLogout} // Use the new handleLogout function
      />

    </DrawerContentScrollView>
  );
};

const DrawerNavigator = () => {
  const { t } = useTranslation();
  const { theme, isDarkMode } = useTheme();

  return (
    <Drawer.Navigator
      drawerContent={(props) => <CustomDrawerContent {...props} />}
      screenOptions={{
        headerStyle: {
          backgroundColor: theme.backgroundColor,
        },
        headerTintColor: theme.textColor,
        drawerActiveBackgroundColor: isDarkMode ? '#333' : '#f4f5f6',
        drawerActiveTintColor: theme.textColor,
        drawerInactiveTintColor: theme.textColor,
        drawerStyle: {
          backgroundColor: theme.backgroundColor,
        }
      }}
    >
      <Drawer.Screen
        // thêm bottom tabs
        // name="MainTabs" 
        // component={TabNavigator}
        // không có bottom tabs
        name="Home"
        component={HomeScreen}
        options={{
          headerShown: false,
<<<<<<< HEAD
          title: t('home'),
          drawerLabel: t('home'),
=======
          title: t('drawer.home'),
          drawerLabel: t('drawer.home'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="home" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="Cart"
        component={CartScreen}
        options={{
<<<<<<< HEAD
          title: t('cartName'),
          drawerLabel: t('cart'),
=======
          title: t('drawer.cart'),
          drawerLabel: t('drawer.cart'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="shopping-cart" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="Order"
        component={OrderScreen}
        options={{
<<<<<<< HEAD
          title: t('orderName'),
          drawerLabel: t('order'),
=======
          title: t('drawer.orders'),
          drawerLabel: t('drawer.orders'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="file-text" size={24} color={theme.textColor} />
        }}
      />
      <Drawer.Screen
        name="Profile"
        component={ProfileScreen}
        options={{
<<<<<<< HEAD
          title: t('profileName'),
          drawerLabel: t('profile'),
=======
          title: t('drawer.profile'),
          drawerLabel: t('drawer.profile'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="user" size={24} color={theme.textColor} />
        }}
      />
      <Drawer.Screen
        name="OrderMana"
        component={OrderManagerScreen}
        options={{
<<<<<<< HEAD
          title: t('orderMana'),
          drawerLabel: t('orderMana'),
=======
          title: t('drawer.orderManagement'),
          drawerLabel: t('drawer.orderManagement'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="user" size={24} color={theme.textColor} />
        }}
      />
      <Drawer.Screen
        name="Account"
        component={AccountScreen}
        options={{
<<<<<<< HEAD
          title: t('account'),
          drawerLabel: t('account'),
=======
          title: t('drawer.accountManagement'),
          drawerLabel: t('drawer.accountManagement'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="user" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="Product"
        component={ProductScreen}
        options={{
<<<<<<< HEAD
          title: t('product'),
          drawerLabel: t('product'),
=======
          title: t('drawer.productManagement'),
          drawerLabel: t('drawer.productManagement'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="box" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="Statistics"
        component={StatisticsScreen}
        options={{
<<<<<<< HEAD
          title: t('statistics'),
          drawerLabel: t('statistics'),
=======
          title: t('drawer.statistics'),
          drawerLabel: t('drawer.statistics'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="bar-chart-2" size={24} color="black" />
        }}
      />

      <Drawer.Screen
        name="Settings"
        component={SettingsScreen}
        options={{
<<<<<<< HEAD
          title: t('settings'),
          drawerLabel: t('settings'),
=======
          title: t('drawer.settings'),
          drawerLabel: t('drawer.settings'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="settings" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="Support"
        component={SupportScreen}
        options={{
<<<<<<< HEAD
          title: t('support'),
          drawerLabel: t('support'),
=======
          title: t('drawer.support'),
          drawerLabel: t('drawer.support'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="help-circle" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="AboutUs"
        component={AboutUsScreen}
        options={{
<<<<<<< HEAD
          title: t('aboutUs'),
          drawerLabel: t('aboutUs'),
=======
          title: t('drawer.aboutUs'),
          drawerLabel: t('drawer.aboutUs'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="info" size={24} color="black" />
        }}
      />
      <Drawer.Screen
        name="Logout"
        component={LogoutScreen}
        options={{
<<<<<<< HEAD
          title: t('logout'),
          drawerLabel: t('logout'),
=======
          title: t('drawer.logout'),
          drawerLabel: t('drawer.logout'),
>>>>>>> origin/dev
          drawerIcon: () => <Feather name="log-out" size={24} color="black" />,
          onPress: () => {
            navigation.navigate('Login');
          }
        }}
      />
    </Drawer.Navigator>
  );
};

const styles = StyleSheet.create({
  headerContainer: {
    padding: 16,
    backgroundColor: '#f6f6f6',
    marginBottom: 20,
  },
  avatar: {
    width: 60,
    height: 60,
    borderRadius: 30,
  },
  avatarPlaceholder: {
    width: 60,
    height: 60,
    borderRadius: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 8,
  },
  email: {
    fontSize: 14,
    color: 'gray',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 10,
    backgroundColor: '#f6f6f6',
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  sectionDivider: {
    paddingHorizontal: 16,
    paddingVertical: 8,
    marginVertical: 8,
    backgroundColor: '#f6f6f6',
  },
  sectionText: {
    fontSize: 14,
    color: 'gray',
    fontWeight: '500',
  },
  languageSwitchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 10,
  },
  switchContainer: {
    width: 56,
    height: 28,
    borderRadius: 14,
    padding: 2,
    marginHorizontal: 10,
  },
  switchKnob: {
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: 'white',
  },
  languageText: {
    fontSize: 14,
    fontWeight: '500',
    color: '#2d201c',
  },
  darkModeContainer: {
    paddingHorizontal: 16,
    paddingVertical: 8,
  },
  darkModeRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingVertical: 8,
  },
  darkModeText: {
    fontSize: 14,
    color: '#2d201c',
    flex: 1,
    marginLeft: 10,
  },
});

export default DrawerNavigator;
