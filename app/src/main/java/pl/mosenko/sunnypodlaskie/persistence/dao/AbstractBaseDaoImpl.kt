package pl.mosenko.sunnypodlaskie.persistence.dao

import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.DatabaseTableConfig
import pl.mosenko.sunnypodlaskie.persistence.entities.BaseOrmLiteEntity

/**
 * Created by syk on 18.05.17.
 */
abstract class AbstractBaseDaoImpl<DataType : BaseOrmLiteEntity?, IdType> : RxBaseDaoImpl<DataType?, Long?>, OrmLiteEnityDAO<DataType?> {
    protected constructor(dataClass: Class<DataType?>?) : super(dataClass) {}
    protected constructor(connectionSource: ConnectionSource?, dataClass: Class<DataType?>?) : super(connectionSource, dataClass) {}
    protected constructor(connectionSource: ConnectionSource?, tableConfig: DatabaseTableConfig<DataType?>?) : super(connectionSource, tableConfig) {}
}
