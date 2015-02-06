package ericmeyer.hockeystreamsandroid.watchlivestream;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.apache.http.impl.client.DefaultHttpClient;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamssharedandroid.login.LocalCurrentUser;
import hockeystreamsclient.apache.RemoteClient;
import hockeystreamsclient.getlivestream.GetLiveStreamAction;
import hockeystreamsclient.getlivestream.LiveStreamView;
import hockeystreamsclient.getlivestream.Response;
import hockeystreamsclient.http.Client;

public class WatchLiveStreamFragment extends Fragment implements LiveStreamView {


    private GetLiveStreamAction action;

    public WatchLiveStreamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch_live_stream, container, false);

        RemoteClient remoteClient = new RemoteClient(new DefaultHttpClient());
        action = new GetLiveStreamAction(remoteClient);
        action.setView(this);

        Button startButton = (Button) view.findViewById(R.id.watch_live_stream_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLiveStreamTask task = new GetLiveStreamTask(action);
                task.execute(getCurrentUserToken(), getStreamID());
            }
        });
        return view;
    }


    @Override
    public void liveStreamWasFound(Response getLiveStreamResponse) {
        System.out.println("HomeTeam = " + getLiveStreamResponse.getHomeTeam());
        System.out.println("AwayTeam = " + getLiveStreamResponse.getAwayTeam());
    }

    public void setAction(GetLiveStreamAction action) {
        this.action = action;
    }

    private String getCurrentUserToken() {
        SharedPreferences currentUserSharedPreferences = getActivity().getSharedPreferences(
                LocalCurrentUser.CURRENT_USER_TAG, 0);
        LocalCurrentUser currentUser = new LocalCurrentUser(currentUserSharedPreferences);
        return currentUser.getCurrentToken();
    }

    private String getStreamID() {
        return getActivity().getIntent().getStringExtra("StreamID");
    }

    public GetLiveStreamAction getAction() {
        return action;
    }

    private class GetLiveStreamTask extends AsyncTask<String, Void, Void> {
        private final GetLiveStreamAction action;

        public GetLiveStreamTask(GetLiveStreamAction action) {
            this.action = action;
        }

        @Override
        protected Void doInBackground(String... params) {
            String token = params[0];
            String streamID = params[1];
            action.getLiveStream(token, streamID);
            return null;
        }
    }

}
