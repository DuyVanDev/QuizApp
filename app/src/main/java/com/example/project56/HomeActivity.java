package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    TextView userName;

    //SharedPreferences để lưu trữ phiên đăng nhập dưới dạng key-value
    boolean loggedIn = false;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor; //Editor để chỉnh sửa và lưu trữ dữ liệu trong SharedPreferences.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        userName = findViewById(R.id.tvUsernameHome);
        userName.setText(name);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_logout:
                    // Tạo một đối tượng AlertDialog.Builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Chuyển tài khoản");
                    builder.setMessage("Bạn có muốn đăng xuất không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Khi người dùng nhấn Có, thoát khỏi Activity
                            HomeActivity.this.finish();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Khi người dùng nhấn Không, đóng hộp thoại
                            dialog.dismiss();
                        }
                    });

                    // Hiển thị hộp thoại
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", 0).edit();
                    editor.clear();
                    editor.apply();
                    //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    //finish();

                    return true;
                case R.id.bottom_profile:
                    /*startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();*/
                    return true;
            }
            return false;
        });

    }

    public void logout(View view) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", 0).edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        // Tạo một đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát ứng dụng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Khi người dùng nhấn Có, thoát khỏi Activity
                HomeActivity.this.finish();


            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Khi người dùng nhấn Không, đóng hộp thoại
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ltw:
                intent = new Intent(HomeActivity.this, StartActivity.class);
                intent.putExtra("categoryId", 1);
                intent.putExtra("categoryName", "Lập trình web");
                startActivity(intent);
                break;
            case R.id.hdt:
                intent = new Intent(HomeActivity.this, StartActivity.class);
                intent.putExtra("categoryId", 2);
                intent.putExtra("categoryName", "Hướng đối tượng");

                startActivity(intent);
                break;

            default:
                Toast.makeText(HomeActivity.this, "a", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}