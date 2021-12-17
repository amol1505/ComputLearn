package com.example.computlearn;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class QuizTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityScenarioRule = new ActivityScenarioRule<QuizActivity>(QuizActivity.class);

    @Test
    public void checkQuiz() {
        onView(withId(R.id.nextquestion)).check(matches(not(isClickable())));
    }


}