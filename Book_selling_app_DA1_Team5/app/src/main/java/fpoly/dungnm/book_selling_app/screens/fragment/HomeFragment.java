package fpoly.dungnm.book_selling_app.screens.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterHomeProducts;
import fpoly.dungnm.book_selling_app.adapter.AdapterProducts;
import fpoly.dungnm.book_selling_app.models.ModelProducts;


public class HomeFragment extends Fragment  {
    RecyclerView rcvHome;
    AdapterHomeProducts adapter;
    ArrayList<ModelProducts> listProducts = new ArrayList<>();
    ProductDAO productDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvHome = view.findViewById(R.id.rcvHome);

        productDAO = new ProductDAO(getContext());
        listProducts = productDAO.getAllProducts();

        // Thiết lập RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rcvHome.setLayoutManager(manager);

        adapter = new AdapterHomeProducts(getContext(), listProducts);

        rcvHome.setAdapter(adapter);

        dialogAskExit();
    }

    private void dialogAskExit() {
        // Xử lý sự kiện khi nhấn nút "Back"
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        // Hiển thị dialog xác nhận thoát
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Thoát ứng dụng")
                                .setMessage("Bạn có chắc chắn muốn thoát không?")
                                .setPositiveButton("Có", (dialog, which) -> requireActivity().finish())
                                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
    }

}