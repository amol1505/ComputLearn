package com.example.computlearn;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<Login>(Login.class);

    @Test
    public void enterDetails() {
        onView(withId(R.id.loginEmail)).perform(typeText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.loginEmail)).check(matches(withText("test@gmail.com")));
        onView(withId(R.id.loginPassword)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).check(matches(withText("password")));
        onView(withId(R.id.loginButton)).check(matches(isClickable()));

    }


}

