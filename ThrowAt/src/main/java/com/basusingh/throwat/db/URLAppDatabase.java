package com.basusingh.throwat.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {URLItems.class}, version = 1)
public abstract class URLAppDatabase extends RoomDatabase {
    public abstract URLDao UrlDao();
}
