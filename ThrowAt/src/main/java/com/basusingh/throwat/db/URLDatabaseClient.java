package com.basusingh.throwat.db;

import android.content.Context;

import androidx.room.Room;

public class URLDatabaseClient {

    private Context mCtx;
    private static URLDatabaseClient mInstance;

    private URLAppDatabase URLAppDatabase;

    private URLDatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        URLAppDatabase = Room.databaseBuilder(mCtx, URLAppDatabase.class, "ThrwAtURL").build();
    }

    public static synchronized URLDatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new URLDatabaseClient(mCtx);
        }
        return mInstance;
    }

    public URLAppDatabase getURLAppDatabase() {
        return URLAppDatabase;
    }
}
