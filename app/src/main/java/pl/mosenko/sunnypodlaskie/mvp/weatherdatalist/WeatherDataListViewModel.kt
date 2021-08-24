package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import androidx.databinding.Bindable
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.Event
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.Result
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.ConnectivityUtil

class WeatherDataListViewModel(
    private var repository: WeatherDataRepository,
    private var connectivityUtil: ConnectivityUtil
) : ViewModel() {

    private val _refreshList = MutableLiveData(false)
    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val _weatherDataList = _refreshList.switchMap {
        repository.loadWeatherData(it).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
    val weatherDataList: LiveData<Result<List<WeatherDataEntity>>> = _weatherDataList

    fun loadWeatherData(forceUpdate: Boolean) {
        if (forceUpdate && !connectivityUtil.isNetworkAvailable()) {
            _snackbarMessage.value = Event(R.string.message_no_connection)
        } else {
            _refreshList.value = forceUpdate
        }
    }
}
