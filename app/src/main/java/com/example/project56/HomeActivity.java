package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        userName = findViewById(R.id.tvUsernameHome);
        userName.setText(name);

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ltw:
                intent = new Intent(HomeActivity.this, StartActivity.class);
                intent.putExtra("categoryId",1);
                intent.putExtra("categoryName","Lập trình web");
                startActivity(intent);
                break;
            case R.id.hdt:
                intent = new Intent(HomeActivity.this, StartActivity.class);
                intent.putExtra("categoryId",2);
                intent.putExtra("categoryName","Hướng đối tượng");

                startActivity(intent);
                break;

            default:
                Toast.makeText(HomeActivity.this, "a", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}