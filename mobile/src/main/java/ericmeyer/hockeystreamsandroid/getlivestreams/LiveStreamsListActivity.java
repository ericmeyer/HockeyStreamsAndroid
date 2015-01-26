package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.login.LocalCurrentUser;

public class LiveStreamsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_streams_list);

        SharedPreferences currentUserSharedPreferences = getSharedPreferences(
                LocalCurrentUser.CURRENT_USER_TAG, 0);
        LocalCurrentUser currentUser = new LocalCurrentUser(currentUserSharedPreferences);

        TextView welcomeMessage = (TextView) findViewById(R.id.live_streams_list_welcome);
        welcomeMessage.setText("Welcome, " + currentUser.getCurrentUsername());
    }

}
