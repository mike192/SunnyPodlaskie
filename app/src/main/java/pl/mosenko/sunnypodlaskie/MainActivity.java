package pl.mosenko.sunnypodlaskie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WeatherDataListFragment.OnWeatherDataItemClickListener {

    private final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
    }

    private void initializeActivity() {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0f);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onWeatherDataItemSelected(long weatherDataId) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "Clicked: " + weatherDataId, Toast.LENGTH_LONG).show();
        }
    }
}
