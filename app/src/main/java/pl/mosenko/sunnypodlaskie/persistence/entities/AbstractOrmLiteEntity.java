package pl.mosenko.sunnypodlaskie.persistence.entities;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by syk on 16.05.17.
 */

public abstract class AbstractOrmLiteEntity {
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    protected long _id;

    public long getId() {
        return _id;
    }
}
