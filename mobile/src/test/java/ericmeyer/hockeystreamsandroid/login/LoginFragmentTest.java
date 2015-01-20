package ericmeyer.hockeystreamsandroid.login;

import android.widget.Button;
import android.widget.EditText;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import ericmeyer.hockeystreamsandroid.R;
import hockeystreamsclient.login.AttemptLogin;
import hockeystreamsclient.login.LoginView;

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
    }

    @Test
    public void testAttemptsLogin() throws Exception {
        AttemptLogin attemptLoginAction = mock(AttemptLogin.class);
        FragmentTestUtil.startFragment(loginFragment);
        loginFragment.setAction(attemptLoginAction);

        setText(R.id.login_username, "the user");
        setText(R.id.login_password, "some password");

        Button submitLogin = (Button) loginFragment.getView().findViewById(R.id.login_submit);
        submitLogin.performClick();

        verify(attemptLoginAction).execute("the user", "some password");
    }

    @Test
    public void testSetsUpAction() throws Exception {
        FragmentTestUtil.startFragment(loginFragment);
        AttemptLogin attemptLoginAction = loginFragment.getAction();

        assertThat(attemptLoginAction, is(notNullValue()));
        LoginView actualView = attemptLoginAction.getView();
        assertThat(actualView, is(notNullValue()));
        assertThat(actualView, is(CoreMatchers.<LoginView>sameInstance(loginFragment)));
    }

    private void setText(int viewID, String text) {
        EditText username = (EditText) loginFragment.getView().findViewById(viewID);
        username.setText(text);
    }

}
