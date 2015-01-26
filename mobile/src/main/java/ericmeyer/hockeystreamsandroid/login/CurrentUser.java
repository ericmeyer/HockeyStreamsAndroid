package ericmeyer.hockeystreamsandroid.login;

import hockeystreamsclient.login.Response;

public interface CurrentUser {
    void put(Response loginResponse);
}
