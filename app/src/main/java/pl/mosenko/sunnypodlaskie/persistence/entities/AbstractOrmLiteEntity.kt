package pl.mosenko.sunnypodlaskie.persistence.entities

import android.provider.BaseColumns
import com.j256.ormlite.field.DatabaseField

/**
 * Created by syk on 16.05.17.
 */
abstract class AbstractOrmLiteEntity : BaseOrmLiteEntity {
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    protected var _id: Long? = null
    override fun getId(): Long? {
        return _id
    }
}
