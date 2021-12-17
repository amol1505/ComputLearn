package com.example.computlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    //Texts which show total score, correct questions and wrong questions
    TextView quizscore,questionsright,questionswrong;
    //icon to exit from the activity
    ImageView resultback;
    //values for wrong and correct answers
    int correct,wrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //receive values for correct and wrong answers from quizactivity
        correct=getIntent().getIntExtra("correct",0);
        wrong=getIntent().getIntExtra("wrong",0);
        //initiate objects
        quizscore=findViewById(R.id.quizscore);
        questionsright=findViewById(R.id.rightquestions);
        questionswrong=findViewById(R.id.wrongquestions);
        resultback=findViewById(R.id.resultback);
        //set textviews to show the result to the reader
        questionsright.setText("Number of questions correct: " + correct);
        questionswrong.setText("Number of questions wrong: " + wrong);
        quizscore.setText("Total score:\n" + correct + "/12");

        //if user presses the icon then redirect them to the dashboard class and call finish() to destroy the running activity
        resultback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                finish();
            }
        });

    }



}