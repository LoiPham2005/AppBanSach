package fpoly.dungnm.book_selling_app.pages.crud_order;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fpoly.dungnm.book_selling_app.pages.crud_order.screen.SPDaGiaoFragment;
import fpoly.dungnm.book_selling_app.pages.crud_order.screen.SPDangGiaoFragment;
import fpoly.dungnm.book_selling_app.pages.crud_order.screen.SPDangXuLiFragment;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.screen.HangBiHoanFragment;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.screen.SanPhamFragment;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.screen.TonKhoFragment;

public class TabChuyenman_Order extends FragmentStateAdapter {
    public static String[] item = {"Đang xử lí","Đang giao", "Đã giao"};
    public TabChuyenman_Order(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        if(position == 0 ){
            fragment = new SPDangXuLiFragment();
        } else if (position ==1) {
            fragment = new SPDangGiaoFragment();
        }else if (position ==2) {
            fragment = new SPDaGiaoFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return item.length;
    }
}
