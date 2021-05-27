package pl.mosenko.sunnypodlaskie.util

import android.content.Context
import android.util.Log
import pl.mosenko.sunnypodlaskie.BuildConfig
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by syk on 27.05.17.
 */
object RawResourceUtil {
    private val TAG = RawResourceUtil::class.java.simpleName
    fun readRawTextFile(context: Context, resId: Int): String? {
        val inputStream = context.resources.openRawResource(resId)
        return readTextFromInputStream(inputStream)
    }

    fun readTextFromInputStream(inputStream: InputStream?): String? {
        try {
            val reader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            reader.close()
            return sb.toString()
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "Exception during reading text from the raw file.", e)
            }
        }
        return null
    }
}
