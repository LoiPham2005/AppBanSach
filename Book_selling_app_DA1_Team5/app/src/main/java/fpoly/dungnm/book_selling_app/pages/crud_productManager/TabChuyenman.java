package fpoly.dungnm.book_selling_app.pages.crud_productManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fpoly.dungnm.book_selling_app.pages.crud_productManager.screen.HangBiHoanFragment;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.screen.SanPhamFragment;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.screen.TonKhoFragment;

public class TabChuyenman extends FragmentStateAdapter {
    public static String[] item = {"Sản Phẩm","Hàng bị hoàn", "Tồn Kho"};
    public TabChuyenman(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        if(position == 0 ){
            fragment = new SanPhamFragment();
        } else if (position ==1) {
            fragment = new HangBiHoanFragment();
        }
        else if (position ==2) {
            fragment = new TonKhoFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return item.length;
    }
}
