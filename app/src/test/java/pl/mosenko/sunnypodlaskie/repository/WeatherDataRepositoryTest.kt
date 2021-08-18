package pl.mosenko.sunnypodlaskie.repository

import com.google.gson.Gson
import com.j256.ormlite.stmt.DeleteBuilder
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import pl.mosenko.sunnypodlaskie.network.api.DefaultWeatherDataApi
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDao
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.testutils.TrampolineSchedulerRule
import pl.mosenko.sunnypodlaskie.util.RawResourceUtil
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by syk on 12.06.17.
 */
class WeatherDataRepositoryTest {
    @Rule
    var rule = MockitoJUnit.rule()

    @Rule
    var trampolineSchedulerRule: TrampolineSchedulerRule? = TrampolineSchedulerRule()

    @Mock
    lateinit var defaultWeatherDataApi: DefaultWeatherDataApi

    @Mock
    lateinit var weatherDataEntityDao: WeatherDataEntityDao

    @Mock
    lateinit var weatherConditionEntities: WeatherConditionEntityDAO

    @InjectMocks
    lateinit var weatherDataRepository: WeatherDataRepository

    @Test
    @Throws(Exception::class)
    fun loadCurrentWeatherData_ShouldCallOnNext() {
        val onNextCalled = AtomicBoolean(false)
        val callback: WeatherDataRepository.Callback = object : WeatherDataRepository.Callback {
            override fun onNextWeatherDataEntities(weatherDataEntityList: MutableList<WeatherDataEntity>, isConnectedToInternet: Boolean) {
                onNextCalled.set(true)
            }

            override fun onError(throwable: Throwable) {
                onNextCalled.set(false)
            }
        }
        Mockito.`when`(defaultWeatherDataApi.getCurrentWeatherData()).thenReturn(
                Observable.just(getTestWeatherDataDto())
        )
        Mockito.`when`(weatherDataEntityDao.deleteBuilder()).thenReturn(Mockito.mock(DeleteBuilder::class.java) as DeleteBuilder<WeatherDataEntity, Long>?)
        Mockito.`when`<Int?>(weatherDataEntityDao.create(Mockito.mock(MutableList::class.java))).thenReturn(1)
        weatherDataRepository.loadCurrentWeatherData(true, callback)
        Assert.assertTrue(onNextCalled.get())
    }

    private fun getTestWeatherDataDto(): WeatherDataDto? {
        val gson = Gson()
        val inJson = this.javaClass.classLoader?.getResourceAsStream("test_weather_data.json")
        val json = RawResourceUtil.readTextFromInputStream(inJson)
        return gson.fromJson(json, WeatherDataDto::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun loadCurrentWeatherData_ShouldCallOnError() {
        val onNextCalled = AtomicBoolean(false)
        val callback: WeatherDataRepository.Callback = object : WeatherDataRepository.Callback {
            override fun onNextWeatherDataEntities(weatherDataEntityList: MutableList<WeatherDataEntity>, isConnectedToInternet: Boolean) {
                onNextCalled.set(true)
            }

            override fun onError(throwable: Throwable) {
                onNextCalled.set(false)
            }
        }
        Mockito.`when`(defaultWeatherDataApi.getCurrentWeatherData()).thenReturn(
                Observable.empty()
        )
        Mockito.`when`(weatherDataEntityDao.deleteBuilder()).thenReturn(Mockito.mock(DeleteBuilder::class.java))
        Mockito.`when`<Int?>(weatherDataEntityDao.create(Mockito.mock(MutableList::class.java))).thenReturn(1)
        weatherDataRepository.loadCurrentWeatherData(true, callback)
        Assert.assertFalse(onNextCalled.get())
    }
}
