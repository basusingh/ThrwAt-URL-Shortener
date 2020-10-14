package com.basusingh.throwat;

import android.app.Activity;

import androidx.annotation.NonNull;

public abstract class ThrwAtTask {

    public abstract boolean isSuccessful();

    public abstract String getUserId();

    public abstract String getApiKey();

    public abstract String getMessage();

}
