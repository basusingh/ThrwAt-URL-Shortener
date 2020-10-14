package com.basusingh.throwat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basusingh.throwat.utils.DeviceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.basusingh.throwat.utils.Helper.THRWAT_SERVER_USER;

public class ThrwAt{

    private static ThrwAt instance;
    private static Context mContext;
    private onSignupCompleteListener onSignupCompleteListener;
    private onPasswordResetCompleteListener onPasswordResetCompleteListener;
    private static final String SETTINGS_NAME = "userSharedPrefThrowAt";
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private static final String USER_ID = "userId";
    private static final String API_KEY = "apiKey";
    private static final String IS_REGISTERED = "isRegistered";

    private static final String SYNC_URL_FROM_SERVER = "syncUrlFromServer";
    private static final String SYNC_INTERVAL = "sync_interval";

    private onPremiumStatusFetchListener onPremiumStatusFetchListener;
    private ThrwAtUser user;


    private ThrwAt(Context context) {
        mContext = context;
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized ThrwAt getInstance(Context context) {
        if (instance == null) {
            instance = new ThrwAt(context);
        }
        return instance;
    }

    public void resetPassword(final String email, onPasswordResetCompleteListener var2){
        onPasswordResetCompleteListener = var2;
        if(!isEmailValid(email)){
            completePasswordReset(true, "Invalid Email");
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_USER + "send-password-reset",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            completePasswordReset(!o.getBoolean("error"), o.getString("message"));
                        } catch (Exception e){;
                            completePasswordReset(true, "An error occurred parsing data [X-1021]");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completePasswordReset(true, "Internet connection problem. Please try again.");
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("device", DeviceUtils.getDeviceInfoAsJSON());
                return new JSONObject(params).toString().getBytes();
            }};

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        ThrwAtNetworkSingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void completePasswordReset(final boolean error, final String message){
        ThrwAtPasswordResetTask obj = new ThrwAtPasswordResetTask() {
            @Override
            public boolean isSuccessful() {
                return error;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
        onPasswordResetCompleteListener.onComplete(obj);
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void registerUser(String name, String email, String password, @NonNull onSignupCompleteListener var2) {
        if(isRegistered()){
            return;
        }
        onSignupCompleteListener = var2;
        if(password.length() < 6){
            completeTask(false, null, null, "Password must be of 6 characters");
            return;
        }
        registerUserOnServer(name, email, password);
    }

    private void registerUserOnServer(final String mName, final String mEmail, final String mPassword){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_USER + "signup",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completeTask(false, null, null, o.getString("message"));
                            } else {
                                JSONObject o1 = o.getJSONObject("data");
                                setUserRegistered(o1.getString("user_id"), o1.getString("api_key"));
                                completeTask(true, o1.getString("user_id"), o1.getString("api_key"), o.getString("message"));
                            }
                        } catch (Exception e){;
                            completeTask(false, null, null, "An error occurred parsing response. Code: X-101");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completeTask(false, null, null, error.getMessage()!= null ? error.getMessage() : "Internet Connection Issue. Please try again.");
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", mName);
                params.put("email", mEmail);
                params.put("password", mPassword);
                params.put("type", "Library | Package: " + mContext.getPackageName());
                params.put("device", DeviceUtils.getDeviceInfoAsJSON());
                return new JSONObject(params).toString().getBytes();
            }};

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        ThrwAtNetworkSingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void completeTask(final boolean success, final String userId, final String apiKey, final String message){
        ThrwAtTask o = new ThrwAtTask() {
            @Override
            public boolean isSuccessful() {
                return success;
            }

            @Override
            public String getUserId() {
                return userId;
            }

            @Override
            public String getApiKey() {
                return apiKey;
            }
            @Override
            public String getMessage(){
                return message;
            }
        };
        onSignupCompleteListener.onComplete(o);
    }

    public void getPremiumStatus(onPremiumStatusFetchListener var2){
        if(!isRegistered()){
            return;
        }
        user = ThrwAt.getInstance(mContext).getCurrentUser();
        onPremiumStatusFetchListener = var2;
        fetchPremiumStatusFromServer();
    }

    private void fetchPremiumStatusFromServer(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_USER + "premium-status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                completePremiumStatusFetchTask(false, o.getString("message"), null);
                            } else {
                                completePremiumStatusFetchTask(true, o.getString("message"), o.getString("premium"));
                            }
                        } catch (Exception e){;
                            completePremiumStatusFetchTask(false, "An error occurred parsing data. Please try again.", null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        completePremiumStatusFetchTask(false, "Internet Connection Error. Please try again.", null);
                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", user.getUserId());
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

    private void completePremiumStatusFetchTask(final boolean status, final String message, final String premium){
        ThrwAtPremiumStatusTask o = new ThrwAtPremiumStatusTask() {
            @Override
            public boolean isSuccessful() {
                return status;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getPremium() {
                return premium;
            }
        };
        onPremiumStatusFetchListener.onComplete(o);
    }

    public ThrwAtUser getCurrentUser(){
        return new ThrwAtUser(mPref.getString(USER_ID, null), mPref.getString(API_KEY, null));
    }

    public boolean isRegistered(){
        return mPref.getBoolean(IS_REGISTERED, false);
    }

    public String getApiKey(){
        return mPref.getString(API_KEY, null);
    }

    public String getUserId(){
        return mPref.getString(USER_ID, null);
    }

    @SuppressLint("CommitPrefEdits")
    private void Edit() {
        if(mEditor == null){
            mEditor = mPref.edit();
        }
    }

    private boolean Commit() {
        boolean result = mEditor.commit();
        mEditor = null;
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public void logoutUser(){
        remove(USER_ID, API_KEY, IS_REGISTERED, SYNC_INTERVAL, SYNC_URL_FROM_SERVER);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... p){
                ThrwAtURLManager.getInstance(mContext).deleteAll();
                removeServerSyncTask();
                return null;
            }
        }.execute();
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    public void remove(String... keys) {
        Edit();
        for (String key : keys) {
            mEditor.remove(key);
        }
        Commit();
    }

    private void setUserRegistered(String userId, String apiKey){
        Edit();
        mEditor.putString(USER_ID, userId);
        mEditor.putString(API_KEY, apiKey);
        mEditor.putBoolean(IS_REGISTERED, true);
        Commit();
    }

    public void setServerSyncEnabled(boolean value){
        if(getServerSyncStatus() != value){
            Edit();
            mEditor.putBoolean(SYNC_URL_FROM_SERVER, value);
            Commit();
            if(value){
                setServerSyncTask();
            } else {
                removeServerSyncTask();
            }
        }
    }

    public boolean getServerSyncStatus(){
        return mPref.getBoolean(SYNC_URL_FROM_SERVER, false);
    }

    public void setServerSyncInterval(int hour){
        Edit();
        mEditor.putInt(SYNC_INTERVAL, hour);
        Commit();
    }

    public int getServerSyncInterval(){
        return mPref.getInt(SYNC_INTERVAL, 5);
    }


    // Set periodic sync
    private void setServerSyncTask(){
        PeriodicWorkRequest syncRequest =
                new PeriodicWorkRequest.Builder(ThrwAtSyncManagerWorker.class, getServerSyncInterval(), TimeUnit.HOURS)
                        .addTag("ServerSync")
                        .build();
        WorkManager
                .getInstance(mContext)
                .enqueue(syncRequest);
    }


    private void removeServerSyncTask(){
        try{
            WorkManager.getInstance(mContext).cancelAllWorkByTag("ServerSync");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getVersionCode() {
        return "1.21";
    }
}
