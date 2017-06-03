package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil;

import static android.media.CamcorderProfile.get;

/**
 * Created by syk on 15.05.17.
 */

public class WeatherAdaper extends RecyclerView.Adapter<WeatherAdaper.WeatherViewHolder> {

    private java.util.List<WeatherDataEntity> mWeatherList;
    private Context mContext;
    private WeatherDataClickedListener mClickHandler;

    public WeatherAdaper(Context context, WeatherDataClickedListener clickHandler) {
        this.mContext = context;
        this.mWeatherList = new ArrayList<WeatherDataEntity>();
        this.mClickHandler = clickHandler;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View itemView = LayoutInflater.from(mContext).inflate(R.layout.weather_data_list_item, parent, false);
        itemView.setFocusable(true);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        WeatherDataEntity weatherInfo = mWeatherList.get(position);
        int iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherInfo.getIconKey());
        holder.weatherIcon.setImageResource(iconResource);
        holder.city.setText(weatherInfo.getCity());
        holder.temperature.setText(WeatherDataUtil.getFormattedTemperature(weatherInfo.getTemperature()));
        holder.weatherDescription.setText(weatherInfo.getWeatherCondition().getDescription());
    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }

    public void swapWeatherList(java.util.List<WeatherDataEntity> weatherList) {
        this.mWeatherList = weatherList;
        notifyDataSetChanged();
    }

    public interface WeatherDataClickedListener {
        void onWeatherDataItemClick(long id);
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.icon_weather) ImageView weatherIcon;
        @BindView(R.id.city) TextView city;
        @BindView(R.id.weather_description) TextView weatherDescription;
        @BindView(R.id.temperature) TextView temperature;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            WeatherDataEntity weatherDataEntity = mWeatherList.get(adapterPosition);
            mClickHandler.onWeatherDataItemClick(weatherDataEntity.getId());
        }
    }
}
