package com.basusingh.throwat;

import java.io.Serializable;

public class ThrwAtUser implements Serializable {

    private String userId, apiKey;

    public ThrwAtUser(){

    }

    public ThrwAtUser(String mUserid, String mApiKey){
        this.userId = mUserid;
        this.apiKey = mApiKey;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
