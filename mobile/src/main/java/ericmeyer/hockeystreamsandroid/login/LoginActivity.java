package ericmeyer.hockeystreamsandroid.login;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;

import ericmeyer.hockeystreamsandroid.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences currentUserSharedPreferences = getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        LocalCurrentUser currentUser = new LocalCurrentUser(currentUserSharedPreferences);

        if (currentUser.getCurrentToken() != null) {
            FragmentManager fragmentManager = getFragmentManager();
            CurrentUserFragment currentUserFragment = new CurrentUserFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.login_current_user_frame, currentUserFragment)
                    .commit();
        }
    }

}
