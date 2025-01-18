package fpoly.dungnm.book_selling_app.screens.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.CartDAO;
import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterCart;
import fpoly.dungnm.book_selling_app.adapter.AdapterProducts;
import fpoly.dungnm.book_selling_app.models.ModelCart;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.pages.order_payment.OrderPaymentActivity;


public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    private TextView tvTotalPrice;
    AdapterCart adapter;
    ArrayList<ModelCart> listCart = new ArrayList<>();
    ImageView imgBackCart;
    CartDAO cartDAO;
    Button btnContinue;
    double totalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        imgBackCart = view.findViewById(R.id.imgBackCart);
        btnContinue = view.findViewById(R.id.btnCheckout);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
        int USER_ID = sharedPreferences.getInt("USER_ID", -1);

        cartDAO = new CartDAO(getContext());
        listCart = cartDAO.getAllCart(USER_ID);

        // Cập nhật tổng giá tiền
        updateTotalPrice();

        // Thiết lập RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adapter = new AdapterCart(getContext(), listCart);

        recyclerView.setAdapter(adapter);

        // Update the total price when there is a change in the cart items
        adapter.setOnItemClickListener(this::updateTotalPrice);

        // Cập nhật tổng giá tiền khi có sự thay đổi trong danh sách sản phẩm
        adapter.setOnItemClickListener(new AdapterCart.OnItemClickListener() {
            @Override
            public void onItemClick() {
                updateTotalPrice();
            }
        });

        imgBackCart.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
//        btnContinue.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), OrderPaymentActivity.class));
//        });

        btnContinue.setOnClickListener(v -> {
            ArrayList<ModelCart> selectedProducts = new ArrayList<>();

            // Lấy các sản phẩm đã được chọn
            for (ModelCart cart : listCart) {
                if (cart.isSelected()) {
                    selectedProducts.add(cart);
                }
            }

            // Kiểm tra nếu không có sản phẩm nào được chọn
            if (selectedProducts.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn ít nhất một sản phẩm!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển danh sách sản phẩm đã chọn sang màn hình thanh toán
            Intent intent = new Intent(getContext(), OrderPaymentActivity.class);
            intent.putParcelableArrayListExtra("selectedCarts", selectedProducts);
            startActivity(intent);
        });

    }

    private void updateTotalPrice() {
        totalPrice = 0;
        for (ModelCart cart : listCart) {
            totalPrice += cart.getAmount();
        }
        tvTotalPrice.setText(String.format("%,.0f", totalPrice )+ "VNĐ");
    }
}