package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.FragmentTestUtil;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.login.LocalCurrentUser;
import hockeystreamsclient.getlive.GetLiveAction;
import hockeystreamsclient.login.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LiveStreamsListFragmentTest {

    private LiveStreamsListFragment liveStreamsFragment;
    private GetLiveAction action;
    private FragmentActivity activity;

    @Before
    public void setUp() {
        liveStreamsFragment = new LiveStreamsListFragment();
        activity = Robolectric.buildActivity(FragmentActivity.class).create().get();
        activity.getFragmentManager().beginTransaction().add(liveStreamsFragment, "liveStreamsFragment").commit();
        action = mock(GetLiveAction.class);

        Activity activity = Robolectric.buildActivity(Activity.class).get();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        Response loginResponse = new Response();
        loginResponse.setToken("some token");
        new LocalCurrentUser(sharedPreferences).put(loginResponse);
    }

    @Test
    public void testFetchesTheListOfLiveGames() {
        liveStreamsFragment.setGetLiveAction(action);

        FragmentTestUtil.startFragment(liveStreamsFragment);

        Button refreshButton = (Button) liveStreamsFragment.getView().findViewById(R.id.live_streams_list_refresh);
        refreshButton.performClick();

        verify(action).getLive("some token");
    }

    @Test
    public void testLetsTheUserKnowThatItIsFetchingGames() {
        liveStreamsFragment.setGetLiveAction(action);

        FragmentTestUtil.startFragment(liveStreamsFragment);

        Button refreshButton = (Button) liveStreamsFragment.getView().findViewById(R.id.live_streams_list_refresh);
        refreshButton.performClick();

        TextView status = (TextView) liveStreamsFragment.getView().findViewById(R.id.live_streams_list_status);
        String expected = liveStreamsFragment.getString(R.string.live_streams_list_refreshing);
        assertThat(status.getText().toString(), is(equalTo(expected)));
    }

    @Test
    public void testClearsTheStatusWhenDoneFetchingGames() {
        TextView status = (TextView) liveStreamsFragment.getView().findViewById(R.id.live_streams_list_status);
        status.setText("ORIGINAL");

        FragmentTestUtil.startFragment(liveStreamsFragment);

        liveStreamsFragment.liveStreamsFound(new hockeystreamsclient.getlive.Response());

        assertThat(status.getText().toString(), is(equalTo("")));
    }

    @Test
    public void testWiresDependencies() {
        FragmentTestUtil.startFragment(liveStreamsFragment);

        assertThat(liveStreamsFragment.getGetLiveAction(), is(notNullValue()));
        assertThat(liveStreamsFragment.getGetLiveAction().getView(), CoreMatchers.<Object>is(sameInstance(liveStreamsFragment)));
    }

}
