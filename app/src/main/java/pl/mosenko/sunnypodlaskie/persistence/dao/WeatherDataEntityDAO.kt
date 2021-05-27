package pl.mosenko.sunnypodlaskie.persistence.dao

import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.DatabaseTableConfig
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 16.05.17.
 */
class WeatherDataEntityDAO : AbstractBaseDaoImpl<WeatherDataEntity?, Long?> {
    constructor(dataClass: Class<WeatherDataEntity?>?) : super(dataClass) {}
    constructor(connectionSource: ConnectionSource?) : super(connectionSource, WeatherDataEntity::class.java) {}
    constructor(connectionSource: ConnectionSource?, tableConfig: DatabaseTableConfig<WeatherDataEntity?>?) : super(connectionSource, tableConfig) {}
}
