package fpoly.dungnm.book_selling_app.pages.crud_frofile.crud_wallet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.WalletDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterWallet;
import fpoly.dungnm.book_selling_app.models.ModelAddres;
import fpoly.dungnm.book_selling_app.models.ModelWallet;

public class WalletActivity extends AppCompatActivity {
    RecyclerView rvWallet;
    AdapterWallet adapterWallet;
    ArrayList<ModelWallet> listWallet = new ArrayList<>();
    WalletDAO walletDAO;
    ImageView imgBackProfile;
    LinearLayout llAddMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wallet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Khởi tạo DAO và các thành phần giao diện
        walletDAO = new WalletDAO(this);
        rvWallet = findViewById(R.id.rvWallet);
        imgBackProfile = findViewById(R.id.imgBackProfile);
        llAddMethod = findViewById(R.id.llAddMethod);

        // Lấy dữ liệu từ cơ sở dữ liệu
        listWallet = (ArrayList<ModelWallet>) walletDAO.getAllPay();

        // Cấu hình RecyclerView và Adapter
        rvWallet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterWallet = new AdapterWallet(this, listWallet);
        rvWallet.setAdapter(adapterWallet);

        // Quay lại màn hình trước đó
        imgBackProfile.setOnClickListener(v -> finish());

        // Thêm ví mặc định
        llAddMethod.setOnClickListener(v -> {
            ModelWallet defaultWallet = new ModelWallet(200000);
            defaultWallet.setId(1) ;// ID duy nhất
            defaultWallet.setPay(200000); // Giá trị mặc định
            walletDAO.insertPay(defaultWallet); // Thêm vào cơ sở dữ liệu
            refreshWalletList(); // Cập nhật lại danh sách
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshWalletList(); // Tải lại dữ liệu khi quay lại màn hình
    }

    // Phương thức làm mới danh sách ví
    private void refreshWalletList() {
        listWallet = (ArrayList<ModelWallet>) walletDAO.getAllPay();
        adapterWallet.updateData(listWallet);
    }


}