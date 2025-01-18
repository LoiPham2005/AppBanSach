package fpoly.dungnm.book_selling_app.login;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fpoly.dungnm.book_selling_app.DAO.UserDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelUser;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText edtFullname, edtEmail, edtPassword, edtConfirmPassword, edtPhone, edtAddress;
    private Button btnSignUp;
    private TextView tvLoginFinish;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        edtFullname = findViewById(R.id.etUsername);
        edtEmail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        edtConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginFinish = findViewById(R.id.tvLoginFinish);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);

        tvLoginFinish.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            registerUser();
        });
    }

    private void registerUser() {
        String username = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        // Validate inputs
        if (username.isEmpty()) {
            edtFullname.setError("Họ tên không được để trống");
            edtFullname.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email không được để trống");
            edtEmail.requestFocus();
            return;
        }
        // Kiểm tra nếu email không hợp lệ
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Mật khẩu không được để trống");
            edtPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            edtConfirmPassword.setError("Xác nhận mật khẩu không được để trống");
            edtConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Xac nhan mật khẩu không được để trống");
            edtConfirmPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            edtPhone.setError("Số điện thoại không được để trống");
            edtPhone.requestFocus();
            return;
        }
        if (phone.length() != 10) {
            edtPhone.setError("Số điện thoại phải có 10 chữ số");
            edtPhone.requestFocus();
            return;
        }else {
            try {
                Integer.parseInt(phone);
            } catch (NumberFormatException e) {
                edtPhone.setError("Số điện thoại phải là số");
                edtPhone.requestFocus();
                return;
            }
            if (address.isEmpty()) {
                edtAddress.setError("Địa chỉ không được để trống");
                edtAddress.requestFocus();
                return;
            }

            // dang ky
            ModelUser user = new ModelUser();
            user.setFullname(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(Integer.parseInt(phone));
            user.setAddress(address);
            user.setStatus(1);

            userDAO = new UserDAO(RegisterActivity.this);
            if (userDAO.insertUser(user)) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}