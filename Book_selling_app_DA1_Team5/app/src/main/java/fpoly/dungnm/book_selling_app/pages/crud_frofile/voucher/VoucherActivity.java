package fpoly.dungnm.book_selling_app.pages.crud_frofile.voucher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.dungnm.book_selling_app.R;

public class VoucherActivity extends AppCompatActivity {
    Button btnUseVoucher,btnUseVoucher2 ;
    ImageView imgBackVoucher;
    TextView tvSale50,tvFreeShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnUseVoucher = findViewById(R.id.btnUseVoucher);
        btnUseVoucher2 = findViewById(R.id.btnUseVoucher2);
        imgBackVoucher = findViewById(R.id.imgBackVoucher);
        tvSale50 = findViewById(R.id.tvDiscount);
        tvFreeShip = findViewById(R.id.tvDiscount2);

        imgBackVoucher.setOnClickListener(v -> {
            finish();
        });
        btnUseVoucher.setOnClickListener(v -> {
            // Trả danh sách sản phẩm về
            Intent resultIntent = new Intent();
            resultIntent.putExtra("namesale", tvSale50.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnUseVoucher2.setOnClickListener(v -> {
            // Trả danh sách sản phẩm về
            Intent resultIntent = new Intent();
            resultIntent.putExtra("namesale", tvFreeShip.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }
}