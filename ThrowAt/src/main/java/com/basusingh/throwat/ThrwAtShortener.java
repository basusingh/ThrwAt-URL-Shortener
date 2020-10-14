package com.basusingh.throwat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basusingh.throwat.db.URLItems;
import com.basusingh.throwat.utils.Patterns;
import com.basusingh.throwat.utils.ThrwAtURLFormat;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.basusingh.throwat.utils.Helper.THRWAT_SERVER_URL;

public class ThrwAtShortener {

    private static ThrwAtShortener instance;
    private static Context mContext;
    private onUrlShortListener onUrlShortListener;
    private ThrwAtUser user;

    private static final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                    "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                    "([).!';/?:,][[:blank:]])?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private ThrwAtShortener(Context context) {
        mContext = context;
    }

    public static synchronized ThrwAtShortener getInstance(Context context) {
        if (instance == null) {
            instance = new ThrwAtShortener(context);
        }
        return instance;
    }

    public void createShortURL(String url, onUrlShortListener var2){
        if(!ThrwAt.getInstance(mContext).isRegistered()){
            return;
        }
        onUrlShortListener = var2;

        String formattedUrl = ThrwAtURLFormat.getFormattedURL(url.trim());
        if(formattedUrl == null){
            completeTask(false, "Invalid URL", null, null, null);
            return;
        }

        user = ThrwAt.getInstance(mContext).getCurrentUser();
        submitData(formattedUrl);
    }

    private void submitData(final String mUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeTask(false, o.getString("message"), null, null, null);
                            } else {
                                JSONObject o1 = o.getJSONObject("tiny_url");
                                URLItems items = new URLItems();
                                items.setUrl(mUrl);
                                items.setUrlId(o1.getString("url_id"));
                                items.setTinyUrl(o1.getString("tiny_url"));
                                items.setTimestamp(o1.getString("timestamp"));
                                items.setTags("");
                                items.setUrlProtected("no");
                                addURLToDatabase(items);
                                completeTask(true, o.getString("message"), mUrl, o1.getString("url_id"), o1.getString("tiny_url"));
                            }
                        } catch (Exception e){;
                            completeTask(false, "An error occurred parsing response. Code: X-201", null, null, null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeTask(false, "Internet Connection Issue. Please try again.", null, null, null);
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url", mUrl);
                return new JSONObject(params).toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("api_key", user.getApiKey());
                return params;
            }};

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        ThrwAtNetworkSingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void updateCustomShortURL(String urlId, String url, String custom, onUrlShortListener var2){
        if(!ThrwAt.getInstance(mContext).isRegistered()){
            return;
        }
        custom.trim();
        onUrlShortListener = var2;

        if(!ThrwAtURLFormat.isValidCustomName(custom)){
            completeTask(false, "Only alphabets, numbers, dashes and underscores allowed.", null, null, null);
            return;
        }

        user = ThrwAt.getInstance(mContext).getCurrentUser();
        updateCustomData(urlId, url, custom);
    }

 
 private void updateCustomData(final String mUrlId, final String mUrl, final String mCustom){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "create-custom",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeTask(false, o.getString("message"), null, null, null);
                            } else {
                                updateCustomURLToDatabase(mUrlId, o.getString("tiny_url"));
                                completeTask(true, o.getString("message"), mUrl, mUrlId, o.getString("tiny_url"));
                            }
                        } catch (Exception e){;
                            completeTask(false, "An error occurred parsing response. Code: X-201", null, null, null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeTask(false, "Internet Connection Issue. Please try again.", null, null, null);
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url_id", mUrlId);
                params.put("custom", mCustom);
                return new JSONObject(params).toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("api_key", user.getApiKey());
                return params;
            }};

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        ThrwAtNetworkSingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @SuppressLint("StaticFieldLeak")
    private void addURLToDatabase(final URLItems items){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... p){
                ThrwAtURLManager.getInstance(mContext).addURL(items);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void updateCustomURLToDatabase(final String urlId, final String custom){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... p){
                ThrwAtURLManager.getInstance(mContext).updateCustomURL(urlId, custom);
                return null;
            }
        }.execute();
    }

    private void completeTask(final boolean success, final String message, final String url, final String urlId, final String tinyUrl){
        ThrwAtShortenerTask o = new ThrwAtShortenerTask() {
            @Override
            public boolean isSuccessful() {
                return success;
            }

            @Override
            public String getUrlId() {
                return urlId;
            }

            @Override
            public String getTinyUrl() {
                return tinyUrl;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getUrl(){
                return url;
            }
        };
        onUrlShortListener.onComplete(o);
    }

   
}
