package pl.mosenko.sunnypodlaskie.persistence.entities;

import android.provider.BaseColumns;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by syk on 27.05.17.
 */

@DatabaseTable(tableName = WeatherConditionEntity.TABLE_NAME)
public class WeatherConditionEntity implements BaseOrmLiteEntity {
    public static final String TABLE_NAME = "weather_condition";
    public static final String COLUMN_DESCRIPTION = "description";

    @Expose
    @DatabaseField(id = true, columnName = BaseColumns._ID, canBeNull = false)
    private Long _id;
    @Expose
    @DatabaseField(columnName = COLUMN_DESCRIPTION, canBeNull = false)
    private String description;

    public WeatherConditionEntity() {
    }

    public WeatherConditionEntity(Long id) {
        this._id = id;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WeatherConditionEntity{" +
                "_id=" + _id +
                ", description='" + description + '\'' +
                '}';
    }
}
