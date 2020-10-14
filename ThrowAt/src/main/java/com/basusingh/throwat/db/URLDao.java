package com.basusingh.throwat.db;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

@androidx.room.Dao
public interface URLDao {

    @Query("SELECT * FROM URLItems ORDER BY uid ASC")
    LiveData<List<URLItems>> getAllURLLiveAscending();

    @Query("SELECT * FROM URLItems ORDER BY uid DESC")
    LiveData<List<URLItems>> getAllURLLive();

    @Query("SELECT * FROM URLItems ORDER BY uid ASC")
    List<URLItems> getAllURLAscending();

    @Query("SELECT * FROM URLItems ORDER BY uid DESC")
    List<URLItems> getAllURL();

    @Query("SELECT * FROM URLItems ORDER BY uid ASC LIMIT :count")
    LiveData<List<URLItems>> getNumberOfURLLiveAscending(int count);

    @Query("SELECT * FROM URLItems ORDER BY uid DESC LIMIT :count")
    LiveData<List<URLItems>> getNumberOfURLLive(int count);

    @Query("SELECT * FROM URLItems ORDER BY uid ASC LIMIT :count")
    List<URLItems> getNumberOfURLAscending(int count);

    @Query("SELECT * FROM URLItems ORDER BY uid DESC LIMIT :count")
    List<URLItems> getNumberOfURL(int count);

    @Query("SELECT tags FROM URLItems WHERE url_id = :urlId")
    String getAllTags(String urlId);

    @Query("SELECT tags FROM URLItems WHERE url_id = :urlId")
    LiveData<String> getAllTagsLive(String urlId);

    @Query("UPDATE URLItems SET tags = :tags WHERE url_id = :urlId")
    void addTags(String urlId, String tags);

    @Query("SELECT tiny_url FROM URLItems WHERE url_id = :urlId")
    String getTinyUrlByID(String urlId);

    @Insert
    void insertAll(List<URLItems> mList);

    @Insert
    void insert(URLItems mItem);

    @Query("UPDATE URLItems SET tiny_url = :custom WHERE url_id = :urlId")
    void updateCustomURL(String urlId, String custom);

    @Query("UPDATE URLItems SET urlProtected = :urlProtected WHERE url_id = :urlId")
    void updateURLPasswordProtected(String urlId, String urlProtected);

    @Query("DELETE FROM URLItems")
    void deleteAll();

    @Query("DELETE FROM URLItems WHERE url_id = :urlId")
    void deleteURLById(String urlId);
}
