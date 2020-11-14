package com.basusingh.throwat.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class URLAppDatabase_Impl extends URLAppDatabase {
  private volatile URLDao _uRLDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `URLItems` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `url_id` TEXT, `url` TEXT, `tiny_url` TEXT, `timestamp` TEXT, `tags` TEXT DEFAULT '', `urlProtected` TEXT DEFAULT 'no')");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '8690fb6469b0eabae705f19d58939fb1')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `URLItems`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsURLItems = new HashMap<String, TableInfo.Column>(7);
        _columnsURLItems.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsURLItems.put("url_id", new TableInfo.Column("url_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsURLItems.put("url", new TableInfo.Column("url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsURLItems.put("tiny_url", new TableInfo.Column("tiny_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsURLItems.put("timestamp", new TableInfo.Column("timestamp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsURLItems.put("tags", new TableInfo.Column("tags", "TEXT", false, 0, "''", TableInfo.CREATED_FROM_ENTITY));
        _columnsURLItems.put("urlProtected", new TableInfo.Column("urlProtected", "TEXT", false, 0, "'no'", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysURLItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesURLItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoURLItems = new TableInfo("URLItems", _columnsURLItems, _foreignKeysURLItems, _indicesURLItems);
        final TableInfo _existingURLItems = TableInfo.read(_db, "URLItems");
        if (! _infoURLItems.equals(_existingURLItems)) {
          return new RoomOpenHelper.ValidationResult(false, "URLItems(com.basusingh.throwat.db.URLItems).\n"
                  + " Expected:\n" + _infoURLItems + "\n"
                  + " Found:\n" + _existingURLItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "8690fb6469b0eabae705f19d58939fb1", "aaadc43e8c97efa02342b59ab405dfa3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "URLItems");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `URLItems`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public URLDao UrlDao() {
    if (_uRLDao != null) {
      return _uRLDao;
    } else {
      synchronized(this) {
        if(_uRLDao == null) {
          _uRLDao = new URLDao_Impl(this);
        }
        return _uRLDao;
      }
    }
  }
}
