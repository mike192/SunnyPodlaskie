package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListActivity

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_data_details)
        showUpButtonInActionBar()
        addWeatherDataDetailsFragment(savedInstanceState)
    }

    private fun addWeatherDataDetailsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val arguments = getFragmentArgument()
            createWeatherDataDetailsFragment(arguments)
        }
    }

    private fun createWeatherDataDetailsFragment(arguments: Bundle?) {
        val weatherDataDetailFragment = WeatherDataDetailFragment()
        weatherDataDetailFragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .add(R.id.weather_data_details_container, weatherDataDetailFragment)
                .commit()
    }

    private fun getFragmentArgument(): Bundle {
        val arguments = Bundle()
        val weatherDataId = intent.getLongExtra(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, 0)
        arguments.putLong(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, weatherDataId)
        return arguments
    }

    private fun showUpButtonInActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, Intent(this, WeatherDataListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
