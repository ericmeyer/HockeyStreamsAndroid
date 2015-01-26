package ericmeyer.hockeystreamsandroid.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.FragmentTestUtil;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.getlivestreams.LiveStreamsListActivity;
import hockeystreamsclient.login.AttemptLogin;
import hockeystreamsclient.login.LoginView;
import hockeystreamsclient.login.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LoginFragmentTest {

    private LoginFragment loginFragment;

    @Before
    public void setUp() throws Exception {
        loginFragment = new LoginFragment();
        FragmentTestUtil.startFragment(loginFragment);
    }

    @Test
    public void testAttemptsLogin() throws Exception {
        AttemptLogin attemptLoginAction = mock(AttemptLogin.class);
        loginFragment.setAction(attemptLoginAction);

        setText(R.id.login_username, "the user");
        setText(R.id.login_password, "some password");

        Button submitLogin = (Button) loginFragment.getView().findViewById(R.id.login_submit);
        submitLogin.performClick();

        verify(attemptLoginAction).execute("the user", "some password");
    }

    @Test
    public void testSetsUpDependencies() {
        AttemptLogin attemptLoginAction = loginFragment.getAction();

        assertThat(attemptLoginAction, is(notNullValue()));
        LoginView actualView = attemptLoginAction.getView();
        assertThat(actualView, is(notNullValue()));
        assertThat(actualView, is(CoreMatchers.<LoginView>sameInstance(loginFragment)));

        assertThat(loginFragment.getCurrentUser(), is(notNullValue()));
    }

    @Test
    public void testSetsTheLoggedInUserOnLogin() {
        CurrentUser currentUser = mock(CurrentUser.class);
        loginFragment.setCurrentUser(currentUser);
        Response loginResponse = new Response();

        loginFragment.loginWasSuccessful(loginResponse);

        verify(currentUser).put(loginResponse);
    }

    @Test
    public void testShowsTheListOfLiveGamesOnLogin() {
        CurrentUser currentUser = mock(CurrentUser.class);
        loginFragment.setCurrentUser(currentUser);
        Response loginResponse = new Response();

        loginFragment.loginWasSuccessful(loginResponse);

        ShadowActivity shadowActivity = Robolectric.shadowOf(loginFragment.getActivity());
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertThat(startedIntent, is(notNullValue()));
        String startedActivityName = startedIntent.getComponent().getClassName();
        assertThat(startedActivityName, is(equalTo(LiveStreamsListActivity.class.getName())));
    }

    private void setText(int viewID, String text) {
        EditText username = (EditText) loginFragment.getView().findViewById(viewID);
        username.setText(text);
    }

}
