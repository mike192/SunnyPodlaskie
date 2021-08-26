package pl.mosenko.sunnypodlaskie.repository

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import pl.mosenko.sunnypodlaskie.api.DefaultWeatherDataApi
import pl.mosenko.sunnypodlaskie.persistence.WeatherDataDatabase
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDao
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter
import java.util.*

/**
 * Created by syk on 12.06.17.
 */
@ExperimentalCoroutinesApi
class WeatherDataRepositoryTest {

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher
    private val database: WeatherDataDatabase = mock()
    private val weatherDtoEntityConverter: WeatherDtoEntityConverter = mock()

    @Before
    fun setupTest() {
        coroutineDispatcher = TestCoroutineDispatcher()
    }

    @Test
    fun `loadWeatherData should return Loading result as the first flow data regardless from input`() =
        coroutineDispatcher.runBlockingTest {
            val defaultWeatherDataApi: DefaultWeatherDataApi = mock()
            val weatherDataEntityDao: WeatherDataEntityDao = mock()

            val repository = WeatherDataRepository(
                database,
                defaultWeatherDataApi,
                weatherDataEntityDao,
                weatherDtoEntityConverter,
                coroutineDispatcher
            )

            val weatherDataResult = repository.loadWeatherData(true)
            val firstResult = weatherDataResult.first()

            assertThat(firstResult).isInstanceOf(Result.Loading::class.java)
        }

    @Test
    fun `loadWeatherData should return data from database when forceUpdate is false`() =
        coroutineDispatcher.runBlockingTest {
            val defaultWeatherDataApi: DefaultWeatherDataApi = mock()
            val expectedWeatherDataList = listOf(
                WeatherData(
                    city = "Białystok",
                    iconKey = "100",
                    receivingTime = Date(),
                    temperature = 5.3
                ),
                WeatherData(
                    city = "Warszawa",
                    iconKey = "100",
                    receivingTime = Date(),
                    temperature = 5.6
                )
            )
            val weatherDataEntityDao: WeatherDataEntityDao = mock {
                onBlocking { getAllWeatherData() } doReturn expectedWeatherDataList
            }

            val repository = WeatherDataRepository(
                database,
                defaultWeatherDataApi,
                weatherDataEntityDao,
                weatherDtoEntityConverter,
                coroutineDispatcher
            )

            val weatherDataResult = repository.loadWeatherData(false)
            var actualWeatherDataList: List<WeatherData>? = null
            weatherDataResult.collect {
                if (it is Result.Success) {
                    actualWeatherDataList = it.data
                }
            }

            assertThat(expectedWeatherDataList).isEqualTo(actualWeatherDataList)
        }

    @Test
    fun `loadWeatherData should fetch data from rest api when forceUpdate is true`() =
        coroutineDispatcher.runBlockingTest {
            val defaultWeatherDataApi: DefaultWeatherDataApi = mock {
                onBlocking { getCurrentWeatherData() } doReturn mock()
            }
            val weatherDataEntityDao: WeatherDataEntityDao = mock()

            val repository = WeatherDataRepository(
                database,
                defaultWeatherDataApi,
                weatherDataEntityDao,
                weatherDtoEntityConverter,
                coroutineDispatcher
            )

            val weatherDataResult = repository.loadWeatherData(true)

            weatherDataResult.collect {   /* no-op */ }
            verify(defaultWeatherDataApi).getCurrentWeatherData()
        }
}
