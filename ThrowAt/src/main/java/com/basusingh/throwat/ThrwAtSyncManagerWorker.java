package com.basusingh.throwat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basusingh.throwat.db.URLItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.basusingh.throwat.utils.Helper.THRWAT_SERVER_URL;

public class ThrwAtSyncManagerWorker extends Worker {

    public ThrwAtSyncManagerWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        if(ThrwAt.getInstance(getApplicationContext()).isRegistered()){
            loadData();
            return Result.success();
        } else {
            return Result.failure();
        }

    }

    private void loadData(){
        Log.e("Started: 111", "Okay");
        final ThrwAtUser user = ThrwAt.getInstance(getApplicationContext()).getCurrentUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, THRWAT_SERVER_URL + "getAll",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject o = new JSONObject(response);
                            if(o.getBoolean("error")){
                                errorOccurred(o.getString("message"));
                            } else {
                                decodeAndUpdate(o.getString("data"));
                            }
                        } catch (Exception e){
                            errorOccurred("Main Parse Error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorOccurred("Internet Connection");
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
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        ThrwAtNetworkSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @SuppressLint("StaticFieldLeak")
    private void decodeAndUpdate(String data){
        final List<URLItems> mList = new ArrayList<>();
        try{
            JSONArray a = new JSONArray(data);
            for(int i = 0; i<a.length(); i++){
                JSONObject o = a.getJSONObject(i);
                URLItems items = new URLItems();
                items.setUrlId(o.getString("url_id"));
                items.setUrl(o.getString("url"));
                items.setTimestamp(o.getString("timestamp"));
                items.setTinyUrl(o.getString("tiny_url"));
                items.setUrlProtected(o.getString("protected"));
                mList.add(items);
            }
        } catch (Exception e){
            e.printStackTrace();
            errorOccurred("Parsing");
        }
        if(!mList.isEmpty()){
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... p){
                    ThrwAtURLManager.getInstance(getApplicationContext()).deleteAll();
                    ThrwAtURLManager.getInstance(getApplicationContext()).addAllURL(mList);
                    return null;
                }
            }.execute();
        }
    }

    private void errorOccurred(String message){
        //Log.e("Error == ", message);
    }
}
