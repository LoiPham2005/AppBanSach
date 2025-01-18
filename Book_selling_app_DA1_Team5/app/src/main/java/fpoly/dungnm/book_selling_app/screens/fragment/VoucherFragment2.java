package fpoly.dungnm.book_selling_app.screens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.VoucherDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterVoucher;
import fpoly.dungnm.book_selling_app.models.ModelVoucher;


public class VoucherFragment2 extends Fragment {
    private FloatingActionButton btnInsertVoucher;
    private ImageView btnBackVoucher;
    private RecyclerView rvVoucher;
    private ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
    private VoucherDAO voucherDAO;
    private AdapterVoucher adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voucher2, container, false);
    }
}