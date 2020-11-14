package com.basusingh.throwat.db;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class URLDao_Impl implements URLDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<URLItems> __insertionAdapterOfURLItems;

  private final SharedSQLiteStatement __preparedStmtOfAddTags;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCustomURL;

  private final SharedSQLiteStatement __preparedStmtOfUpdateURLPasswordProtected;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteURLById;

  public URLDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfURLItems = new EntityInsertionAdapter<URLItems>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `URLItems` (`uid`,`url_id`,`url`,`tiny_url`,`timestamp`,`tags`,`urlProtected`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, URLItems value) {
        stmt.bindLong(1, value.getUid());
        if (value.urlId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.urlId);
        }
        if (value.url == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.url);
        }
        if (value.tinyUrl == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.tinyUrl);
        }
        if (value.timestamp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timestamp);
        }
        if (value.tags == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.tags);
        }
        if (value.urlProtected == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.urlProtected);
        }
      }
    };
    this.__preparedStmtOfAddTags = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE URLItems SET tags = ? WHERE url_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCustomURL = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE URLItems SET tiny_url = ? WHERE url_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateURLPasswordProtected = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE URLItems SET urlProtected = ? WHERE url_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM URLItems";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteURLById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM URLItems WHERE url_id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<URLItems> mList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfURLItems.insert(mList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final URLItems mItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfURLItems.insert(mItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addTags(final String urlId, final String tags) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAddTags.acquire();
    int _argIndex = 1;
    if (tags == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, tags);
    }
    _argIndex = 2;
    if (urlId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, urlId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfAddTags.release(_stmt);
    }
  }

  @Override
  public void updateCustomURL(final String urlId, final String custom) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCustomURL.acquire();
    int _argIndex = 1;
    if (custom == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, custom);
    }
    _argIndex = 2;
    if (urlId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, urlId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateCustomURL.release(_stmt);
    }
  }

  @Override
  public void updateURLPasswordProtected(final String urlId, final String urlProtected) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateURLPasswordProtected.acquire();
    int _argIndex = 1;
    if (urlProtected == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, urlProtected);
    }
    _argIndex = 2;
    if (urlId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, urlId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateURLPasswordProtected.release(_stmt);
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteURLById(final String urlId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteURLById.acquire();
    int _argIndex = 1;
    if (urlId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, urlId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteURLById.release(_stmt);
    }
  }

  @Override
  public LiveData<List<URLItems>> getAllURLLiveAscending() {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"URLItems"}, false, new Callable<List<URLItems>>() {
      @Override
      public List<URLItems> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
          final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final URLItems _item;
            _item = new URLItems();
            final int _tmpUid;
            _tmpUid = _cursor.getInt(_cursorIndexOfUid);
            _item.setUid(_tmpUid);
            _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
            _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
            _item.tags = _cursor.getString(_cursorIndexOfTags);
            _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<URLItems>> getAllURLLive() {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"URLItems"}, false, new Callable<List<URLItems>>() {
      @Override
      public List<URLItems> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
          final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final URLItems _item;
            _item = new URLItems();
            final int _tmpUid;
            _tmpUid = _cursor.getInt(_cursorIndexOfUid);
            _item.setUid(_tmpUid);
            _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
            _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
            _item.tags = _cursor.getString(_cursorIndexOfTags);
            _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<URLItems> getAllURLAscending() {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
      final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final URLItems _item;
        _item = new URLItems();
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item.setUid(_tmpUid);
        _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
        _item.url = _cursor.getString(_cursorIndexOfUrl);
        _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
        _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _item.tags = _cursor.getString(_cursorIndexOfTags);
        _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<URLItems> getAllURL() {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
      final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final URLItems _item;
        _item = new URLItems();
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item.setUid(_tmpUid);
        _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
        _item.url = _cursor.getString(_cursorIndexOfUrl);
        _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
        _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _item.tags = _cursor.getString(_cursorIndexOfTags);
        _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<URLItems>> getNumberOfURLLiveAscending(final int count) {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, count);
    return __db.getInvalidationTracker().createLiveData(new String[]{"URLItems"}, false, new Callable<List<URLItems>>() {
      @Override
      public List<URLItems> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
          final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final URLItems _item;
            _item = new URLItems();
            final int _tmpUid;
            _tmpUid = _cursor.getInt(_cursorIndexOfUid);
            _item.setUid(_tmpUid);
            _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
            _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
            _item.tags = _cursor.getString(_cursorIndexOfTags);
            _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<URLItems>> getNumberOfURLLive(final int count) {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, count);
    return __db.getInvalidationTracker().createLiveData(new String[]{"URLItems"}, false, new Callable<List<URLItems>>() {
      @Override
      public List<URLItems> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
          final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final URLItems _item;
            _item = new URLItems();
            final int _tmpUid;
            _tmpUid = _cursor.getInt(_cursorIndexOfUid);
            _item.setUid(_tmpUid);
            _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
            _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
            _item.tags = _cursor.getString(_cursorIndexOfTags);
            _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<URLItems> getNumberOfURLAscending(final int count) {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, count);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
      final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final URLItems _item;
        _item = new URLItems();
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item.setUid(_tmpUid);
        _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
        _item.url = _cursor.getString(_cursorIndexOfUrl);
        _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
        _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _item.tags = _cursor.getString(_cursorIndexOfTags);
        _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<URLItems> getNumberOfURL(final int count) {
    final String _sql = "SELECT * FROM URLItems ORDER BY uid DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, count);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfUrlId = CursorUtil.getColumnIndexOrThrow(_cursor, "url_id");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfTinyUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tiny_url");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfUrlProtected = CursorUtil.getColumnIndexOrThrow(_cursor, "urlProtected");
      final List<URLItems> _result = new ArrayList<URLItems>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final URLItems _item;
        _item = new URLItems();
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item.setUid(_tmpUid);
        _item.urlId = _cursor.getString(_cursorIndexOfUrlId);
        _item.url = _cursor.getString(_cursorIndexOfUrl);
        _item.tinyUrl = _cursor.getString(_cursorIndexOfTinyUrl);
        _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _item.tags = _cursor.getString(_cursorIndexOfTags);
        _item.urlProtected = _cursor.getString(_cursorIndexOfUrlProtected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String getAllTags(final String urlId) {
    final String _sql = "SELECT tags FROM URLItems WHERE url_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (urlId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, urlId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<String> getAllTagsLive(final String urlId) {
    final String _sql = "SELECT tags FROM URLItems WHERE url_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (urlId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, urlId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"URLItems"}, false, new Callable<String>() {
      @Override
      public String call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final String _result;
          if(_cursor.moveToFirst()) {
            _result = _cursor.getString(0);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public String getTinyUrlByID(final String urlId) {
    final String _sql = "SELECT tiny_url FROM URLItems WHERE url_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (urlId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, urlId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
