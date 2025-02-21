<<<<<<< HEAD
import * as React from 'react';
import { View, useWindowDimensions, StyleSheet } from 'react-native';
import { TabView, SceneMap, TabBar } from 'react-native-tab-view';
import TabInventory from './inventory/TabInventory';
import TabReturn from './return/TabReturn';
=======
import React from 'react';
import { View, useWindowDimensions, StyleSheet } from 'react-native';
import { TabView, SceneMap, TabBar } from 'react-native-tab-view';
import TabInventory from './inventory/TabInventory';
>>>>>>> origin/dev
import TabProducts from './product_crud/TabProducts';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../../context/ThemeContext';

<<<<<<< HEAD
// SceneMap sử dụng các hàm đã định nghĩa
const renderScene = SceneMap({
  first: TabProducts,
  center: TabInventory,
  // second: TabReturn,
});

export default function TabViewExample() {
=======
const renderScene = SceneMap({
  first: TabProducts,
  center: TabInventory,
});

const ProductScreen = () => {
>>>>>>> origin/dev
  const layout = useWindowDimensions();
  const [index, setIndex] = React.useState(0);
  const { t } = useTranslation();
  const { theme, isDarkMode } = useTheme();

<<<<<<< HEAD
  // Routes cho TabView
  const routes = [
    { key: 'first', title: t('product') },
    { key: 'center', title: t('inventory') },
    // { key: 'second', title: t('return') },
  ];

  // Tùy chỉnh TabBar
=======
  const routes = [
    { key: 'first', title: t('productManagement.products') },
    { key: 'center', title: t('productManagement.inventory') }
  ];

>>>>>>> origin/dev
  const renderTabBar = (props) => (
    <TabBar
      {...props}
      style={{
<<<<<<< HEAD
        backgroundColor: theme.backgroundColor, // Màu nền của TabBar
      }}
      indicatorStyle={{
        backgroundColor: 'red', // Màu của đường chỉ báo dưới tab
        // height: 3, // Độ dày của chỉ báo
      }}
      activeColor={theme.textColor} // Màu của text tab được chọn
      inactiveColor={isDarkMode ? '#888' : '#aaa'} // Màu của text tab không được chọn
      labelStyle={{
        fontSize: 14, // Kích thước text
        fontWeight: '600', // Độ dày text
=======
        backgroundColor: theme.backgroundColor,
      }}
      indicatorStyle={{
        backgroundColor: 'red',
      }}
      activeColor={theme.textColor}
      inactiveColor={isDarkMode ? '#888' : '#aaa'}
      labelStyle={{
        fontSize: 14,
        fontWeight: '600',
>>>>>>> origin/dev
      }}
    />
  );

  return (
    <TabView
      navigationState={{ index, routes }}
      renderScene={renderScene}
      onIndexChange={setIndex}
      initialLayout={{ width: layout.width }}
<<<<<<< HEAD
      renderTabBar={renderTabBar} // Thêm TabBar tùy chỉnh
    />
  );
}
=======
      renderTabBar={renderTabBar}
    />
  );
};
>>>>>>> origin/dev

const styles = StyleSheet.create({
  scene: {
    flex: 1,
  },
});
<<<<<<< HEAD
=======

export default ProductScreen;
>>>>>>> origin/dev
