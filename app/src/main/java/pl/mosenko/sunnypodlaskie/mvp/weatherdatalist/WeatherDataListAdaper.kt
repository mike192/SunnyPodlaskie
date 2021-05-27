package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListAdaper.WeatherViewHolder
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil
import java.util.*

/**
 * Created by syk on 15.05.17.
 */
class WeatherDataListAdaper(private val context: Context?, clickHandler: WeatherDataClickedListener?) : RecyclerView.Adapter<WeatherViewHolder?>() {
    private var weatherDataList: MutableList<WeatherDataEntity?>?
    private val clickHandler: WeatherDataClickedListener?
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherViewHolder? {
        val itemView = LayoutInflater.from(context).inflate(R.layout.weather_data_list_item, parent, false)
        itemView.isFocusable = true
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder?, position: Int) {
        val weatherInfo = weatherDataList.get(position)
        val iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherInfo.getIconKey())
        holder.weatherIcon.setImageResource(iconResource)
        holder.city.setText(weatherInfo.getCity())
        holder.temperature.setText(WeatherDataUtil.getFormattedTemperature(weatherInfo.getTemperature()))
        holder.weatherDescription.setText(weatherInfo.getWeatherCondition().description)
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    fun swapWeatherList(weatherList: MutableList<WeatherDataEntity?>?) {
        weatherDataList = weatherList
        notifyDataSetChanged()
    }

    interface WeatherDataClickedListener {
        open fun onWeatherDataItemClick(id: Long)
    }

    inner class WeatherViewHolder(itemView: View?) : ViewHolder(itemView), View.OnClickListener {
        @kotlin.jvm.JvmField
        @BindView(R.id.WeatherDataListFragment_icon_weather)
        var weatherIcon: ImageView? = null

        @kotlin.jvm.JvmField
        @BindView(R.id.WeatherDataListFragment_city)
        var city: TextView? = null

        @kotlin.jvm.JvmField
        @BindView(R.id.WeatherDataListFragment_weather_description)
        var weatherDescription: TextView? = null

        @kotlin.jvm.JvmField
        @BindView(R.id.WeatherDataListFragment_temperature)
        var temperature: TextView? = null
        override fun onClick(v: View?) {
            val adapterPosition = adapterPosition
            val weatherDataEntity = weatherDataList.get(adapterPosition)
            clickHandler.onWeatherDataItemClick(weatherDataEntity.getId())
        }

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }
    }

    init {
        weatherDataList = ArrayList()
        this.clickHandler = clickHandler
    }
}
