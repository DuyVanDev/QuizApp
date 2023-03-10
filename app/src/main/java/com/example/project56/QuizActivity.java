package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {
    TextView txtQuestions;
    TextView txtQuestion;

    Button option1, option2, option3,option4;
    Button nextBtn;

    int questionPosition=0;
    String selectedOptionByUser = "";
    Timer quiztimer;
    int totalTimeInMins = 1;
    int seconds = 0;
    SQLiteDatabase db;
    private List<Question> questionList;
    String getAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final MediaPlayer mediaPlayerCorrect = MediaPlayer.create(QuizActivity.this,R.raw.correct);
        final MediaPlayer mediaPlayerInCorrect = MediaPlayer.create(QuizActivity.this,R.raw.incorrect);
        questionList = getAllQuestions();
        ImageView backBtn = findViewById(R.id.backBtn);
        TextView timer = findViewById(R.id.timer);
        TextView selectTopicname = findViewById(R.id.topicName);
        txtQuestions = findViewById(R.id.questions);
        txtQuestion = findViewById(R.id.question);

        option1 =(Button) findViewById(R.id.option1);
        option2 = (Button)findViewById(R.id.option2);
        option3 = (Button)findViewById(R.id.option3);
        option4 =(Button) findViewById(R.id.option4);

        nextBtn = findViewById(R.id.nextBtn);

        startTimer(timer);

        txtQuestions.setText((questionPosition + 1) + ""+"/"+questionList.size());
        txtQuestion.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOption1());
        option2.setText(questionList.get(0).getOption2());
        option3.setText(questionList.get(0).getOption3());
        option4.setText(questionList.get(0).getOption4());
        getAnswer = questionList.get(questionPosition).getAnswer_cr();
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();
                    if(option1.getText().toString().equals(getAnswer)) {
                        option1.setBackgroundResource(R.drawable.round_back_green10);
                        option1.setTextColor(Color.WHITE);
                        mediaPlayerCorrect.start();
                    }
                    else {
                        option1.setBackgroundResource(R.drawable.round_back_red10);
                        option1.setTextColor(Color.WHITE);
                        mediaPlayerInCorrect.start();
                    }

                    revealAnswer();
//                    mediaPlayer.start();
                    questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option2.getText().toString();
                    if(option2.getText().toString().equals(getAnswer)) {
                        option2.setBackgroundResource(R.drawable.round_back_green10);
                        option2.setTextColor(Color.WHITE);
                        mediaPlayerCorrect.start();
                    }
                    else {
                        option2.setBackgroundResource(R.drawable.round_back_red10);
                        option2.setTextColor(Color.WHITE);
                        mediaPlayerInCorrect.start();
                    }

                    revealAnswer();
//                    mediaPlayer.start();
                    questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();
                    if(option3.getText().toString().equals(getAnswer)) {
                        option3.setBackgroundResource(R.drawable.round_back_green10);
                        option3.setTextColor(Color.WHITE);
                        mediaPlayerCorrect.start();
                    }
                    else {
                        option3.setBackgroundResource(R.drawable.round_back_red10);
                        option3.setTextColor(Color.WHITE);
                        mediaPlayerInCorrect.start();
                    }

                    revealAnswer();
//                    mediaPlayer.start();
                    questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();
                    if(option4.getText().toString().equals(getAnswer)) {
                        option4.setBackgroundResource(R.drawable.round_back_green10);
                        option4.setTextColor(Color.WHITE);
                        mediaPlayerCorrect.start();
                    }
                    else {
                        option4.setBackgroundResource(R.drawable.round_back_red10);
                        option4.setTextColor(Color.WHITE);
                        mediaPlayerInCorrect.start();
                    }

                    revealAnswer();
