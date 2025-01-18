package fpoly.dungnm.book_selling_app.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fpoly.dungnm.book_selling_app.DAO.UserDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelUser;
import fpoly.dungnm.book_selling_app.screens.ScreensActivity;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private UserDAO userDAO;
    String checkRole = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view
        edtEmail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);


        // Xử lý sự kiện cho nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        // Xử lý sự kiện cho TextView "Đăng ký"
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình đăng ký
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra nếu email hoặc password để trống
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email không được để trống");
            edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Mật khẩu không được để trống");
            edtPassword.requestFocus();
            return;
        }

        // Kiểm tra nếu email không hợp lệ
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return;
        }

        ModelUser user = null;
        userDAO = new UserDAO(this);
        user = userDAO.checkLogin(email,password);
        if(!(user == null)){
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, ScreensActivity.class);
            if (user.getEmail().equals("admin@gmail.com")) {
                checkRole = "1";
                intent.putExtra("role",checkRole );
            } else {
                checkRole = "0";
                intent.putExtra("role",checkRole );
            }
            SharedPreferences preferences = getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("EMAIL",email);
            editor.putString("PASSWORD",password);
            editor.putInt("USER_ID", user.getId());
            editor.apply();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

}