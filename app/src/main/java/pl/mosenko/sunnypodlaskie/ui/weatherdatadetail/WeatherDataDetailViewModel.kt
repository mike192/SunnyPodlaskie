package pl.mosenko.sunnypodlaskie.ui.weatherdatadetail

import androidx.lifecycle.*
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
import pl.mosenko.sunnypodlaskie.repository.Result
import pl.mosenko.sunnypodlaskie.repository.Result.Error
import pl.mosenko.sunnypodlaskie.repository.Result.Success
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.ui.common.Event

class WeatherDataDetailViewModel(
    private val repository: WeatherDataRepository
) : ViewModel() {

    private val _city = MutableLiveData<String>()

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    private val _weatherDataDetails = _city.switchMap {
        repository.getWeatherDataByCity(it).asLiveData(viewModelScope.coroutineContext)
            .map { result -> convertResult(result) }
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
