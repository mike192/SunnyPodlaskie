package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
class WeatherDataListAdaper(
    private val context: Context?,
    val clickHandler: WeatherDataClickedListener
) : RecyclerView.Adapter<WeatherViewHolder>() {

    private var weatherDataList: MutableList<WeatherDataEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.weather_data_list_item, parent, false)
        itemView.isFocusable = true
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherInfo = weatherDataList.get(position)
        val iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherInfo.iconKey)
        holder.weatherIcon.setImageResource(iconResource)
        holder.city.text = weatherInfo.city
        holder.temperature.text = WeatherDataUtil.getFormattedTemperature(weatherInfo.temperature)
        holder.weatherDescription.setText(weatherInfo.weatherCondition?.description)
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    fun swapWeatherList(weatherList: MutableList<WeatherDataEntity>) {
        weatherDataList = weatherList
        notifyDataSetChanged()
    }

    interface WeatherDataClickedListener {
        fun onWeatherDataItemClick(id: Long)
    }

    inner class WeatherViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.WeatherDataListFragment_icon_weather)
        lateinit var weatherIcon: ImageView

        @BindView(R.id.WeatherDataListFragment_city)
        lateinit var city: TextView

        @BindView(R.id.WeatherDataListFragment_weather_description)
        lateinit var weatherDescription: TextView

        @BindView(R.id.WeatherDataListFragment_temperature)
        lateinit var temperature: TextView

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val weatherDataEntity = weatherDataList.get(adapterPosition)
            clickHandler.onWeatherDataItemClick(weatherDataEntity.id!!)
        }

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }
    }
}
