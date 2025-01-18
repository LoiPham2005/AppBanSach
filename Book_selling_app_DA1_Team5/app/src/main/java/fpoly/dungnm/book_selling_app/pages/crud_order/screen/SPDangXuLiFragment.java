package fpoly.dungnm.book_selling_app.pages.crud_order.screen;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.OrderDAO;
import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterHomeProducts;
import fpoly.dungnm.book_selling_app.adapter.AdapterOrder;
import fpoly.dungnm.book_selling_app.models.ModelOrder;
import fpoly.dungnm.book_selling_app.models.ModelProducts;

public class SPDangXuLiFragment extends Fragment {
RecyclerView rcvOrder;
    AdapterOrder adapter;
    ArrayList<ModelOrder> listOrders = new ArrayList<>();
    OrderDAO orderDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_s_p_dang_xu_li, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvOrder = view.findViewById(R.id.rcvOrder);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
        int USER_ID = sharedPreferences.getInt("USER_ID", -1);


        orderDAO = new OrderDAO(getContext());
        listOrders = orderDAO.getAllOrders(USER_ID);

        // Thiết lập RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rcvOrder.setLayoutManager(manager);

        adapter = new AdapterOrder(getContext(), listOrders);

        rcvOrder.setAdapter(adapter);

    }
}