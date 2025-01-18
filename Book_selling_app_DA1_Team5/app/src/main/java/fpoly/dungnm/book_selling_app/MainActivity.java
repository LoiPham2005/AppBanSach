package fpoly.dungnm.book_selling_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.dungnm.book_selling_app.DAO.UserDAO;
import fpoly.dungnm.book_selling_app.login.LoginActivity;
import fpoly.dungnm.book_selling_app.models.ModelUser;
import fpoly.dungnm.book_selling_app.screens.ScreensActivity;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startButton = findViewById(R.id.startButton);

        String checkEmail;
        String checkPass;
        SharedPreferences preferences = getSharedPreferences("CHECK_LOGIN" , Context.MODE_PRIVATE);
        checkEmail = preferences.getString("EMAIL","1");
        checkPass = preferences.getString("PASSWORD","");
        if ("0".equals(checkEmail)) {
            // Thông báo phiên đăng nhập hết hạn
            Toast.makeText(getApplicationContext(), "Phiên đăng nhập hết hạn hãy đăng nhập lại", Toast.LENGTH_LONG).show();
        } else if ("1".equals(checkEmail)) {
            Toast.makeText(getApplicationContext(), "Wellcome!", Toast.LENGTH_LONG).show();
        } else {
            // Gọi phương thức checkLogin với email và password từ SharedPreferences
            UserDAO userDAO = new UserDAO(this);
            ModelUser user = userDAO.checkLogin(checkEmail, checkPass);
            if (user != null) {
                // Xử lý khi thông tin đăng nhập chính xác
                startButton.setVisibility(View.GONE);
                String checkRole = "1";
                if (checkEmail.equals("admin@gmail.com")) {
                    checkRole = "1"; // Admin
                } else {
                    checkRole = "0"; // Người dùng
                }
                String finalCheckRole = checkRole;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ScreensActivity.class);
                        intent.putExtra("role", finalCheckRole);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            } else {
                Toast.makeText(getApplicationContext(), "Phiên đăng nhập hết hạn hãy đăng nhập lại", Toast.LENGTH_LONG).show();
            }
        }
    }
    // Phương thức được gọi khi nhấn nút "Bắt đầu ngay"
    public void batDauNgay(View view) {
        // Tạo Intent để chuyển đến LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}