package ericmeyer.hockeystreamsandroid.getlivestreams;

import android.os.AsyncTask;

import hockeystreamsclient.getlive.GetLiveAction;

public class GetLiveTask extends AsyncTask<String, Void, Void> {
    private final GetLiveAction getLiveAction;

    public GetLiveTask(GetLiveAction getLiveAction) {
        this.getLiveAction = getLiveAction;
    }

    @Override
    protected Void doInBackground(String... params) {
        String token = params[0];
        getLiveAction.getLive(token);
        return null;
    }
}
