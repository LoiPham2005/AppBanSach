package fpoly.dungnm.book_selling_app.screens.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterProducts;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.pages.crud_order.TabChuyenman_Order;
import fpoly.dungnm.book_selling_app.pages.crud_productManager.TabChuyenman;

public class OrderFragment extends Fragment {
    TabLayout tabLayoutOrder;
    ViewPager2 viewPager2oder;
    TabChuyenman_Order tabChuyenman;
    ImageView imgBackProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayoutOrder = view.findViewById(R.id.tabLayoutOrder);
        viewPager2oder = view.findViewById(R.id.viewPager2oder);
        imgBackProduct = view.findViewById(R.id.imgBackProduct);

        tabChuyenman = new TabChuyenman_Order(getActivity());
        viewPager2oder.setAdapter(tabChuyenman);

        new TabLayoutMediator(tabLayoutOrder, viewPager2oder, (tab, i) -> {
            tab.setText(TabChuyenman_Order.item[i]);
        }).attach();

        imgBackProduct.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

    }
}