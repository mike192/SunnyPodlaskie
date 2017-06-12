package pl.mosenko.sunnypodlaskie.repository;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.DeleteBuilder;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.RawResourceUtil;
import pl.mosenko.sunnypodlaskie.testutils.TrampolineSchedulerRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by syk on 12.06.17.
 */
public class WeatherDataRepositoryTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Rule
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

    @Mock
    RxWeatherDataAPI rxWeatherDataAPI;
    @Mock
    WeatherDataEntityDAO weatherDataEntityDAO;
    @Mock
    WeatherConditionEntityDAO weatherConditionEntities;
    @InjectMocks
    WeatherDataRepository weatherDataRepository;

    @Test
    public void loadCurrentWeatherData_ShouldCallOnNext() throws Exception {
        final AtomicBoolean onNextCalled = new AtomicBoolean(false);
        WeatherDataRepository.Callback callback = new WeatherDataRepository.Callback() {
            @Override
            public void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet) {
                onNextCalled.set(true);
            }

            @Override
            public void onError(Throwable throwable) {
                onNextCalled.set(false);
            }
        };
        when(rxWeatherDataAPI.getCurrentWeatherData()).thenReturn(
          Observable.just(getTestWeatherDataDto())
        );
        when(weatherDataEntityDAO.deleteBuilder()).thenReturn(mock(DeleteBuilder.class));
        when(weatherDataEntityDAO.create(mock(List.class))).thenReturn(1);

        weatherDataRepository.loadCurrentWeatherData(true, callback);

        Assert.assertTrue(onNextCalled.get());
    }

    private WeatherDataDto getTestWeatherDataDto() {
        Gson gson = new Gson();
        InputStream inJson = this.getClass().getClassLoader().getResourceAsStream("test_weather_data.json");
        String json = RawResourceUtil.readTextFromInputStream(inJson);
        WeatherDataDto weatherDataDto = gson.fromJson(json, WeatherDataDto.class);
        return weatherDataDto;
    }

    @Test
    public void loadCurrentWeatherData_ShouldCallOnError() throws Exception {
        final AtomicBoolean onNextCalled = new AtomicBoolean(false);
        WeatherDataRepository.Callback callback = new WeatherDataRepository.Callback() {
            @Override
            public void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet) {
                onNextCalled.set(true);
            }

            @Override
            public void onError(Throwable throwable) {
                onNextCalled.set(false);
            }
        };
        when(rxWeatherDataAPI.getCurrentWeatherData()).thenReturn(
                Observable.empty()
        );
        when(weatherDataEntityDAO.deleteBuilder()).thenReturn(mock(DeleteBuilder.class));
        when(weatherDataEntityDAO.create(mock(List.class))).thenReturn(1);

        weatherDataRepository.loadCurrentWeatherData(true, callback);

        Assert.assertFalse(onNextCalled.get());
    }
}