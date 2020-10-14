package com.basusingh.throwat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basusingh.throwat.db.URLDatabaseClient;
import com.basusingh.throwat.db.URLItems;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.basusingh.throwat.utils.Helper.THRWAT_SERVER_URL;

public class ThrwAtURLManager {

    private static ThrwAtURLManager instance;
    private static Context mContext;
    private ThrwAtUser user;
    private onURLStatsFetchCompleteListener onURLStatsFetchCompleteListener;
    private onURLDeleteListener onURLDeleteListener;
    private onURLPasswordUpdateListener onURLPasswordUpdateListener;
    private onURLPasswordRemoveListener onURLPasswordRemoveListener;
    private onURLStatsCountFetchListener onURLStatsCountFetchListener;
    List<URLStatsItems> mList;

    private ThrwAtURLManager(Context context) {
        mContext = context;
    }

    public static synchronized ThrwAtURLManager getInstance(Context context) {
        if (instance == null) {
            instance = new ThrwAtURLManager(context);
        }
        return instance;
    }

    private void deleteURL(String urlId){
        URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().deleteURLById(urlId);
    }

    private void setURLPasswordProtected(String urlID, String urlProtected){
        URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().updateURLPasswordProtected(urlID, urlProtected);
    }

    public void addURL(URLItems items){
        URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().insert(items);
    }

    public void addAllURL(List<URLItems> items){
        URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().insertAll(items);
    }

    public void removeTag(String urlId, String tag){
        Gson gson = new Gson();
        String oldTag = URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllTags(urlId);
        if(oldTag == null || oldTag.isEmpty()){
            return;
        }
        String[] myArray = gson.fromJson(oldTag, String[].class);
        List<String> list = Arrays.asList(myArray);
        myArray = removeArrayElement(list, tag);
        if(myArray.length == 0){
            URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().addTags(urlId, "");
        } else {
            URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().addTags(urlId, gson.toJson(myArray));
        }
    }

    private static String[] removeArrayElement(List<String> arr, String val) {
        List<String> newList = new ArrayList<>();
        for (String obj : arr) {
            if (!obj.equalsIgnoreCase(val)) {
                newList.add(obj);
            }
        }

        return newList.toArray(new String[0]);
    }

    public void addTag(String urlId, String tag){
        Gson gson = new Gson();
        String oldTag = URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllTags(urlId);
        if(oldTag == null || oldTag.isEmpty()){
            String[] myArray = {tag};
            URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().addTags(urlId, gson.toJson(myArray));
        } else {
            boolean found = false;
            String[] myArray = gson.fromJson(oldTag, String[].class);
            String[] newArray = new String[myArray.length + 1];
            for (int i = 0; i < myArray.length; i++){
                if(myArray[i].equals(tag)){
                    found = true;
                    break;
                } else {
                    newArray[i] = myArray[i];
                }
            }
            if(!found){
                newArray[myArray.length] = tag;
                URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().addTags(urlId, gson.toJson(newArray));
            }
        }
    }

    public List<URLItems> getAllURLByTags(String tag){
        Gson gson = new Gson();
        List<URLItems> items = URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllURL();
        List<URLItems> newItems = new ArrayList<>();
        for(int i = 0; i<items.size(); i++){
            URLItems u = items.get(i);
            String oldTag = u.getTags();
            if(oldTag == null || oldTag.isEmpty()){
            } else {
                String[] myArray = gson.fromJson(oldTag, String[].class);
                for (String s : myArray) {
                    if (s.equals(tag)) {
                        newItems.add(u);
                        break;
                    }
                }
            }
        }
        return newItems;
    }


    public String getAllTags(String urlId){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllTags(urlId);
    }

    public LiveData<String> getAllTagsLive(String urlId){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllTagsLive(urlId);
    }

    public LiveData<List<URLItems>> getAllShortenURLSLive(){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllURLLive();
    }

