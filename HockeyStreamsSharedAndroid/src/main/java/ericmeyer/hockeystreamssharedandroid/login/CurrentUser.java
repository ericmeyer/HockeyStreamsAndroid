package ericmeyer.hockeystreamssharedandroid.login;

import hockeystreamsclient.login.Response;

public interface CurrentUser {

    void put(Response loginResponse);
    void logout();

}
