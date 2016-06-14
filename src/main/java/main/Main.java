package main;

import timertasks.AccessTokenRefresher;
import java.io.IOException;
import java.util.Timer;
import parser.PageParserCenter;
import timertasks.FacebookPostCenter;

public class Main {

    public static void main(String[] args) throws IOException {
        AccessTokenRefresher tokenRefreshTask = new AccessTokenRefresher();
        
        Timer accessTokenTimer = new Timer();
        accessTokenTimer.schedule(tokenRefreshTask, 0, 1 * 60 * 1000);

        new FacebookPostCenter().start();
        new PageParserCenter().start(); 
    }
}
