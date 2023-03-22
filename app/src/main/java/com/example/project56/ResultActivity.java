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
        inCorrectAnswer = findViewById(R.id.inCorrectAnswer);
        btnReview =(Button) findViewById(R.id.btnReview);
        correctAnswer.setText("Số câu đúng: " + getCorrectAnswers());
        inCorrectAnswer.setText("Số câu sai: " + getInCorrectAnswers());
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,HomeActivity.class);
                QuizActivity.listCorrect.clear();
                QuizActivity.listInCorrect.clear();
                db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);
                ContentValues values = new ContentValues();
                values.put(LoginActivity.COLUMN_USER_SELECTED, "");
                db.update(LoginActivity.TABLE_QUESTION, values, null, null);

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

    private int getCorrectAnswers() {

        db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT  count(*) FROM " + LoginActivity.TABLE_QUESTION + " where " + LoginActivity.COLUMN_USER_SELECTED + " == " + LoginActivity.COLUMN_ANSWER_NR , null);
        c.moveToFirst();

        int correctAnswers = c.getInt(0);

        return correctAnswers;
    }

    private int getInCorrectAnswers() {

        db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT  count(*) FROM " + LoginActivity.TABLE_QUESTION + " where " + LoginActivity.COLUMN_USER_SELECTED + " != " + LoginActivity.COLUMN_ANSWER_NR , null);
        c.moveToFirst();
        int inCorrectAnswers = c.getInt(0);


        return inCorrectAnswers;
    }
}