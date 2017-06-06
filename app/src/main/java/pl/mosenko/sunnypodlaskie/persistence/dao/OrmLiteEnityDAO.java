package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.dao.Dao;

import pl.mosenko.sunnypodlaskie.persistence.entities.BaseOrmLiteEntity;

/**
 * Created by syk on 17.05.17.
 */

public interface OrmLiteEnityDAO<DataType extends BaseOrmLiteEntity> extends Dao<DataType, Long> {
}
