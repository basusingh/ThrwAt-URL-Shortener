package com.basusingh.throwat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThrwAtURLFormat {

    private static final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                    "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                    "([).!';/?:,][[:blank:]])?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public static String getReachableURL(String tinyUrl){
        return ("https://thrw.at/" + tinyUrl);
    }

    public static String getFormattedURL(String url){
        String formattedURL = addProtocolToURL(url);
        if(!validateURL(formattedURL)){
            return null;
        }
        return formattedURL;
    }

    private static String addProtocolToURL(String url){
        if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://") || url.toLowerCase().startsWith("ftp://") || url.toLowerCase().startsWith("mailto://") ) {
            return url;
        }
        return ("http://" + url);
    }

    public static boolean validateURL(String url){
        boolean res = true;
        if(!Patterns.WEB_URL.matcher(url).matches()){
            res = false;
        }
        Matcher matcher = URL_PATTERN.matcher(url);
        if(!matcher.matches()){
            res = false;
        }
        return res;
    }

    public static boolean isValidCustomName(String name){
        Pattern pattern = Pattern.compile("[0-9a-zA-Z_-]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
