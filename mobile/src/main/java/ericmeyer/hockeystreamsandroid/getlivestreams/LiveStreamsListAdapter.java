package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ericmeyer.hockeystreamsandroid.R;
import hockeystreamsclient.getlive.Game;

public class LiveStreamsListAdapter extends ArrayAdapter<Game> {
    private final Activity activity;
    private List<Game> games;

    public LiveStreamsListAdapter(Activity activity) {
        super(activity, 0);
        this.activity = activity;
        this.games = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = getItem(position);
        return populateView(convertView, game);
    }

    public View populateView(View convertView, Game game) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.live_stream_list_item, null);
        }
        TextView homeTeam = (TextView) convertView.findViewById(R.id.live_stream_list_item_home_team);
        TextView feedType = (TextView) convertView.findViewById(R.id.live_stream_list_item_feed_type);

        homeTeam.setText(game.getHomeTeam() + " " + String.valueOf(game.getID()));
        if (game.getFeedType() != null) {
            feedType.setText(game.getFeedType());
        }
        return convertView;
    }

    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

}
