package ericmeyer.hockeystreamsandroid.login;

import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import ericmeyer.hockeystreamsandroid.R;
import hockeystreamsclient.login.AttemptLogin;

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

    private void setText(int viewID, String text) {
        EditText username = (EditText) loginFragment.getView().findViewById(viewID);
        username.setText(text);
    }

}
