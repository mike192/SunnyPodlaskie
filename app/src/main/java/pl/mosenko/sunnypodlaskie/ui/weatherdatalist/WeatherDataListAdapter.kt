package pl.mosenko.sunnypodlaskie.ui.weatherdatalist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import pl.mosenko.sunnypodlaskie.databinding.WeatherDataListItemBinding
import pl.mosenko.sunnypodlaskie.ui.weatherdatalist.WeatherDataListAdapter.WeatherViewHolder
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
import java.util.*

/**
 * Created by syk on 15.05.17.
 */
class WeatherDataListAdapter(
    val clickHandler: WeatherDataClickedListener
) : RecyclerView.Adapter<WeatherViewHolder>() {

    private var weatherDataList: List<WeatherData> = ArrayList()

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

    fun swapWeatherList(weatherList: List<WeatherData>) {
        weatherDataList = weatherList
        notifyDataSetChanged()
    }

    interface WeatherDataClickedListener {
        fun onWeatherDataItemClick(city: String)
    }

    inner class WeatherViewHolder(private val itemBinding: WeatherDataListItemBinding) :
        ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(weatherInfo: WeatherData) {
            itemBinding.weatherData = weatherInfo
            itemBinding.clickListener = this
            itemBinding.executePendingBindings()
        }

        override fun onClick(v: View) {
            val weatherDataEntity = weatherDataList[bindingAdapterPosition]
            clickHandler.onWeatherDataItemClick(weatherDataEntity.city)
        }
    }
}
