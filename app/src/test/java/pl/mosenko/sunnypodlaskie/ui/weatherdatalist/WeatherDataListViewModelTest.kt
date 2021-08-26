package pl.mosenko.sunnypodlaskie.ui.weatherdatalist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.ConnectivityUtil
import pl.mosenko.sunnypodlaskie.util.getOrAwaitValue

class WeatherDataListViewModelTest {

    @get:Rule
    var instantExecutorRole = InstantTaskExecutorRule()

    @Test
    fun `loadWeatherData should trigger snackbar message when no network is available and forceUpdate is true`() {
        val repository: WeatherDataRepository = mock()
        val connectivityUtil: ConnectivityUtil = mock {
            on { isNetworkAvailable() } doReturn false
        }
        val viewModel = WeatherDataListViewModel(repository, connectivityUtil)

        viewModel.loadWeatherData(true)

        val value = viewModel.snackbarMessage.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()).isNotNull()
    }
}
