package pl.mosenko.sunnypodlaskie.util

import android.util.Base64

/**
 * Created by syk on 11.05.17.
 */
class WeatherApiKeyProvider {
    companion object {
        init {
            System.loadLibrary("api_keys")
        }
    }

    private external fun getEncodedAPIKey(): String?

    fun getDecodedAPIKey(): String {
        return String(Base64.decode(getEncodedAPIKey()!!.toByteArray(), Base64.DEFAULT))
    }
}
