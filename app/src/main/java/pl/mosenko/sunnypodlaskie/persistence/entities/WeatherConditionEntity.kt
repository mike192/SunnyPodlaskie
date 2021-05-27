package pl.mosenko.sunnypodlaskie.persistence.entities

import android.provider.BaseColumns
import com.google.gson.annotations.Expose
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity

/**
 * Created by syk on 27.05.17.
 */
@DatabaseTable(tableName = WeatherConditionEntity.TABLE_NAME)
class WeatherConditionEntity : BaseOrmLiteEntity {
    @Expose
    @DatabaseField(id = true, columnName = BaseColumns._ID, canBeNull = false)
    private var _id: Long? = null

    @Expose
    @DatabaseField(columnName = COLUMN_DESCRIPTION, canBeNull = false)
    private var description: String? = null

    constructor() {}
    constructor(id: Long?) {
        _id = id
    }

    override fun getId(): Long? {
        return _id
    }

    fun setId(id: Long?) {
        _id = id
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    override fun toString(): String {
        return "WeatherConditionEntity{" +
                "_id=" + _id +
                ", description='" + description + '\'' +
                '}'
    }

    companion object {
        val TABLE_NAME: String? = "weather_condition"
        val COLUMN_DESCRIPTION: String? = "description"
    }
}
