package com.example.computlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    //progress bar which is an indicator of how much time is left
    ProgressBar quiztimer;
    //timer in text, the quiz question and 4 options for answers
    TextView progresscount,quizquestion, option1, option2, option3, option4;
    //a quiz model object so questions can be added as well as their options and answers
    QuizModel quizmodel;
    //correct count
    int correct=0;
    //wrong count
    int wrong=0;
    //index to move through questions that are displayed as the index gets incremented
    int index=0;
    //countdowntimer object
    CountDownTimer countDownTimer;
    //value set for the progress bar
    int timerValue = 100;
    //cards for option1, option2, option3 and option4
    CardView card1, card2, card3, card4;
    //option to move onto next question
    LinearLayout nextquestion;
    //icon to exit the activity
    ImageView quizback;
    //initiating arraylist
    public static ArrayList<QuizModel> questionList = new ArrayList<QuizModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //initiating components
        quiztimer=findViewById(R.id.quiztimer);
        progresscount=findViewById(R.id.progresscount);
        quizquestion=findViewById(R.id.quizquestion);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        card1= findViewById(R.id.card1);
        card2= findViewById(R.id.card2);
        card3= findViewById(R.id.card3);
        card4= findViewById(R.id.card4);
        nextquestion=findViewById(R.id.nextquestion);
        quizback = findViewById(R.id.quizback);
        //resetting arraylist so no duplicates and adding questions
        questionList.clear();
        questionList.add(new QuizModel("What programming style does Java use?", "Functional", "Imperative", "Declarative", "Procedural", "Imperative"));
        questionList.add(new QuizModel("Which one is not a concept of programming?", "Exclusion", "Sequence", "Selection","Iteration", "Exclusion"));
        questionList.add(new QuizModel("Which Java library is used to capture user input?", "Reader", "Scanner", "Outputter", "Result", "Scanner"));
        questionList.add(new QuizModel("What does an agent do?", "Perceiving its environment through sensors and acting on it through actuators", "Perceiving its environment through sensors and relaying messages", "Acting on an environment through actuators from relayed messages", "Relay messages solely between a sensor and actuator", "Perceiving its environment through sensors and acting on it through actuators"));
        questionList.add(new QuizModel("What is the observability of a crossword puzzle agent?", "Partially observable", "Fully observable", "Not observable", "Depends on circumstances", "Fully observable"));
        questionList.add(new QuizModel("What is the deterministic feature of a crossword puzzle agent?", "Episodic", "Stochastic", "Deterministic", "None of the above", "Deterministic"));
        questionList.add(new QuizModel("What query selects all fields of a table?", "SELECT ALL FROM TABLE", "SELECT * FROM TABLE", "SELECT ALL OF TABLE", "ALL FROM TABLE", "SELECT * FROM TABLE"));
        questionList.add(new QuizModel("What is the purpose of a primary key?", "To autoincrement the values", "To set a certain input type", "To keep the data hidden", "To be a unique identifier", "To be a unique identifier"));
        questionList.add(new QuizModel("What function only deletes data from a table?", "DROP", "TRUNCATE", "REMOVE", "None", "TRUNCATE"));
        questionList.add(new QuizModel("Whats not a current problem with security?", "Nature of threats remain unknown", "Policies not sufficient", "No international legislation", "The current technology for devices", "The current technology for devices"));
        questionList.add(new QuizModel("What principle applies to only authorised figures seeing sensitive information?", "Confidentiality", "Integrity", "Authority", "None", "Confidentiality"));
        questionList.add(new QuizModel("What does an impersonation of a user quantify as?", "Threat", "Attack", "Exposure", "Counteraction", "Attack"));

        //shuffling the arraylist so orde of questions always different
        Collections.shuffle(questionList);
        //get question at index number of whatever the value of index has been incremented to
        quizmodel=questionList.get(index);

        //cards default white
        card1.setCardBackgroundColor(getResources().getColor(R.color.white));
        card2.setCardBackgroundColor(getResources().getColor(R.color.white));
        card3.setCardBackgroundColor(getResources().getColor(R.color.white));
        card4.setCardBackgroundColor(getResources().getColor(R.color.white));

        //user cannot press next without selecting an option
        nextquestion.setClickable(false);

        //redirect to dashboard and call finish to destroy this activity when user presses on back icon
        quizback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                finish();
            }
        });

        //countdown timer which will make the progress bar go down and update the time every second until ~20 seconds
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue=timerValue-5;
                progresscount.setText(String.valueOf((int) (millisUntilFinished/1000)));
                quiztimer.setProgress(timerValue);

            }
            //when timer is up a time out dialog will appear prompting the user to restart or exit
            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuizActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryagain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                    }
                });
                dialog.findViewById(R.id.exitbutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    }
                });
                dialog.show();
            }
        }.start();

        dataSet();
    }
    //resetting data
    private void dataSet() {
         nextquestion.setClickable(false);
         quizquestion.setText(quizmodel.getQuestion());
         option1.setText(quizmodel.getOption1());
         option2.setText(quizmodel.getOption2());
         option3.setText(quizmodel.getOption3());
         option4.setText(quizmodel.getOption4());
         timerValue = 100;
         quiztimer.setProgress(timerValue);
         countDownTimer.cancel();
         countDownTimer.start();
    }
    //incrementing correct count and index whilst making the card green when correct answer selected and progress to next question when nextquestion button clicked
    public void correctAnswer(CardView card) {
        card.setBackgroundColor(getResources().getColor(R.color.green));
        nextquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct++;
                index++;
                quizmodel = questionList.get(index);
                colourReset();
                dataSet();


            }
        });

    }
    //incrementing wrong count and index whilst setting card to red when wrong answer selected
    public void wrongAnswer(CardView card) {

        card.setBackgroundColor(getResources().getColor(R.color.red));
        nextquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong++;

                if(index<questionList.size()-1) {
                    index++;
                    quizmodel = questionList.get(index);
                    colourReset();
                    dataSet();


                }
                else {
                    quizEnd(); //call function when last queston has been completed
                }

            }
        });

    }
    //redirect to results activity, usually called once the end of the quiz array reached
    private void quizEnd() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("correct",correct);
        intent.putExtra("wrong",wrong);

        startActivity(intent);
    }
    //set all answer options clickable
    public void buttonClicked() {
        option1.setClickable(true);
        option2.setClickable(true);
        option3.setClickable(true);
        option4.setClickable(true);
    }
    //set all answer options not clickable
    public void buttonNotClicked() {
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        option4.setClickable(false);
    }
    //reset color of cards for next question
    public void colourReset() {
        card1.setBackgroundColor(getResources().getColor(R.color.white));
        card2.setBackgroundColor(getResources().getColor(R.color.white));
        card3.setBackgroundColor(getResources().getColor(R.color.white));
        card4.setBackgroundColor(getResources().getColor(R.color.white));
    }
    //when first answer option is clicked: set green if matches right answer, call correctanswer function if not at end otherwise call quiz end function and increment
    public void option1Click(View view) {
        buttonNotClicked();
        nextquestion.setClickable(true);
        if(quizmodel.getOption1().equals(quizmodel.getAnswer())){
            card1.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<questionList.size()-1) {
                correctAnswer(card1);
            }
            else {
                correct++;
                quizEnd();
            }
        }
        else {
            wrongAnswer(card1); //call wronganswer function if doesnt match answer
        }
    }
    //when second answer option is clicked: set green if matches right answer, call correctanswer function if not at end otherwise call quiz end function and increment
    public void option2Click(View view) {
        buttonNotClicked();
        nextquestion.setClickable(true);
        if(quizmodel.getOption2().equals(quizmodel.getAnswer())){
            card2.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<questionList.size()-1) {
                correctAnswer(card2);
            }
            else {
                correct++;
                quizEnd();
            }
        }
        else {
            wrongAnswer(card2); //call wronganswer if doesnt match answer
        }
    }
    //when third answer option is clicked: set green if matches right answer, call correctanswer function if not at end otherwise call quiz end function and increment
    public void option3Click(View view) {
        buttonNotClicked();
        nextquestion.setClickable(true);
        if(quizmodel.getOption3().equals(quizmodel.getAnswer())){
            card3.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<questionList.size()-1) {
                correctAnswer(card3);
            }
            else {
                correct++;
                quizEnd();
            }
        }
        else {
            wrongAnswer(card3); //call wronganswer if doesnt match answer
        }
    }
    //when fourth answer option is clicked: set green if matches right answer, call correctanswer function if not at end otherwise call quiz end function and increment
    public void option4Click(View view) {
        buttonNotClicked();
        nextquestion.setClickable(true);
        if(quizmodel.getOption4().equals(quizmodel.getAnswer())){
            card4.setCardBackgroundColor(getResources().getColor(R.color.red));

            if(index<questionList.size()-1) {
                correctAnswer(card4);
            }
            else {
                correct++;
                quizEnd();
            }
        }
        else {
            wrongAnswer(card4); //call wronganswer if doesnt match answer
        }
    }
}