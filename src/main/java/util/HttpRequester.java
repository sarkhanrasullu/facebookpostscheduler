/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Sarkhan Rasullu
 */
//this class is a util for just sending requests and getting response from this request
public class HttpRequester {

    private static final Logger LOG = Logger.getLogger(HttpRequester.class.getName());

    public enum HttpRequestMethod {
        GET,
        POST
    };

    public synchronized String sendRequest(HttpRequestMethod requestMethod, String uri) throws Exception {

        String responseStr = null;

        if (uri == null || uri.isEmpty()) {
            return "";
        }

        uri = uri.trim();

        if (!uri.startsWith("http")) {
            uri = "http://" + uri;
        }

        uri = EncodeUtil.encodeUrl(uri);

        LOG.log(Level.INFO, "method:" + requestMethod.name() + ", request:" + uri);

        if (requestMethod == HttpRequestMethod.GET) {
            responseStr = sendGet(uri);
        } else if (requestMethod == HttpRequestMethod.POST) {
            String[] uriParts = uri.split("\\?");
            responseStr = sendPost(uriParts[0], uriParts[1]);
        }
        LOG.log(Level.INFO, "response:" + responseStr);

        return responseStr;
    }

    public static String getStr(InputStream is) throws IOException {
        InputStreamReader in = new InputStreamReader(is,Charset.defaultCharset());
        
        BufferedReader bufferedReader = new BufferedReader(in);
        StringBuilder sb = new StringBuilder();

        if (bufferedReader != null) {
            int cp;
            while ((cp = bufferedReader.read()) != -1) {
                sb.append((char) cp);
            }
            bufferedReader.close();
        }
        
        return sb.toString();
    }

    public static String sendGet(String uri) throws Exception {

        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new Exception("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        String resultStr = getStr(conn.getInputStream());
        
        conn.disconnect();
        LOG.log(Level.INFO, "method:GET, request:" + uri+",response:" + resultStr);

        return resultStr;
    }

    private static String sendPost(String url, String parameters) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        String resultStr = getStr(con.getInputStream());
        con.disconnect();
        return resultStr;
    }
}
