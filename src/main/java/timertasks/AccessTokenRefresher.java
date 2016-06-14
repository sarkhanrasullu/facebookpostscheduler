package timertasks;

import config.Config;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessTokenRefresher extends TimerTask {

    @Override
    public void run() {
        refreshAccessToken();
    }

    public void refreshAccessToken() {
        try { 
            String url = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + Config.appid + "&"
                    + "client_secret=" + Config.appsecret + "&"
                    + "grant_type=fb_exchange_token&"
                    + "fb_exchange_token=" + Config.pageaccesstoken;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Config.pageaccesstoken = response.toString().replace("access_token=", "").split("&expires=")[0];
            //print result
            System.out.println("access token successfully refreshed=" + Config.pageaccesstoken);
        } catch (Exception ex) {
            Logger.getLogger(FacebookPoster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
