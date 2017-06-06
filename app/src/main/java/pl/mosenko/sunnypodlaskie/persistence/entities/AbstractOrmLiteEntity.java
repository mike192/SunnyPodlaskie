package pl.mosenko.sunnypodlaskie.persistence.entities;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by syk on 16.05.17.
 */

public abstract class AbstractOrmLiteEntity implements BaseOrmLiteEntity {
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    protected Long _id;

    @Override
    public Long getId() {
        return _id;
    }
}
