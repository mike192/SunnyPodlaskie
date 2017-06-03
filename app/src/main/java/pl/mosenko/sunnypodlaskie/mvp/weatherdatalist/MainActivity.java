package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsActivity;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailsActivity;
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil;

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
        WeatherDataAlarmSyncUtil.startAlarmOnRunApp(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
