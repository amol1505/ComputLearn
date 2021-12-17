package com.example.computlearn;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Rule
    public ActivityScenarioRule<Register> activityScenarioRule = new ActivityScenarioRule<Register>(Register.class);

    @Test
    public void registerDetails() {
        onView(withId(R.id.registerName)).perform(typeText("test name"), closeSoftKeyboard());
        onView(withId(R.id.registerName)).check(matches(withText("test name")));
        onView(withId(R.id.registerEmail)).perform(typeText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.registerEmail)).check(matches(withText("test@gmail.com")));
        onView(withId(R.id.registerPassword)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.registerPassword)).check(matches(withText("password")));
        onView(withId(R.id.registerButton)).check(matches(isClickable()));
    }


}
