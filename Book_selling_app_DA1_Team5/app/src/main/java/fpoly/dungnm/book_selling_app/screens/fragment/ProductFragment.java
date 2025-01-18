package fpoly.dungnm.book_selling_app.screens.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.TabChuyenman;


public class ProductFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TabChuyenman tabChuyenman;
    ImageView imgBackProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager2);
        imgBackProduct = view.findViewById(R.id.imgBackProduct);

        tabChuyenman = new TabChuyenman(getActivity());
        viewPager2.setAdapter(tabChuyenman);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, i) -> {
            tab.setText(TabChuyenman.item[i]);
        }).attach();

        imgBackProduct.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }
}