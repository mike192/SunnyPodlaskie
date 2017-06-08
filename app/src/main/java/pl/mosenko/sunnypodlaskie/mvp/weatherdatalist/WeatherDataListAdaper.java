package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil;

import static android.media.CamcorderProfile.get;

/**
 * Created by syk on 15.05.17.
 */

public class WeatherDataListAdaper extends RecyclerView.Adapter<WeatherDataListAdaper.WeatherViewHolder> {

    private List<WeatherDataEntity> weatherDataList;
    private Context context;
    private WeatherDataClickedListener clickHandler;

    public WeatherDataListAdaper(Context context, WeatherDataClickedListener clickHandler) {
        this.context = context;
        this.weatherDataList = new ArrayList<>();
        this.clickHandler = clickHandler;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View itemView = LayoutInflater.from(context).inflate(R.layout.weather_data_list_item, parent, false);
        itemView.setFocusable(true);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        WeatherDataEntity weatherInfo = weatherDataList.get(position);
        int iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherInfo.getIconKey());
        holder.weatherIcon.setImageResource(iconResource);
        holder.city.setText(weatherInfo.getCity());
        holder.temperature.setText(WeatherDataUtil.getFormattedTemperature(weatherInfo.getTemperature()));
        holder.weatherDescription.setText(weatherInfo.getWeatherCondition().getDescription());
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public void swapWeatherList(List<WeatherDataEntity> weatherList) {
        this.weatherDataList = weatherList;
        notifyDataSetChanged();
    }

    public interface WeatherDataClickedListener {
        void onWeatherDataItemClick(long id);
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.WeatherDataListFragment_icon_weather) ImageView weatherIcon;
        @BindView(R.id.WeatherDataListFragment_city) TextView city;
        @BindView(R.id.WeatherDataListFragment_weather_description) TextView weatherDescription;
        @BindView(R.id.WeatherDataListFragment_temperature) TextView temperature;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            WeatherDataEntity weatherDataEntity = weatherDataList.get(adapterPosition);
            clickHandler.onWeatherDataItemClick(weatherDataEntity.getId());
        }
    }
}
