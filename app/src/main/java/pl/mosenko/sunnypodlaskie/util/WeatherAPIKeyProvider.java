package pl.mosenko.sunnypodlaskie.util;

import android.util.Base64;

/**
 * Created by syk on 11.05.17.
 */

public class WeatherAPIKeyProvider {

    static {
        System.loadLibrary("api_keys");
    }

    private native String getEncodedAPIKey();

    public String getDecodedAPIKey() {
        String apiKey = new String(Base64.decode(getEncodedAPIKey().getBytes(), Base64.DEFAULT));
        return apiKey;
    }

}
