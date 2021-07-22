package pl.mosenko.sunnypodlaskie.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsFragment
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment.Companion.ARG_WEATHER_DATA_ID
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main),
    WeatherDataListFragment.WeatherItemCallback,
    WeatherDataListFragment.NavigationCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.fragments.isEmpty()) {
            showDataListFragment()
        }
    }

    private fun showDataListFragment() {
        supportFragmentManager.commit {
            replace(R.id.fl_container, WeatherDataListFragment())
        }
    }

    override fun onItemSelected(weatherDataId: Long) {
        showWeatherDataDetailsFragment(weatherDataId)
    }

    private fun showWeatherDataDetailsFragment(weatherDataId: Long) {
        val fragment = WeatherDataDetailFragment().apply {
            arguments = bundleOf(ARG_WEATHER_DATA_ID to weatherDataId)
        }

        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fl_container, fragment)
        }
    }

    override fun navigateToSettings() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fl_container, SettingsFragment())
        }
    }
}
