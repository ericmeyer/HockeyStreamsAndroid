package ericmeyer.hockeystreamsandroid.login;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.tester.android.content.TestSharedPreferences;

import java.util.HashMap;
import java.util.Map;

import hockeystreamsclient.login.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocalCurrentUserTest {

    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        Map<String, Map<String, Object>> map = new HashMap<>();
        sharedPreferences = new TestSharedPreferences(map, "current user", 0);
    }

    @Test
    public void testSavesTheCurrentToken() {
        LocalCurrentUser currentUser = new LocalCurrentUser(sharedPreferences);
        Response loginResponse = new Response();
        loginResponse.setToken("ABC123");
        currentUser.put(loginResponse);
        assertThat(currentUser.getCurrentToken(), is(equalTo("ABC123")));
    }

    @Test
    public void testPersistsTheTokenAcrossInstances() {
        LocalCurrentUser currentUser = new LocalCurrentUser(sharedPreferences);
        Response loginResponse = new Response();
        loginResponse.setToken("ABC123");
        currentUser.put(loginResponse);
        assertThat(new LocalCurrentUser(sharedPreferences).getCurrentToken(), is(equalTo("ABC123")));
    }

    @Test
    public void testSavesTheCurrentUsername() {
        LocalCurrentUser currentUser = new LocalCurrentUser(sharedPreferences);
        Response loginResponse = new Response();
        loginResponse.setUsername("some user");
        currentUser.put(loginResponse);
        assertThat(currentUser.getCurrentUsername(), is(equalTo("some user")));
    }

    @Test
    public void testLogsTheCurrentUserOut() {
        LocalCurrentUser currentUser = new LocalCurrentUser(sharedPreferences);
        Response loginResponse = new Response();
        loginResponse.setUsername("some user");
        currentUser.put(loginResponse);
        currentUser.logout();

        assertThat(currentUser.getCurrentUsername(), is(nullValue()));
        assertThat(currentUser.getCurrentToken(), is(nullValue()));
    }

}
