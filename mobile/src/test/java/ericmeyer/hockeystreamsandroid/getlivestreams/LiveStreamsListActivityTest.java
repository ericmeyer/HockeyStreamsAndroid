package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.content.SharedPreferences;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamssharedandroid.login.LocalCurrentUser;
import hockeystreamsclient.login.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LiveStreamsListActivityTest {

    private ActivityController<LiveStreamsListActivity> activityBuilder;
    private LiveStreamsListActivity liveStreamsActivity;

    @Before
    public void setUp() {
        activityBuilder = Robolectric.buildActivity(LiveStreamsListActivity.class);
        liveStreamsActivity = activityBuilder.get();
    }

    @Test
    public void testWelcomesTheLoggedInUser() {
        SharedPreferences sharedPreferences = liveStreamsActivity.getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        Response loginResponse = new Response();
        loginResponse.setUsername("the user");
        new LocalCurrentUser(sharedPreferences).put(loginResponse);

        activityBuilder.create();

        TextView welcomeMessage = (TextView) liveStreamsActivity.findViewById(R.id.live_streams_list_welcome);
        String textViewText = welcomeMessage.getText().toString();
        assertThat(textViewText, containsString("the user"));
    }

}
