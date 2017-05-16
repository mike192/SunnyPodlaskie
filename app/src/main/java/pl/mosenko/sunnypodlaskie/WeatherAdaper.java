package pl.mosenko.sunnypodlaskie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.mosenko.sunnypodlaskie.model.List;
import pl.mosenko.sunnypodlaskie.util.WeatherUtil;

import static android.R.attr.x;

/**
 * Created by syk on 15.05.17.
 */

public class WeatherAdaper extends RecyclerView.Adapter<WeatherAdaper.WeatherViewHolder> {

    private java.util.List<pl.mosenko.sunnypodlaskie.model.List> weatherList;
    private Context context;

    public WeatherAdaper(Context context, java.util.List<List> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View itemView = LayoutInflater.from(context).inflate(R.layout.weather_list_item, parent, false);
        itemView.setFocusable(true);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        List weatherInfo = weatherList.get(position);
        int iconResource = WeatherUtil.getWeatherIconResourceByCode(weatherInfo.getWeather().get(0).getIcon());
        holder.weatherIcon.setImageResource(iconResource);
        holder.city.setText(weatherInfo.getName());
        holder.temperature.setText(String.format("%2.1f", weatherInfo.getMain().getTemp())  + "\u00b0");
        holder.weatherDescription.setText(weatherInfo.getWeather().get(0).getDescription());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void swapWeatherList(java.util.List<List> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_weather) ImageView weatherIcon;
        @BindView(R.id.city) TextView city;
        @BindView(R.id.weather_description) TextView weatherDescription;
        @BindView(R.id.temperature) TextView temperature;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
