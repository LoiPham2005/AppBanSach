package fpoly.dungnm.book_selling_app.pages.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.dungnm.book_selling_app.MainActivity;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.screens.ScreensActivity;
import fpoly.dungnm.book_selling_app.screens.fragment.HomeFragment;

public class InvoiceActivity extends AppCompatActivity {
    Button btnContinueHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnContinueHome = findViewById(R.id.btnContinueHome);

        btnContinueHome.setOnClickListener(v -> {
            // Chuyển về ScreensActivity
            Intent intent = new Intent(InvoiceActivity.this, ScreensActivity.class);
            intent.putExtra("role", "0"); // Truyền thông tin role nếu cần thiết
            startActivity(intent);
            finish(); // Kết thúc InvoiceActivity để tránh quay lại màn hình này
        });
    }
}