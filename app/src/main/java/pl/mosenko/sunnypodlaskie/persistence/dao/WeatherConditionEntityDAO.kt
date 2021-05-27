package pl.mosenko.sunnypodlaskie.persistence.dao

import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.DatabaseTableConfig
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity

/**
 * Created by syk on 06.06.17.
 */
class WeatherConditionEntityDAO : AbstractBaseDaoImpl<WeatherConditionEntity?, Long?> {
    constructor(dataClass: Class<WeatherConditionEntity?>?) : super(dataClass) {}
    constructor(connectionSource: ConnectionSource?) : super(connectionSource, WeatherConditionEntity::class.java) {}
    constructor(connectionSource: ConnectionSource?, tableConfig: DatabaseTableConfig<WeatherConditionEntity?>?) : super(connectionSource, tableConfig) {}
}
