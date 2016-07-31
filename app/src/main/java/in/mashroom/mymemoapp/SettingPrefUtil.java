package in.mashroom.mymemoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

/**
 * Created by rhiramat on 2016/07/31.
 */
public class SettingPrefUtil {

    public static final String PREF_FILE_NAME = "settings";

    private static final String KEY_FILE_NAME_PREFIX = "file.name.prefix";
    private static final String KEY_FILE_NAME_PREFIX_DEFAULT = "memo";

    private static final String KEY_TEXT_SIZE = "text.size";
    public static final String TEXT_SIZE_LARGE = "text.size.large";
    public static final String TEXT_SIZE_MEDIUM = "text.size.medium";
    public static final String TEXT_SIZE_SMALL = "text.size.small";

    private static final String KEY_TEXT_STYLE = "text.style";
    public static final String TEXT_STYLE_BOLD = "text.style.bold";
    public static final String TEXT_STYLE_ITALIC = "text.style.italic";

    private static final String KEY_SCREEN_REVERSE = "screen.reverse";

    public static String getFileNamePrefix(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);

        return sp.getString(KEY_FILE_NAME_PREFIX, KEY_FILE_NAME_PREFIX_DEFAULT);
    }

    public static float getFontSize(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                PREF_FILE_NAME, Context.MODE_PRIVATE);
        String storedSize = sp.getString(KEY_TEXT_SIZE, TEXT_SIZE_MEDIUM);

        switch (storedSize) {
            case TEXT_SIZE_LARGE:
                return context.getResources().getDimension(R.dimen.settings_text_size_large);
            case TEXT_SIZE_MEDIUM:
                return context.getResources().getDimension(R.dimen.settings_text_size_medium);
            case TEXT_SIZE_SMALL:
                return context.getResources().getDimension(R.dimen.settings_text_size_small);
            default:
                return context.getResources().getDimension(R.dimen.settings_text_size_medium);
        }
    }

    public static int getTypeface(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                PREF_FILE_NAME, Context.MODE_PRIVATE);
        Set<String> storedTypeface = sp.getStringSet(KEY_TEXT_STYLE,
                Collections.<String>emptySet());

        int typefaceBit = Typeface.NORMAL;
        for(String value : storedTypeface) {
            switch (value) {
                case TEXT_STYLE_ITALIC:
                    typefaceBit |= Typeface.ITALIC;
                    break;
                case TEXT_STYLE_BOLD:
                    typefaceBit |= Typeface.BOLD;
                    break;
            }
        }
        return typefaceBit;
    }


    public static boolean isScreenReverse(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_SCREEN_REVERSE, false);
    }
}
