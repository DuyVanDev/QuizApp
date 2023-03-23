package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    Button btnFinal, btnReview;
    TextView correctAnswer, inCorrectAnswer;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btnFinal = findViewById(R.id.btnFinal);
        correctAnswer = findViewById(R.id.correctAnswer);
        List<Question> questionList = QuizActivity.questionList;
        int correctAnswers = getIntent().getIntExtra("correct",0);
        int inCorrectAnswers = getIntent().getIntExtra("incorrect",0);
        inCorrectAnswer = findViewById(R.id.inCorrectAnswer);
        btnReview =(Button) findViewById(R.id.btnReview);
        correctAnswer.setText("Số câu đúng: " + correctAnswers);
        inCorrectAnswer.setText("Số câu sai: " + inCorrectAnswers);
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);
                ContentValues values = new ContentValues();
                values.put(LoginActivity.COLUMN_USER_SELECTED, "");
                db.update(LoginActivity.TABLE_QUESTION, values, null, null);

                Intent intent = new Intent(ResultActivity.this,HomeActivity.class);

                startActivity(intent);
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,ButtonReviewActivity.class);
                startActivity(intent);
            }
        });
    }


}