import * as React from 'react';
import { View, useWindowDimensions, StyleSheet } from 'react-native';
import { TabView, SceneMap, TabBar } from 'react-native-tab-view';
import TabInventory from './inventory/TabInventory';
import TabReturn from './return/TabReturn';
import TabProducts from './product_crud/TabProducts';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../../context/ThemeContext';

// SceneMap sử dụng các hàm đã định nghĩa
const renderScene = SceneMap({
  first: TabProducts,
  center: TabInventory,
  // second: TabReturn,
});

export default function TabViewExample() {
  const layout = useWindowDimensions();
  const [index, setIndex] = React.useState(0);
  const { t } = useTranslation();
  const { theme, isDarkMode } = useTheme();

  // Routes cho TabView
  const routes = [
    { key: 'first', title: t('product') },
    { key: 'center', title: t('inventory') },
    // { key: 'second', title: t('return') },
  ];

  // Tùy chỉnh TabBar
  const renderTabBar = (props) => (
    <TabBar
      {...props}
      style={{
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
      }}
    />
  );

  return (
    <TabView
      navigationState={{ index, routes }}
      renderScene={renderScene}
      onIndexChange={setIndex}
      initialLayout={{ width: layout.width }}
      renderTabBar={renderTabBar} // Thêm TabBar tùy chỉnh
    />
  );
}

const styles = StyleSheet.create({
  scene: {
    flex: 1,
  },
});
