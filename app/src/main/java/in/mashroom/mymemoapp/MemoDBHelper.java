package in.mashroom.mymemoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rhiramat on 2016/07/31.
 */
public class MemoDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "memo.db";

    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "memo";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DATA = "_data";
    public static final String DATE_ADDED = "date_added";
    public static final String DATE_MODIFIED = "date_modified";

    public MemoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TITLE + " TEXT, " +
                        DATA + " TEXT, " +
                        DATE_ADDED + " INTEGER NOT NULL, " +
                        DATE_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL " +
                        ")";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // バージョン管理
    }
}
