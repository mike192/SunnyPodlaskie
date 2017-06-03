package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListActivity;

/**
 * Created by syk on 20.05.17.
 */

public class WeatherDataDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data_details);
        showUpButtonInActionBar();
        addWeatherDataDetailsFragment(savedInstanceState);
    }

    private void addWeatherDataDetailsFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getFragmentArgument();
            createWeatherDataDetailsFragment(arguments);
        }
    }

    private void createWeatherDataDetailsFragment(Bundle arguments) {
        WeatherDataDetailFragment weatherDataDetailFragment = new WeatherDataDetailFragment();
        weatherDataDetailFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.weather_data_details_container, weatherDataDetailFragment)
                .commit();
    }

    private Bundle getFragmentArgument() {
        Bundle arguments = new Bundle();
        Long weatherDataId = getIntent().getLongExtra(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, 0);
        arguments.putLong(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, weatherDataId);
        return arguments;
    }

    private void showUpButtonInActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, WeatherDataListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
