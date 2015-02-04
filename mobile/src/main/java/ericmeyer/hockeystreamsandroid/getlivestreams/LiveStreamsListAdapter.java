package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ericmeyer.hockeystreamsandroid.R;
import hockeystreamsclient.getlive.Response;

public class LiveStreamsListAdapter extends ArrayAdapter<Response.Game> {
    private final Activity activity;
    private List<Response.Game> games;

    public LiveStreamsListAdapter(Activity activity) {
        super(activity, 0);
        this.activity = activity;
        this.games = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.live_stream_list_item, null);
        }
        TextView homeTeam = (TextView) convertView.findViewById(R.id.live_stream_list_item_home_team);
        TextView feedType = (TextView) convertView.findViewById(R.id.live_stream_list_item_feed_type);

        Response.Game game = games.get(position);
        homeTeam.setText(game.getHomeTeam() + " " + String.valueOf(game.getID()));
        if (game.getFeedType() != null) {
            feedType.setText(game.getFeedType());
        }

        return convertView;
    }

    public void setGames(List<Response.Game> games) {
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

}
