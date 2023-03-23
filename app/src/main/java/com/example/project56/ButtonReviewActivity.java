package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ButtonReviewActivity extends AppCompatActivity {
    List<Question> lstQuestion = QuizActivity.questionList;
    List<Button> buttonList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_review);
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        findButtons(rootView, buttonList);
        for(int i = 0 ;i < lstQuestion.size() ; i++) {
            if(lstQuestion.get(i).getAnswer_cr().equals(lstQuestion.get(i).getUserSelectedAnswer()) ) {
                buttonList.get(i).setBackgroundResource(R.drawable.round_back_green10);
            }else  {
                buttonList.get(i).setBackgroundResource(R.drawable.round_back_red10);
            }
        }
//        for(int i = 0 ;i < numberTotal.length ; i++) {
//            if(lstCorrect.contains(numberTotal[i])) {
//                buttonList.get(i).setBackgroundResource(R.drawable.round_back_green10);
//            }
//            if(lstInCorrect.contains(numberTotal[i])) {
//                buttonList.get(i).setBackgroundResource(R.drawable.round_back_red10);
//            }
//        }


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

    public void setBg() {

    }

    
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cau1: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);

                intent.putExtra("numberQuestion",1);
                startActivity(intent);
                break;
            }

            case R.id.cau2: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",2);
                startActivity(intent);
                break;
            }

            case R.id.cau3: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",3);
                startActivity(intent);
                break;
            }

            case R.id.cau4: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",4);
                startActivity(intent);
                break;
            }

            case R.id.cau5: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",5);
                startActivity(intent);
                break;
            }

            case R.id.cau6: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",6);
                startActivity(intent);
                break;
            }

            case R.id.cau7: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",7);
                startActivity(intent);
                break;
            }

            case R.id.cau8: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",8);
                startActivity(intent);
                break;
            }

            case R.id.cau9: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",9);
                startActivity(intent);
                break;
            }

            case R.id.cau10: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",10);
                startActivity(intent);
                break;
            }

            case R.id.cau11: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",11);
                startActivity(intent);
                break;
            }

            case R.id.cau12: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",12);
                startActivity(intent);
                break;
            }

            case R.id.cau13: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",13);
                startActivity(intent);
                break;
            }

            case R.id.cau14: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",14);
                startActivity(intent);
                break;
            }

            case R.id.cau15: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",15);
                startActivity(intent);
                break;
            }

            case R.id.cau16: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",16);
                startActivity(intent);
                break;
            }

            case R.id.cau17: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",17);
                startActivity(intent);
                break;
            }

            case R.id.cau18: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",18);
                startActivity(intent);
                break;
            }
            case R.id.cau19: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",19);
                startActivity(intent);
                break;
            }
            case R.id.cau20: {
                Intent intent = new Intent(ButtonReviewActivity.this, ReviewTestActivity.class);
                intent.putExtra("numberQuestion",20);
                startActivity(intent);
                break;
            }

            default: {
                Toast.makeText(ButtonReviewActivity.this, "Chua biet", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}