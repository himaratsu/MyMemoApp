package in.mashroom.mymemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by rhiramat on 2016/07/31.
 */
public class MemoRepository {

    // prefix-yyyy-mm-dd-HH-MM-SS.txt
    private static final String MEMO_FILE_FORMAT = "%1$s-%2$tF-%2$tH-%2$tM-%2$tS.txt";

    public static Uri store(Context context, String memo) {
        File outputDir;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            outputDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        } else {
            outputDir = new File(context.getExternalFilesDir(null), "Documents");
        }

        if (outputDir == null) {
            return null;
        }

        boolean hasDirectory = true;

        if (!outputDir.exists() || !outputDir.isDirectory()) {
            hasDirectory = outputDir.mkdirs()
        }

        if (!hasDirectory) {
            return null;
        }

        File outputFile = saveAsFile(context, outputDir, memo);

        if (outputFile == null) {
            return null;
        }

        String title = memo.length() > 10 ? memo.substring(0, 10) : memo;
        ContentValues values = new ContentValues();
        values.put(MemoDBHelper.TITLE, title);
        values.put(MemoDBHelper.DATA, outputFile.getAbsolutePath());
        values.put(MemoDBHelper.DATA_ADDED, System.currentTimeMillis());

        return context.getContentResolver().insert(MemoProvider.CONTENT_URI, values);
    }

    private static File saveAsFile(Context context, File outputDir, String memo) {
        String fileNamePrefix = SettingPrefUtil.getFileNamePrefix(context);
        Calendar now = Calendar.getInstance();
        String fileName = String.format(MEMO_FILE_FORMAT, fileNamePrefix, now);
        File outputFile = new File(outputDir, fileName);
        FileWriter writer = null;

        try {
            writer = new FileWriter(outputFile);
            writer.write(memo);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outputFile;
    }

}
