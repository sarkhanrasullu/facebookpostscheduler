/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sarkhan Rasullu
 */
public class EncodeUtil {

    public static String encodeUrl(String url) throws Exception {
        if (url == null) {
            return null;
        }

        String[] uriParts = url.split("\\?");
        String leftPart = "";
        String rightPart = "";

        if (uriParts.length > 0) {
            leftPart = uriParts[0];
        }
        if (uriParts.length > 1) {
            rightPart = uriParts[1];
        }

        if (rightPart.length() > 0) {
            String[] rightParameters = rightPart.split("&");
            for (int i = 0; i < rightParameters.length; i++) {
                if (i == 0) {
                    leftPart += "?";
                } else {
                    leftPart += "&";
                }
                String[] params = rightParameters[i].split("=");
                
                if (params.length > 0) {
                    leftPart += params[0]+"=";
                }

                if (params.length > 1) {
                    leftPart += URLEncoder.encode(params[1], "UTF-8");
                }
            }
        }

        return leftPart;
    }
}
