package pl.mosenko.sunnypodlaskie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WeatherDataListFragment.OnWeatherDataItemClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
        setupTwoPane();
    }

    private void setupTwoPane() {
        if (findViewById(R.id.weather_details_container) != null) {
            mTwoPane = true;
        }
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
        if (mTwoPane) {
            replaceWeatherDataDetailsFragment(weatherDataId);
        } else {
            startWeatherDataDetailsActivity(weatherDataId);
        }
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "Clicked: " + weatherDataId, Toast.LENGTH_SHORT).show();
        }
    }

    private void startWeatherDataDetailsActivity(long weatherDataId) {
        Intent intent = new Intent(this, WeatherDataDetailsActivity.class);
        intent.putExtra(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, weatherDataId);
        startActivity(intent);
    }

    private void replaceWeatherDataDetailsFragment(long weatherDataId) {
        Bundle arguments = new Bundle();
        arguments.putLong(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, weatherDataId);
        WeatherDataDetailFragment fragment = new WeatherDataDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.weather_details_container, fragment)
                .commit();
    }
}
