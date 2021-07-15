package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsActivity
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailsActivity

class WeatherDataListActivity : AppCompatActivity(), WeatherDataListFragment.Callback {
    private var twoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()
        setupTwoPane()
    }

    private fun setupTwoPane() {
        if (findViewById<View?>(R.id.fl_container) != null) {
            twoPane = true
        }
    }

    private fun initializeActivity() {
        setContentView(R.layout.activity_weather_data_list)
        supportActionBar?.elevation = 0f
    }

    override fun onItemSelected(weatherDataId: Long) {
        if (twoPane) {
            replaceWeatherDataDetailsFragment(weatherDataId)
        } else {
            startWeatherDataDetailsActivity(weatherDataId)
        }
    }

    private fun startWeatherDataDetailsActivity(weatherDataId: Long) {
        val intent = Intent(this, WeatherDataDetailsActivity::class.java)
        intent.putExtra(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, weatherDataId)
        startActivity(intent)
    }

    private fun replaceWeatherDataDetailsFragment(weatherDataId: Long) {
        val arguments = Bundle()
        arguments.putLong(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID, weatherDataId)
        val fragment = WeatherDataDetailFragment()
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
