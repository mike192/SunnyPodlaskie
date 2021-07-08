package pl.mosenko.sunnypodlaskie.persistence.converters

import androidx.room.TypeConverter
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity

object WeatherConditionEntityConverter {

    @TypeConverter
    fun toWeatherConditionId(entity: WeatherConditionEntity) = entity.id

    @TypeConverter
    fun fromWeatherConditionId(id: Int) = WeatherConditionEntity(id, weatherConditions[id]!!)
}

private val weatherConditions: Map<Int, String> = mapOf(
    200 to "burza z piorunami niewielkie opady",
    201 to "burza z piorunami umiarkowane opady",
    202 to "burza z piorunami silne opady",
    210 to "przelotna burza z piorunami",
    211 to "burza z piorunami",
    212 to "silna burza z piorunami",
    221 to "oberwanie chmury wyładowania atmosferyczne",
    230 to "burza z piorunami niewielka mżawka",
    231 to "burza z piorunami mżawka",
    232 to "burza z piorunami silna mżawka",
    300 to "niewielka mżawka",
    301 to "mżawka",
    302 to "gęsta mżawka",
    310 to "mżący deszcz",
    311 to "mżawka",
    312 to "gęsty to mżący deszcz",
    313 to "przelotne opady z mżawką",
    314 to "silne przelotne opady z mżawką",
    321 to "przelotna mżawka",
    500 to "niewielkie opady",
    501 to "umiarkowane opady",
    502 to "gęste opady",
    503 to "bardzo silne opady",
    504 to "ulewne opady",
    511 to "marznące opady",
    520 to "rzadkie przelotne opady",
    521 to "przelotne opady",
    522 to "gęste przelotne opady",
    531 to "przelotne oberwanie chmury",
    600 to "niewielkie opady śniegu",
    601 to "opady śniegu",
    602 to "silne opady śniegu",
    611 to "opady śniegu z deszczem",
    612 to "przelotne opady śniegu z deszczem",
    615 to "lekkie opady śniegu z deszczem",
    616 to "opady śniegu z deszczem",
    620 to "niewielkie przelotne opady śniegu",
    621 to "przelotne opady śniegu",
    622 to "silne przelotne opady śniegu",
    701 to "mgiełka",
    711 to "zadymienie",
    721 to "zamglenie",
    731 to "burza piaskowa",
    741 to "mgła",
    751 to "burza piaskowa",
    761 to "pył",
    762 to "pył wulkaniczny",
    771 to "szkwał",
    781 to "trąba powietrzna",
    800 to "czyste niebo",
    801 to "pogodnie",
    802 to "małe zachmurzenie",
    803 to "zachmurzenie umiarkowane",
    804 to "duże zachmurzenie",
    900 to "tornado",
    901 to "monsun",
    902 to "huragan",
    903 to "przymrozek",
    904 to "upał",
    905 to "wietrznie",
    906 to "grad",
    951 to "bezwietrznie",
    952 to "słaby wiatr",
    953 to "lekki wiatr",
    954 to "umiarkowany wiatr",
    955 to "lekka bryza",
    956 to "silna bryza",
    957 to "silny wiatr",
    958 to "szkwał",
    959 to "mroźna wichura",
    960 to "burza",
    961 to "gwałtowna burza",
    962 to "huragan"
)
