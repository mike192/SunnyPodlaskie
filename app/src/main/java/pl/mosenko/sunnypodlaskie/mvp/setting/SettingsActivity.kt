package pl.mosenko.sunnypodlaskie.mvp.setting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 23.05.17.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
