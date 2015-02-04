package ericmeyer.hockeystreamsandroid.login;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.getlivestreams.LiveStreamsListActivity;
import ericmeyer.hockeystreamssharedandroid.login.LocalCurrentUser;

public class CurrentUserFragment extends Fragment {

    private Button loginAsCurrentUser;

    public CurrentUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_user, container, false);

        loginAsCurrentUser = (Button) view.findViewById(R.id.login_as_current_user_button);

        SharedPreferences currentUserSharedPreferences = getActivity().getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        LocalCurrentUser currentUser = new LocalCurrentUser(currentUserSharedPreferences);

        loginAsCurrentUser.setText("Log in as \"" + currentUser.getCurrentUsername() + "\"");

        loginAsCurrentUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLiveGames();
            }
        });
        return view;
    }

    private void showLiveGames() {
        Intent intent = new Intent(this.getActivity(), LiveStreamsListActivity.class);
        startActivity(intent);
    }

}
