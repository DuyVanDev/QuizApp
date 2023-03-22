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
    int[]numberTotal =  {0,1,2};
    List<Integer> lstCorrect = QuizActivity.listCorrect;
    List<Integer> lstInCorrect = QuizActivity.listInCorrect;
    List<Button> buttonList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_review);
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        findButtons(rootView, buttonList);
        for(int i = 0 ;i < numberTotal.length ; i++) {
            if(lstCorrect.contains(numberTotal[i])) {
                buttonList.get(i).setBackgroundResource(R.drawable.round_back_green10);
            }
            if(lstInCorrect.contains(numberTotal[i])) {
                buttonList.get(i).setBackgroundResource(R.drawable.round_back_red10);
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

            default: {
                Toast.makeText(ButtonReviewActivity.this, "Chua biet", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}