package in.mashroom.mymemoapp;

import android.content.ContentProvider;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by rhiramat on 2016/07/31.
 */
public class MemoProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.android.sample.mymemoapp.memo";
    private static final String CONTENT_PATH = "files";

    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir/";
    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item/";

    public static final String MIME_ITEM = "vnd.memoapp.memo";
    public static final String MIME_TYPE_MULTIPLE = MIME_DIR_PREFIX + MIME_ITEM;
    public static final String MIME_TYPE_SINGLE = MIME_DIR_PREFIX + MIME_ITEM;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    private static final int URI_MATCH_MEMO_LIST = 1;
    private static final int URI_MATCH_MEMO_ITEM = 2;

    private static final UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sMatcher.addURI(AUTHORITY, CONTENT_PATH, URI_MATCH_MEMO_LIST);
        sMatcher.addURI(AUTHORITY, CONTENT_PATH + "/#", URI_MATCH_MEMO_ITEM);
    }

    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        MemoDBHelper helper = new MemoDBHelper(getContext());
        mDatabase = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        int match = sMatcher.match(uri);

        Cursor cursor;
        switch (match) {
            case URI_MATCH_MEMO_LIST:
                cursor = mDatabase.query(MemoDBHelper.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_MATCH_MEMO_ITEM:
                String id = uri.getLastPathSegment();
                cursor = mDatabase.query(MemoDBHelper.TABLE_NAME,
                        projection, MemoDBHelper._ID + "=" + id
                                + (TextUtils.isEmpty(selection) ? "" + " AND (" + selection + ")"),
                        selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("invalid uri: " + uri);
        }

        Context context = getContext();
        if (context != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return cursor;
    }
}