    public LiveData<List<URLItems>> getAllShortenURLSLiveAscending(){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllURLLiveAscending();
    }

    public List<URLItems>getAllShortenURLS(){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllURL();
    }

    public List<URLItems> getAllShortenURLSAscending(){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getAllURLAscending();
    }

    public LiveData<List<URLItems>> getShortenURLSLive(int count){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getNumberOfURLLive(count);
    }

    public LiveData<List<URLItems>> getShortenURLSLiveAscending(int count){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getNumberOfURLLiveAscending(count);
    }

    public List<URLItems>getShortenURLS(int count){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getNumberOfURL(count);
    }

    public List<URLItems> getShortenURLSAscending(int count){
        return URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getNumberOfURLAscending(count);
    }

    public void updateCustomURL(String urlId, String customUrl){
        URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().updateCustomURL(urlId, customUrl);
    }

    public void deleteAll(){
        URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().deleteAll();
    }

    public void doForceSyncFromServer(){
        mContext.startService(new Intent(mContext, ThrwAtSyncManager.class));
    }

    public String[] parseTags(String tags){
        Gson gson = new Gson();
        return gson.fromJson(tags, String[].class);
    }

    public String getQRCodeURL(String urlId){
        String tiny = URLDatabaseClient.getInstance(mContext).getURLAppDatabase().UrlDao().getTinyUrlByID(urlId);
        return "https://quickchart.io/qr?text=https://thrw.at/" + tiny;
    }

    public void getURLStatsCount(String urlId, onURLStatsCountFetchListener var2){
        user = ThrwAt.getInstance(mContext).getCurrentUser();
        onURLStatsCountFetchListener = var2;
        fetchStatsCount(urlId);
    }

