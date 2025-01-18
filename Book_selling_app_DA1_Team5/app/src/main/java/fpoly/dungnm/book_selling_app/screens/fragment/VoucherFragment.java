package fpoly.dungnm.book_selling_app.screens.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import fpoly.dungnm.book_selling_app.DAO.VoucherDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterVoucher;
import fpoly.dungnm.book_selling_app.models.ModelVoucher;


public class VoucherFragment extends Fragment {
    private FloatingActionButton btnInsertVoucher;
    private ImageView btnBackVoucher;
    private RecyclerView rvVoucher;
    private ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
    private VoucherDAO voucherDAO;
    private AdapterVoucher adapter;

    public VoucherFragment() {
        // Required empty public constructor
    }

    public static VoucherFragment newInstance() {
        VoucherFragment fragment = new VoucherFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);
        btnInsertVoucher = view.findViewById(R.id.btnInsertVoucher);
        btnBackVoucher = view.findViewById(R.id.btnBackVoucher);
        rvVoucher = view.findViewById(R.id.rvVoucher);

        voucherDAO = new VoucherDAO(getContext());
        listVoucher = voucherDAO.getAllVoucher();
        if (listVoucher.size() == 0) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            loadData();
        }

        btnBackVoucher.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        btnInsertVoucher.setOnClickListener(v -> {
            openDialogInsertVoucher();
        });


        return view;
    }

    private void openDialogInsertVoucher() {
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.layout_dialog_add_voucher, null);
        EditText edContent = dialog.findViewById(R.id.edContent);
        EditText edDiscount = dialog.findViewById(R.id.edDiscount);
        EditText edStartDate = dialog.findViewById(R.id.edStartDate);
        EditText edEndDate = dialog.findViewById(R.id.edEndDate);
        RadioButton rdDiscount = dialog.findViewById(R.id.rdDiscount);
        RadioButton rdShip = dialog.findViewById(R.id.rdShip);

        Calendar calendar = Calendar.getInstance();
        edStartDate.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                edStartDate.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Sự kiện chọn ngày cho End Date
        edEndDate.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                edEndDate.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialog);

        builder.setPositiveButton("Thêm", (dialog1, which) -> {
            String content = edContent.getText().toString();
            String discount = edDiscount.getText().toString();
            String startDate = edStartDate.getText().toString();
            String endDate = edEndDate.getText().toString();
            String type ="";
            if (rdDiscount.isChecked()) {
                type = "discount";
            } else {
                type = "ship";
            }
            if (content.isEmpty() || discount.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                int discountInt = Integer.parseInt(discount);
                ModelVoucher voucher = new ModelVoucher(type,discountInt,  content , startDate, endDate);
                if(voucherDAO.insertVoucher(voucher)) {
                    Toast.makeText(getContext(), "Thêm thành công" +type, Toast.LENGTH_SHORT).show();
                    listVoucher.clear();
                    listVoucher = voucherDAO.getAllVoucher();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", (dialog1, which) -> {
            dialog1.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

    private void loadData() {
        listVoucher.clear();
        listVoucher = voucherDAO.getAllVoucher();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvVoucher.setLayoutManager(manager);
        adapter = new AdapterVoucher(getContext(), listVoucher);
        rvVoucher.setAdapter(adapter);
    }
}