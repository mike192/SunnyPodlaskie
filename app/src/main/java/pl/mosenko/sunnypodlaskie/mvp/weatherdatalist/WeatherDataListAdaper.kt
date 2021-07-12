package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import pl.mosenko.sunnypodlaskie.databinding.WeatherDataListItemBinding
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListAdaper.WeatherViewHolder
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil
import java.util.*

/**
 * Created by syk on 15.05.17.
 */
class WeatherDataListAdaper(
    val clickHandler: WeatherDataClickedListener
) : RecyclerView.Adapter<WeatherViewHolder>() {

    private var weatherDataList: MutableList<WeatherDataEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemBinding = WeatherDataListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WeatherViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherDataList[position])
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

    inner class WeatherViewHolder(private val itemBinding: WeatherDataListItemBinding) :
        ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(weatherInfo: WeatherDataEntity) {
            val iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherInfo.iconKey)
            itemBinding.ivIconWeather.setImageResource(iconResource)
            itemBinding.tvCity.text = weatherInfo.city
            itemBinding.tvTemperature.text =
                WeatherDataUtil.getFormattedTemperature(weatherInfo.temperature)
            itemBinding.tvWeatherDescription.text = weatherInfo.weatherCondition?.description
        }

        override fun onClick(v: View) {
            val weatherDataEntity = weatherDataList[bindingAdapterPosition]
            clickHandler.onWeatherDataItemClick(weatherDataEntity.id!!)
        }
    }
}
