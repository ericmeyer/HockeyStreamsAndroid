package ericmeyer.hockeystreamsandroid.watchlivestream;

import android.app.Activity;
import android.os.Bundle;

import ericmeyer.hockeystreamsandroid.R;

public class WatchLiveStreamActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_live_stream);

        String streamID = getIntent().getStringExtra("StreamID");
        System.out.println("streamID = " + streamID);
    }

}
