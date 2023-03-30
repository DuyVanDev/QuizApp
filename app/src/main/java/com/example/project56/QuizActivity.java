package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {
    TextView txtQuestions;
    TextView txtQuestion;
    List<Button> buttonList = new ArrayList<>();
    Button option1, option2, option3,option4;
    Button nextBtn;
    Button prevBtn;
    FloatingActionButton fab;
    String sql;

    int questionPosition;
    static String selectedOptionByUser = "";
    Timer quiztimer;
    int totalTimeInMins = 20;
    int seconds = 0;
    SQLiteDatabase db;
    static List<Question> questionList;
    String getAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.bottomsheetlayout, null);
        findButtons(rootView, buttonList);
        fab=findViewById(R.id.fab);
        questionList = getAllQuestions();

        questionPosition = 0;
        ImageView backBtn = findViewById(R.id.backBtn);
        TextView timer = findViewById(R.id.timer);
        TextView selectTopicname = findViewById(R.id.topicName);
        txtQuestions = findViewById(R.id.questions);
        txtQuestion = findViewById(R.id.question);

        ContentValues values = new ContentValues();
        values.put(LoginActivity.COLUMN_USER_SELECTED, selectedOptionByUser);
        db.update(LoginActivity.TABLE_QUESTION, values, null, null);
        option1 =(Button) findViewById(R.id.option1);
        option2 = (Button)findViewById(R.id.option2);
        option3 = (Button)findViewById(R.id.option3);
        option4 =(Button) findViewById(R.id.option4);

        nextBtn = (Button)findViewById(R.id.nextBtn);
        prevBtn = (Button)findViewById(R.id.prevBtn);
        selectedOptionByUser = "";

        startTimer(timer);

        selectTopicname.setText(getIntent().getStringExtra("categoryName"));
        txtQuestions.setText((questionPosition + 1) + ""+"/"+(questionList.size()));
        txtQuestion.setText(questionList.get(questionPosition).getQuestion());
        option1.setText(questionList.get(questionPosition).getOption1());
        option2.setText(questionList.get(questionPosition).getOption2());
        option3.setText(questionList.get(questionPosition).getOption3());
        option4.setText(questionList.get(questionPosition).getOption4());





        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this, buttonList.get(1).getText(), Toast.LENGTH_SHORT).show();
                option1.setBackgroundResource(R.drawable.round_back_selected);
                option1.setTextColor(Color.WHITE);
                option2.setTextColor(Color.parseColor("#1F6BB8"));
                option2.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option3.setTextColor(Color.parseColor("#1F6BB8"));
                option3.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option4.setTextColor(Color.parseColor("#1F6BB8"));
                option4.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                selectedOptionByUser = option1.getText().toString();
                questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                updateUserSelected();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                option2.setBackgroundResource(R.drawable.round_back_selected);
                option2.setTextColor(Color.WHITE);
                option1.setTextColor(Color.parseColor("#1F6BB8"));
                option1.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option3.setTextColor(Color.parseColor("#1F6BB8"));
                option3.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option4.setTextColor(Color.parseColor("#1F6BB8"));
                option4.setBackgroundResource(R.drawable.round_back_white_stroke_10);

                selectedOptionByUser = option2.getText().toString();
                questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                updateUserSelected();


            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option3.setBackgroundResource(R.drawable.round_back_selected);
                option3.setTextColor(Color.WHITE);
                option1.setTextColor(Color.parseColor("#1F6BB8"));
                option1.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option2.setTextColor(Color.parseColor("#1F6BB8"));
                option2.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option4.setTextColor(Color.parseColor("#1F6BB8"));
                option4.setBackgroundResource(R.drawable.round_back_white_stroke_10);

                selectedOptionByUser = option3.getText().toString();
                questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                updateUserSelected();


            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option4.setBackgroundResource(R.drawable.round_back_selected);
                option4.setTextColor(Color.WHITE);
                option1.setTextColor(Color.parseColor("#1F6BB8"));
                option1.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option2.setTextColor(Color.parseColor("#1F6BB8"));
                option2.setBackgroundResource(R.drawable.round_back_white_stroke_10);
                option3.setTextColor(Color.parseColor("#1F6BB8"));
                option3.setBackgroundResource(R.drawable.round_back_white_stroke_10);

                selectedOptionByUser = option4.getText().toString();
                questionList.get(questionPosition).setUserSelectedAnswer(selectedOptionByUser);
                updateUserSelected();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(questionList.get(questionPosition).getUserSelectedAnswer())){
                    Toast.makeText(QuizActivity.this, "Chưa chọn đáp án kìa bạn !", Toast.LENGTH_SHORT).show();
                    return;
                }
                    String action = "next";
                    changeNextQuestion(action);
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String action = "prev";
                changeNextQuestion(action);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showBottomDialog();
            }
        });
    }

    private void changeNextQuestion(String action) {

        if(action == "next") {
            questionPosition++;
            txtQuestions.setText((questionPosition + 1) + ""+"/"+(questionList.size()));
            prevBtn.setBackgroundResource(R.drawable.round_back_green10);
            prevBtn.setEnabled(true);


            if((questionPosition+1) == questionList.size()) {
                nextBtn.setText("Kết thúc");
            }

            if(questionPosition >= questionList.size()) {
                Intent intent = new Intent(QuizActivity.this,ButtonReviewActivity.class);

                quiztimer.purge();
                quiztimer.cancel();
                intent.putExtra("correct",getCorrectAnswers());
                intent.putExtra("incorrect",getInCorrectAnswers());

                intent.putExtra("categoryId",getIntent().getIntExtra("categoryId",0));
                intent.putExtra("categoryName",getIntent().getStringExtra("categoryName"));
                startActivity(intent);
                finish();
            }
            else  {
                getAnswer = questionList.get(questionPosition).getAnswer_cr();

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
                getAnswerSelected();
            }
        }
        else {
            questionPosition--;
            nextBtn.setText("Sau");
            txtQuestions.setText((questionPosition + 1) + ""+"/"+(questionList.size()));
            if(questionPosition-1 < 0) {
                prevBtn.setEnabled(false);
                prevBtn.setBackgroundResource(R.drawable.round_black_green_disable);
            }

                getAnswer = questionList.get(questionPosition).getAnswer_cr();

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
                getAnswerSelected();
        }

    }

    private void startTimer(TextView timerTextView) {
        quiztimer = new Timer();
        quiztimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(seconds <11 && totalTimeInMins ==0) {
                    timerTextView.setTextColor(Color.RED);
                }

                if(seconds == 0 && totalTimeInMins == 00) {
                    quiztimer.purge();
                    quiztimer.cancel();
                    Intent intent = new Intent(QuizActivity.this,ButtonReviewActivity.class);
                    intent.putExtra("correct",getCorrectAnswers());
                    intent.putExtra("incorrect",getInCorrectAnswers());
                    startActivity(intent);
                    finish();
                }
                else if(seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
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

    @Override
    public void onBackPressed() {
        quiztimer.purge();
        quiztimer.cancel();

        startActivity(new Intent(QuizActivity.this, HomeActivity.class));
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<>();
        // Select All Query
        Intent intent = getIntent();
        int value2 = intent.getIntExtra("categoryId",0);
        db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT  * FROM " + LoginActivity.TABLE_QUESTION + "   where " + LoginActivity.COLUMN_SUBJECT_ID_RF + " =? ORDER BY RANDOM() LIMIT 20"  ,new String[]{String.valueOf(value2)});
        // looping through all rows and adding to list
        c.moveToFirst();

        while(!c.isAfterLast()) {
            quesList.add(new Question(c.getInt(0),c.getString(1),c.getString(2),
                    c.getString(3),c.getString(4),c.getString(5),c.getInt(6),c.getString(7)));
            c.moveToNext();
        }
        return quesList;
    }

    public void updateUserSelected() {

        try {
            db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE,null);

            ContentValues values = new ContentValues();
            values.put(LoginActivity.COLUMN_USER_SELECTED, selectedOptionByUser);
            db.update(LoginActivity.TABLE_QUESTION, values, "question=?", new String[]{questionList.get(questionPosition).getQuestion()});

        } catch (Exception ex) {
            Toast.makeText(QuizActivity.this, "err", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAnswerSelected () {
        String userGetSelected = "";
        Cursor c = db.rawQuery("SELECT userSelectedAnswer FROM " + LoginActivity.TABLE_QUESTION + "  where " + LoginActivity.COLUMN_QUESTION + " = ? "  ,new String[]{questionList.get(questionPosition).getQuestion()});
        c.moveToFirst();
        userGetSelected = c.getString(0);



        if(userGetSelected.equals(option1.getText())) {
            option1.setBackgroundResource(R.drawable.round_back_selected);
            option1.setTextColor(Color.WHITE);
        }
        else if(userGetSelected.equals(option2.getText())) {
            option2.setBackgroundResource(R.drawable.round_back_selected);
            option2.setTextColor(Color.WHITE);
        }
        else if(userGetSelected.equals(option3.getText())) {
            option3.setBackgroundResource(R.drawable.round_back_selected);
            option3.setTextColor(Color.WHITE);
        }
        else if(userGetSelected.equals(option4.getText())) {
            option4.setBackgroundResource(R.drawable.round_back_selected);
            option4.setTextColor(Color.WHITE);
        }
        else {
            selectedOptionByUser = "";
            updateUserSelected();
        }

    }

    private int getCorrectAnswers() {
        int correctAnswer = 0;
        for(int i = 0 ;i < questionList.size();i++) {



            String getUserSelected = questionList.get(i).getUserSelectedAnswer();
            String getAnswer = questionList.get(i).getAnswer_cr();
            if(getUserSelected.equals(getAnswer)) {
                correctAnswer++;
            }

        }
        return correctAnswer;
    }

    private int getInCorrectAnswers() {
        int inCorrectAnswer = 0;
        for(int i = 0 ;i < questionList.size();i++) {
            String getUserSelected = questionList.get(i).getUserSelectedAnswer();
            String getAnswer = questionList.get(i).getAnswer_cr();

             if(!getUserSelected.equals(getAnswer)) {
                inCorrectAnswer++;
            }

        }
        return inCorrectAnswer;
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);




        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cau1: {

                questionPosition = 0;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau2: {
                questionPosition = 1;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau3: {
                questionPosition = 2;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau4: {
                questionPosition = 3;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau5: {
                questionPosition = 4;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau6: {
                questionPosition = 5;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau7: {
                questionPosition = 6;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau8: {
                questionPosition = 7;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau9: {
                questionPosition = 8;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau10: {
                questionPosition = 9;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau11: {
                questionPosition = 10;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau12: {
                questionPosition = 11;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau13: {
                questionPosition = 12;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau14: {
                questionPosition = 13;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau15: {
                questionPosition = 14;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau16: {
                questionPosition = 15;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau17: {
                questionPosition = 16;
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
                getAnswerSelected();
                break;
            }

            case R.id.cau18: {
                questionPosition = 17;
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
                getAnswerSelected();
                break;
            }
            case R.id.cau19: {
                questionPosition = 18;
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
                getAnswerSelected();
                break;
            }
            case R.id.cau20: {
                questionPosition = 19;
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
                getAnswerSelected();
                break;
            }

            default: {
                Toast.makeText(QuizActivity.this, "Chua biet", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    private void findButtons(ViewGroup viewGroup, List<Button> buttonList) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                findButtons((ViewGroup) child, buttonList);
            } else if (child instanceof Button) {
                buttonList.add((Button) child);
            }
        }
    }


}