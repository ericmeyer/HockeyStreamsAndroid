package ericmeyer.hockeystreamsandroid.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.impl.client.DefaultHttpClient;

import ericmeyer.hockeystreamsandroid.R;
import ericmeyer.hockeystreamsandroid.getlivestreams.LiveStreamsListActivity;
import hockeystreamsclient.apache.RemoteClient;
import hockeystreamsclient.login.AttemptLogin;
import hockeystreamsclient.login.LoginView;
import hockeystreamsclient.login.Response;

public class LoginFragment extends Fragment implements LoginView {

    public static final String APPLICATION_API_KEY = "e5b6ae15286e31027772fb70cc0b2936";
    private EditText password;
    private EditText username;
    private Button attemptLogin;
    private AttemptLogin attemptLoginAction;
    private CurrentUser currentUser;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RemoteClient remoteClient = new RemoteClient(new DefaultHttpClient());
        attemptLoginAction = new AttemptLogin(APPLICATION_API_KEY, remoteClient);
        attemptLoginAction.setLoginView(this);

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        username = (EditText) view.findViewById(R.id.login_username);
        password = (EditText) view.findViewById(R.id.login_password);

        attemptLogin = (Button) view.findViewById(R.id.login_submit);
        attemptLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AttemptLoginTask(attemptLoginAction).execute(getUsername(), getPassword());
            }
        });

        SharedPreferences currentUserSharedPreferences = getActivity().getSharedPreferences(LocalCurrentUser.CURRENT_USER_TAG, 0);
        currentUser = new LocalCurrentUser(currentUserSharedPreferences);
        return view;
    }

    @Override
    public void loginWasSuccessful(Response loginResponse) {
        System.out.println("loginResponse.getToken() = " + loginResponse.getToken());
        currentUser.put(loginResponse);
        Intent intent = new Intent(this.getActivity(), LiveStreamsListActivity.class);
        startActivity(intent);
    }

    @Override
    public void invalidUsernameOrPassword() {
        System.out.println("invalidUsernameOrPassword");
    }

    public void setAction(AttemptLogin action) {
        this.attemptLoginAction = action;
    }

    private String getPassword() {
        return password.getText().toString();
    }

    private String getUsername() {
        return username.getText().toString();
    }

    public AttemptLogin getAction() {
        return attemptLoginAction;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    private class AttemptLoginTask extends AsyncTask<String, Void, Void> {
        private final AttemptLogin attemptLoginAction;

        public AttemptLoginTask(AttemptLogin attemptLoginAction) {
            this.attemptLoginAction = attemptLoginAction;
        }

        @Override
        protected Void doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            attemptLoginAction.execute(username, password);
            return null;
        }
    }

}
