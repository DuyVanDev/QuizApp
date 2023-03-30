package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText txtusername, txtpassword, txtconfirmpassword;
    Button btnSignUp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        txtusername = (EditText)findViewById(R.id.username);
        txtpassword = (EditText)findViewById(R.id.password);
        txtconfirmpassword = (EditText)findViewById(R.id.confirmpassword);

        databaseHelper = new DatabaseHelper(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các trường nhập liệu
                String username = txtusername.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                String confirmPassword = txtconfirmpassword.getText().toString().trim();

                // Kiểm tra thông tin nhập liệu
                if (TextUtils.isEmpty(username)) {
                   txtusername.setError("Tên đăng nhập không được để trống");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    txtpassword.setError("Mật khẩu không được để trống");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    txtconfirmpassword.setError("Mật khẩu xác nhận không đúng");
                    return;
                }

                // Thêm người dùng vào CSDL
                long result = databaseHelper.addUser(username, password);
                if (result > 0) {
                    // Đăng ký thành công, chuyển về trang đăng nhập
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                } else {
                    // Đăng ký không thành công
                    Toast.makeText(SignUpActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void tVLogin_onClick(View view) {
    }
}