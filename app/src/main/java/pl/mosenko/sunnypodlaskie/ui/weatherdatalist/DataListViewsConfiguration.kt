package pl.mosenko.sunnypodlaskie.ui.weatherdatalist

enum class DataListViewsConfiguration(
    val content: Boolean,
    val loading: Boolean,
    val empty: Boolean,
    val error: Boolean
) {
    CONTENT(true, false, false, false),
    LOADING(false, true, false, false),
    EMPTY(false, false, true, false),
    ERROR(false, false, false, true)
}