    private void fetchStatsCount(final String urlID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "stats-count",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeURLStatsCountFetchTask(false, o.getString("message"), 0);
                            } else {
                                completeURLStatsCountFetchTask(true, o.getString("message"), o.getInt("count"));
                            }
                        } catch (Exception e){;
                            completeURLStatsCountFetchTask(false, "An error occurred parsing data. Please try again.", 0);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeURLStatsCountFetchTask(false, "Internet Connection Error. Please try again.", 0);
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url_id", urlID);
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

    private void completeURLStatsCountFetchTask(final boolean res, final String message, final int count){
        ThrwAtURLStatsCountTask o = new ThrwAtURLStatsCountTask() {
            @Override
            public boolean isSuccessful() {
                return res;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public int getCount() {
                return count;
            }
        };
        onURLStatsCountFetchListener.onComplete(o);
    }

    public void getURLStats(String urlId, onURLStatsFetchCompleteListener var2){
        user = ThrwAt.getInstance(mContext).getCurrentUser();
        onURLStatsFetchCompleteListener = var2;
        fetchStats(urlId);
    }

    private void fetchStats(final String urlID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "stats",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeURLStatsFetchTask(false, o.getString("message"), null, null);
                            } else {
                                completeURLStatsFetchTask(true, o.getString("message"), o.getString("stats"), o.getString("premium"));
                            }
                        } catch (Exception e){;
                            completeURLStatsFetchTask(false, "An error occurred parsing data. Please try again.", null, null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeURLStatsFetchTask(false, "Internet Connection Error. Please try again.", null, null);
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url_id", urlID);
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
    private void completeURLStatsFetchTask(final boolean result, final String message, final String stats, final String premium){
        if(result){
            if(mList == null){
                mList = new ArrayList<>();
            }
            new AsyncTask<Void, Boolean, Boolean>(){
                @Override
                protected Boolean doInBackground(Void... p){
                    try{
                        JSONArray a = new JSONArray(stats);
                        if(premium.equalsIgnoreCase("yes")){
                            for(int i = 0; i<a.length(); i++){
                                JSONObject obj = a.getJSONObject(i);
                                URLStatsItems o = new URLStatsItems();
                                o.setTimestamp(obj.getString("timestamp"));
                                o.setStats_id(obj.getString("stats_id"));
                                o.setUrl_id(obj.getString("url_id"));
                                JSONObject obj1 = obj.getJSONObject("stats");
                                o.setReferrer(obj1.getString("referer"));
                                o.setHost(obj1.getString("host"));
                                o.setPort(obj1.getString("port"));
                                o.setUserAgentName(obj1.getString("user_agent_name"));
                                o.setUserAgentVersion(obj1.getString("user_agent_version"));
                                o.setUserAgentPlatform(obj1.getString("user_agent_platform"));
                                o.setUserCity(obj1.getString("city"));
                                o.setUserRegion(obj1.getString("region"));
                                o.setUserCountryName(obj1.getString("country"));
                                o.setUserContinentName(obj1.getString("continent"));
                                o.setUserTimezone(obj1.getString("timezone"));
                                o.setUserCurrency(obj1.getString("currency_code"));
                                mList.add(o);
                            }
                            return true;
                        } else {
                            for(int i = 0; i<a.length(); i++){
                                JSONObject obj = a.getJSONObject(i);
                                URLStatsItems o = new URLStatsItems();
                                o.setTimestamp(obj.getString("timestamp"));
                                o.setStats_id(obj.getString("stats_id"));
                                o.setUrl_id(obj.getString("url_id"));
                                JSONObject obj1 = obj.getJSONObject("stats");
                                o.setReferrer(obj1.getString("referer"));
                                o.setUserAgentName(obj1.getString("user_agent_name"));
                                o.setUserRegion(obj1.getString("region"));
                                o.setUserCountryName(obj1.getString("country"));
                                o.setUserTimezone(obj1.getString("timezone"));
                                mList.add(o);
                            }
                            return true;
                        }
                    } catch (Exception e){
                        return false;
                    }
                }
                @Override
                public void onPostExecute(Boolean val){
                    if(!val){
                        ThrwAtURLStatsFetchTask o = new ThrwAtURLStatsFetchTask() {
                            @Override
                            public boolean isSuccessful() {
                                return false;
                            }

                            @Override
                            public String getStats() {
                                return null;
                            }

                            @Override
                            public String getMessage() {
                                return "An error occurred parsing data";
                            }

                            @Override
                            public List<URLStatsItems> getURLStats() {
                                return null;
                            }

                            @Override
                            public String getPremium() {
                                return null;
                            }
                        };
                        onURLStatsFetchCompleteListener.onComplete(o);
                    } else {
                        if(mList.isEmpty()){
                            ThrwAtURLStatsFetchTask o = new ThrwAtURLStatsFetchTask() {
                                @Override
                                public boolean isSuccessful() {
                                    return false;
                                }

                                @Override
                                public String getStats() {
                                    return null;
                                }

                                @Override
                                public String getMessage() {
                                    return "No stats found for this URL";
                                }

                                @Override
                                public List<URLStatsItems> getURLStats() {
                                    return null;
                                }

                                @Override
                                public String getPremium() {
                                    return null;
                                }
                            };
                            onURLStatsFetchCompleteListener.onComplete(o);
                        } else {
                            ThrwAtURLStatsFetchTask o = new ThrwAtURLStatsFetchTask() {
                                @Override
                                public boolean isSuccessful() {
                                    return result;
                                }

                                @Override
                                public String getStats() {
                                    return stats;
                                }

                                @Override
                                public String getMessage() {
                                    return message;
                                }

                                @Override
                                public List<URLStatsItems> getURLStats() {
                                    return mList;
                                }

                                @Override
                                public String getPremium() {
                                    return premium;
                                }
                            };
                            onURLStatsFetchCompleteListener.onComplete(o);
                        }
                    }
                }
            }.execute();
        } else {
            ThrwAtURLStatsFetchTask o = new ThrwAtURLStatsFetchTask() {
                @Override
                public boolean isSuccessful() {
                    return false;
                }

                @Override
                public String getStats() {
                    return null;
                }

                @Override
                public String getMessage() {
                    return message;
                }

                @Override
                public List<URLStatsItems> getURLStats() {
                    return null;
                }

                @Override
                public String getPremium() {
                    return null;
                }
            };
            onURLStatsFetchCompleteListener.onComplete(o);
        }
    }


    public void deleteURL(String urlId, onURLDeleteListener var2){
        user = ThrwAt.getInstance(mContext).getCurrentUser();
        onURLDeleteListener = var2;
        deleteURLFromServer(urlId);
    }

    private void deleteURLFromServer(final String urlID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "delete-url",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeURLDeleteTask(false, o.getString("message"));
                            } else {
                                deleteURLLocally(urlID);
                                completeURLDeleteTask(true, o.getString("message"));
                            }
                        } catch (Exception e){;
                            completeURLDeleteTask(false, "An error occurred parsing data. Please try again.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeURLDeleteTask(false, "Internet Connection Error. Please try again.");
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url_id", urlID);
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
    private void deleteURLLocally(final String urlId){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... p){
                deleteURL(urlId);
                return null;
            }
        }.execute();
    }

    private void completeURLDeleteTask(final boolean result, final String message){
        ThrwAtURLDeleteTask o = new ThrwAtURLDeleteTask() {
            @Override
            public boolean isSuccessful() {
                return result;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
        onURLDeleteListener.onComplete(o);
    }

    public void updatePassword(String urlId, String password, onURLPasswordUpdateListener var2){
        if(password.isEmpty()){
            return;
        }
        user = ThrwAt.getInstance(mContext).getCurrentUser();
        onURLPasswordUpdateListener = var2;
        updateURLPasswordOnServer(urlId, password);
    }

    private void updateURLPasswordOnServer(final String urlID, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "update-password",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeURLUpdatePasswordTask(false, o.getString("message"));
                            } else {
                                updateURLPasswordProtected(urlID, "yes");
                                completeURLUpdatePasswordTask(true, o.getString("message"));
                            }
                        } catch (Exception e){;
                            completeURLUpdatePasswordTask(false, "An error occurred parsing data. Please try again.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeURLUpdatePasswordTask(false, "Internet Connection Error. Please try again.");
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url_id", urlID);
                params.put("password", password);
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
    private void updateURLPasswordProtected(final String urlId, final String urlProtected){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... p){
                setURLPasswordProtected(urlId, urlProtected);
                return null;
            }
        }.execute();
    }

    private void completeURLUpdatePasswordTask(final boolean result, final String message){
        ThrwAtURLPasswordUpdateTask o = new ThrwAtURLPasswordUpdateTask() {
            @Override
            public boolean isSuccessful() {
                return result;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
        onURLPasswordUpdateListener.onComplete(o);
    }


    public void removePassword(String urlId, onURLPasswordRemoveListener var2){
        user = ThrwAt.getInstance(mContext).getCurrentUser();
        onURLPasswordRemoveListener = var2;
        removeURLPasswordOnServer(urlId);
    }

    private void removeURLPasswordOnServer(final String urlID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "remove-password",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeURLRemovePasswordTask(false, o.getString("message"));
                            } else {
                                updateURLPasswordProtected(urlID, "no");
                                completeURLRemovePasswordTask(true, o.getString("message"));
                            }
                        } catch (Exception e){;
                            completeURLRemovePasswordTask(false, "An error occurred parsing data. Please try again.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeURLRemovePasswordTask(false, "Internet Connection Error. Please try again.");
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
                params.put("url_id", urlID);
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

    private void completeURLRemovePasswordTask(final boolean result, final String message){
        ThrwAtURLPasswordRemoveListener o = new ThrwAtURLPasswordRemoveListener() {
            @Override
            public boolean isSuccessful() {
                return result;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
        onURLPasswordRemoveListener.onComplete(o);
    }
}
