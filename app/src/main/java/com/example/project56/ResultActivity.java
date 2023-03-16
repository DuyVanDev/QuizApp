package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    Button btnFinal;
    TextView correctAnswer, inCorrectAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btnFinal = findViewById(R.id.btnFinal);
        correctAnswer = findViewById(R.id.correctAnswer);
        inCorrectAnswer = findViewById(R.id.inCorrectAnswer);

        int getCorrectAnswers = getIntent().getIntExtra("correct",0);
        int getInCorrectAnswers = getIntent().getIntExtra("incorrect",0);

        correctAnswer.setText("Số câu đúng: " + getCorrectAnswers);
        inCorrectAnswer.setText("Số câu sai: " + getInCorrectAnswers);
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}