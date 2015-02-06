package ericmeyer.hockeystreamsandroid.watchlivestream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamssharedandroid.login.LocalCurrentUser;
import hockeystreamsclient.getlivestream.GetLiveStreamAction;
import hockeystreamsclient.getlivestream.LiveStreamView;
import hockeystreamsclient.login.Response;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class WatchLiveStreamFragmentTest {

    private WatchLiveStreamFragment liveStreamFragment;
    private LocalCurrentUser currentUser;

    @Before
    public void setUp() {
        liveStreamFragment = new WatchLiveStreamFragment();
        FragmentTestUtil.startFragment(liveStreamFragment);
        Activity activity = liveStreamFragment.getActivity();
        Intent intent = new Intent();
        intent.putExtra("StreamID", "123");
        activity.setIntent(intent);

        SharedPreferences sharedPreferences = liveStreamFragment.getActivity().getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        currentUser = new LocalCurrentUser(sharedPreferences);
        login();
    }

    @Test
    public void testFetchesTheStream() {
        GetLiveStreamAction action = mock(GetLiveStreamAction.class);
        liveStreamFragment.setAction(action);

        clickStart();

        verify(action).getLiveStream("the token", "123");
    }

    @Test
    public void testWiresDependencies() {
        GetLiveStreamAction action = liveStreamFragment.getAction();
        assertThat(action, is(notNullValue()));
        assertThat(action.getView(), is(CoreMatchers.<LiveStreamView>sameInstance(liveStreamFragment)));
    }

    private void clickStart() {
        Button startButton = (Button) liveStreamFragment.getView().findViewById(R.id.watch_live_stream_start);
        startButton.performClick();
    }

    private void login() {
        Response loginResponse = new Response();
        loginResponse.setUsername("the user");
        loginResponse.setToken("the token");
        currentUser.put(loginResponse);
    }
}
