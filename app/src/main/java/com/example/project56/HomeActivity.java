package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView hdt = (ImageView) findViewById(R.id.hdt);

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.hdt:
                intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("categoryId",2);

                startActivity(intent);
                break;
            case R.id.ltw:
                intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("categoryId",1);

                startActivity(intent);
                break;
            default:
                Toast.makeText(HomeActivity.this, "a", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}