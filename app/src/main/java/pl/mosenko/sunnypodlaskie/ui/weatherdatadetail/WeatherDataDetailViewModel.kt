package pl.mosenko.sunnypodlaskie.ui.weatherdatadetail

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pl.mosenko.sunnypodlaskie.ui.Event
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
import pl.mosenko.sunnypodlaskie.repository.Result
import pl.mosenko.sunnypodlaskie.repository.Result.Error
import pl.mosenko.sunnypodlaskie.repository.Result.Success
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository

class WeatherDataDetailViewModel(
    private val repository: WeatherDataRepository
) : ViewModel() {

    private val _city = MutableLiveData<String>()

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    private val _weatherDataDetails = _city.switchMap {
        repository.getWeatherDataByCity(it).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
            .map { convertResult(it) }
    }
    val weatherDataDetails: LiveData<WeatherDataDetailPresentationModel?> = _weatherDataDetails

    private fun convertResult(result: Result<WeatherData>): WeatherDataDetailPresentationModel? {
        return when (result) {
            is Success -> {
                WeatherDataDetailPresentationModel(result.data)
            }
            is Error -> {
                showErrorMessage(result.throwable)
                null
            }
            else -> null
        }
    }

    fun loadWeatherDataDetails(city: String?) {
        city?.let {
            _city.value = it
        }
    }

    private fun showErrorMessage(throwable: Throwable) {
        _errorEvent.value = Event(throwable)
    }
}
