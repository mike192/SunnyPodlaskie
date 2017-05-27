package pl.mosenko.sunnypodlaskie.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by syk on 27.05.17.
 */

public class RawResourceUtil {
    private static final String TAG = RawResourceUtil.class.getSimpleName();
    private RawResourceUtil() {}

    public static String readRawTextFile(@NonNull Context context, @NonNull int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            Log.e(TAG, "Exceptino during reading text from raw file.", e);
        }
        return null;
    }
}
