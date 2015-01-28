package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.impl.client.DefaultHttpClient;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.login.LocalCurrentUser;
import hockeystreamsclient.apache.RemoteClient;
import hockeystreamsclient.getlive.GetLiveAction;
import hockeystreamsclient.getlive.LiveStreamsView;
import hockeystreamsclient.getlive.Response;

public class LiveStreamsListFragment extends ListFragment implements LiveStreamsView {

    private LiveStreamsListAdapter adapter;
    private GetLiveAction getLiveAction;
    private TextView status;

    public LiveStreamsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteClient remoteClient = new RemoteClient(new DefaultHttpClient());
        getLiveAction = new GetLiveAction(remoteClient);
        getLiveAction.setView(this);

        adapter = new LiveStreamsListAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_streams_list, container, false);
        status = (TextView) view.findViewById(R.id.live_streams_list_status);
        setUpRefreshButton(view);
        return view;
    }

    private void setUpRefreshButton(View view) {
        Button refreshButton = (Button) view.findViewById(R.id.live_streams_list_refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status.setText(getString(R.string.live_streams_list_refreshing));
                new GetLiveTask(getLiveAction).execute(getCurrentUserToken());
            }
        });
    }

    public void setGetLiveAction(GetLiveAction getLiveAction) {
        this.getLiveAction = getLiveAction;
    }

    public GetLiveAction getGetLiveAction() {
        return getLiveAction;
    }

    @Override
    public void liveStreamsFound(final Response response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status.setText("");
                adapter.setGames(response.getGames());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getLiveFailed() {
        status.setText("Failed to get list of live games");
    }

    private String getCurrentUserToken() {
        SharedPreferences currentUserSharedPreferences = getActivity().getSharedPreferences(
                LocalCurrentUser.CURRENT_USER_TAG, 0);
        LocalCurrentUser currentUser = new LocalCurrentUser(currentUserSharedPreferences);
        return currentUser.getCurrentToken();
    }

}
