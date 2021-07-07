package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

/**
 * Created by syk on 06.06.17.
 */
data class WeatherDataDetailPresentationModel(
        var titleDetails: String? = null,
        var temperature: String? = null,
        var description: String? = null,
        var iconResource: Int = 0,
        var pressure: String? = null,
        var windDetails: String? = null,
        var humidity: String? = null,
        var sunrise: String? = null,
        var sunset: String? = null
)
