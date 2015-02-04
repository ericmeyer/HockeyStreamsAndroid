package ericmeyer.hockeystreamsandroid.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;

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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    private LoginActivity loginActivity;
    private LocalCurrentUser currentUser;
    private ActivityController<LoginActivity> activityController;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(LoginActivity.class);
        loginActivity = activityController.get();
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        currentUser = new LocalCurrentUser(sharedPreferences);
    }

    @Test
    public void testHasAnOptionToLoginAsCurrentUser() {
        login();

        activityController.create();

        assertThat(loginAsCurrentUserFragment(), is(notNullValue()));
    }

    @Test
    public void testHasNoOptionToLoginAsCurrentUser() {
        currentUser.logout();

        activityController.create();

        assertThat(loginAsCurrentUserFragment(), is(nullValue()));
    }

    private Fragment loginAsCurrentUserFragment() {
        FragmentManager fragmentManager = loginActivity.getFragmentManager();
        return fragmentManager.findFragmentById(R.id.login_current_user_frame);
    }

    private void login() {
        Response loginResponse = new Response();
        loginResponse.setUsername("the user");
        loginResponse.setToken("the token");
        currentUser.put(loginResponse);
    }

}
