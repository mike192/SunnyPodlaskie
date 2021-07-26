package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import androidx.lifecycle.*
import pl.mosenko.sunnypodlaskie.mvp.Event
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.Result
import pl.mosenko.sunnypodlaskie.repository.Result.Error
import pl.mosenko.sunnypodlaskie.repository.Result.Success
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.WeatherDataDetailPresentationModelConverter

class WeatherDataDetailViewModel(
    val repository: WeatherDataRepository
) : ViewModel() {

    private val _city = MutableLiveData<String>()

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    private val _weatherDataDetails = _city.switchMap {
        repository.observeWeatherDataByCity(it)
            .map { convertResult(it) }
    }
    val weatherDataDetails: LiveData<WeatherDataDetailPresentationModel?> = _weatherDataDetails

    private fun convertResult(result: Result<WeatherDataEntity>): WeatherDataDetailPresentationModel? {
        return if (result is Success) {
            WeatherDataDetailPresentationModelConverter.convert(result.data)
        } else if (result is Error) {
            showErrorMessage(result.exception)
            null
        } else null
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