//                    mediaPlayer.start();
                    questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else {
                    changeNextQuestion();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiztimer.purge();
                quiztimer.cancel();

                startActivity(new Intent(QuizActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void changeNextQuestion() {
        questionPosition++;
        getAnswer = questionList.get(questionPosition).getAnswer_cr();
        if((questionPosition+1) == questionList.size()) {
            nextBtn.setText("Submit Quiz");
        }
        else if(questionPosition < questionList.size()) {
            selectedOptionByUser = "";
            option1.setTextColor(Color.parseColor("#1F6BB8"));
            option1.setBackgroundResource(R.drawable.round_back_white_stroke_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));
            option2.setBackgroundResource(R.drawable.round_back_white_stroke_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));
            option3.setBackgroundResource(R.drawable.round_back_white_stroke_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));
            option4.setBackgroundResource(R.drawable.round_back_white_stroke_10);
            txtQuestion.setText(questionList.get(questionPosition).getQuestion());
            option1.setText(questionList.get(questionPosition).getOption1());
            option2.setText(questionList.get(questionPosition).getOption2());
            option3.setText(questionList.get(questionPosition).getOption3());
            option4.setText(questionList.get(questionPosition).getOption4());
            txtQuestions.setText((questionPosition + 1) + ""+"/"+questionList.size());
        }
        else {
            Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
            intent.putExtra("correct",getCorrectAnswers());
            intent.putExtra("incorrect",getInCorrectAnswers());
            startActivity(intent);
            finish();
        }
    }

    private void startTimer(TextView timerTextView) {
        quiztimer = new Timer();
        quiztimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && totalTimeInMins ==0) {
                    quiztimer.purge();
                    quiztimer.cancel();
                    Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("correct",getCorrectAnswers());
                    intent.putExtra("incorrect",getInCorrectAnswers());
                    startActivity(intent);
                    finish();
                }
                else {
                    seconds--;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         String finalMinutes = String.valueOf(totalTimeInMins);
                         String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1) {
                            finalMinutes = "0"+finalMinutes;
                        }
                        if(finalSeconds.length() == 1) {
                            finalSeconds = "0"+finalSeconds;
                        }

                        timerTextView.setText((finalMinutes+":"+finalSeconds));
                    }
                });
            }
        },1000,1000);
    }

    private int getCorrectAnswers() {

        int correctAnswers = 0;
        for(int i = 0; i < questionList.size(); i++) {
            String getUserSelectedAnswers = questionList.get(i).getUserSelectedAnswer();
            String getAnswer = questionList.get(i).getAnswer_cr();

            if(getUserSelectedAnswers.equals(getAnswer)) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }
    private int getInCorrectAnswers() {

        int correctAnswers = 0;
        for(int i = 0; i < questionList.size(); i++) {
            String getUserSelectedAnswers = questionList.get(i).getUserSelectedAnswer();
            String getAnswer = questionList.get(i).getAnswer_cr();

            if(!getUserSelectedAnswers.equals(getAnswer)) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    @Override
    public void onBackPressed() {
        quiztimer.purge();
        quiztimer.cancel();

        startActivity(new Intent(QuizActivity.this, HomeActivity.class));
    }

    private void revealAnswer() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(QuizActivity.this,R.raw.correct);

        Toast.makeText(QuizActivity.this, getAnswer + " " + selectedOptionByUser, Toast.LENGTH_SHORT).show();

        if(option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        }
        else if(option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        }
        else if(option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        }
        else if(option4.getText().toString().equals(getAnswer)){
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }

    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<>();
        // Select All Query
        Intent intent = getIntent();
        int value2 = intent.getIntExtra("categoryId",0);
        db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT  * FROM " + LoginActivity.TABLE_QUESTION + " where " + LoginActivity.COLUMN_SUBJECT_ID_RF + " =?"  ,new String[]{String.valueOf(value2)});
        // looping through all rows and adding to list
        c.moveToFirst();
        while(!c.isAfterLast()) {
            quesList.add(new Question(c.getInt(0),c.getString(1),c.getString(2),
                    c.getString(3),c.getString(4),c.getString(5),c.getInt(6),c.getString(7)));
            c.moveToNext();
        }
        return quesList;
    }


}