package ericmeyer.hockeystreamsandroid.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.FragmentTestUtil;

import java.util.HashMap;
import java.util.Map;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.getlivestreams.LiveStreamsListActivity;
import hockeystreamsclient.login.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CurrentUserFragmentTest {

    private CurrentUserFragment currentUserFragment;

    @Before
    public void setUp() throws Exception {
        currentUserFragment = new CurrentUserFragment();
    }

    @Test
    public void testShowsTheListOfLiveGames() {
        FragmentTestUtil.startFragment(currentUserFragment);

        Button login = (Button) currentUserFragment.getView().findViewById(R.id.login_as_current_user_button);
        login.performClick();


        ShadowActivity shadowActivity = Robolectric.shadowOf(currentUserFragment.getActivity());
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertThat(startedIntent, is(notNullValue()));
        String startedActivityName = startedIntent.getComponent().getClassName();
        assertThat(startedActivityName, is(equalTo(LiveStreamsListActivity.class.getName())));
    }

    @Test
    public void testUpdatesTheButtonText() {
        setupCurrentUser();

        FragmentTestUtil.startFragment(currentUserFragment);

        Button login = (Button) currentUserFragment.getView().findViewById(R.id.login_as_current_user_button);

        assertThat(login.getText().toString(), containsString("the user"));
    }

    private void setupCurrentUser() {
        Activity activity = Robolectric.buildActivity(Activity.class).get();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        Response loginResponse = new Response();
        loginResponse.setUsername("the user");
        new LocalCurrentUser(sharedPreferences).put(loginResponse);
    }

}
