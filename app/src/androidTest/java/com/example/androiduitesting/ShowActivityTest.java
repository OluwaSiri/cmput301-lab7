package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    // check if the activity correctly switched
    @Test
    public void testActivitySwitch() {
        // add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // click the city in the list
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // check that the new activity is displayed
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    // check if the city name is consistent
    @Test
    public void testCityNameConsistency() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // check that text matches the clicked name
        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    // test the back button
    @Test
    public void testBackButton() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Tokyo"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // click the back button
        onView(withId(R.id.button_back)).perform(click());

        // verify MainActivity list is visible again
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
