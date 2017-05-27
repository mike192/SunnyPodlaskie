package pl.mosenko.sunnypodlaskie.persistence.entities;

import android.provider.BaseColumns;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity.TABLE_NAME;

/**
 * Created by syk on 27.05.17.
 */

@DatabaseTable(tableName = TABLE_NAME)
public class WeatherConditionEntity {
    public static final String TABLE_NAME = "weather_condition";
    public static final String COLUMN_DESCRIPTION = "description";

    @Expose
    @DatabaseField(id = true, columnName = BaseColumns._ID, canBeNull = false)
    private Long id;
    @Expose
    @DatabaseField(columnName = COLUMN_DESCRIPTION, canBeNull = false)
    private String description;

    public WeatherConditionEntity() {
    }

    public WeatherConditionEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
