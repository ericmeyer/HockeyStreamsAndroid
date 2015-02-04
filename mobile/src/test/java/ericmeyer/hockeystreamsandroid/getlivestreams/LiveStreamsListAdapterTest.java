package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ericmeyer.hockeystreamsandroid.R;
import hockeystreamsclient.getlive.Game;
import hockeystreamsclient.getlive.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LiveStreamsListAdapterTest {

    private LiveStreamsListAdapter liveStreamsListAdapter;

    @Before
    public void setUp() {
        Activity activity = Robolectric.buildActivity(Activity.class).create().get();
        liveStreamsListAdapter = new LiveStreamsListAdapter(activity);
    }

    @Test
    public void testSetsTheHomeTeam() {
        Response response = new Response();
        Game game = new Game();
        game.setHomeTeam("Chicago Blackhawks");
        response.addGame(game);

        View view = liveStreamsListAdapter.populateView(null, game);

        assertThat(view, is(notNullValue()));
        TextView homeTeam = (TextView) view.findViewById(R.id.live_stream_list_item_home_team);
        assertThat(homeTeam.getText().toString(), containsString("Chicago Blackhawks"));
    }

}
