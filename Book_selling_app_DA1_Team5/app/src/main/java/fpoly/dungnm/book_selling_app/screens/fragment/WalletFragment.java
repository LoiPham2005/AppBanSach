package fpoly.dungnm.book_selling_app.screens.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.dungnm.book_selling_app.DAO.WalletDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelWallet;


public class WalletFragment extends Fragment {

    private RelativeLayout itemWallet;
    private TextView tvBalance;
    private WalletDAO walletDAO;
    private ImageView imgBackProfile;
    private int USER_ID;
    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.thanhtoan, container, false);
        itemWallet = view.findViewById(R.id.itemWallet);
        tvBalance = view.findViewById(R.id.tvBalance);
        imgBackProfile = view.findViewById(R.id.imgBackProfile);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
        USER_ID = sharedPreferences.getInt("USER_ID", -1);

        walletDAO = new WalletDAO(getContext());
//        ModelWallet wallet = walletDAO.getWalletByUserId(USER_ID);
//        if (wallet == null) {
//            if (walletDAO.insertWallet(new ModelWallet(USER_ID, 0))) {
//                wallet = walletDAO.getWalletByUserId(USER_ID);
//            } else {
//                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
//                return null;
//            }
//        }
//        tvBalance.setText(""+wallet.getBalance());

        itemWallet.setOnClickListener(v -> {
            openDialogUpdateWallet();
        });

        imgBackProfile.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        return view;
    }

    private void openDialogUpdateWallet() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_update_wallet, null);

        EditText edtBalance = view.findViewById(R.id.edtBalance);

        edtBalance.setText(""+tvBalance.getText());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cập nhật số dư");
        builder.setView(view);
        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String balance = edtBalance.getText().toString();
            if (balance.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập số dư", Toast.LENGTH_SHORT).show();
            } else {
//                if (walletDAO.updateWallet(new ModelWallet(USER_ID, Integer.parseInt(balance)))) {
//                    tvBalance.setText(balance);
//                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();

    }
}