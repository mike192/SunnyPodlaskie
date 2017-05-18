package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.dao.Dao;
import pl.mosenko.sunnypodlaskie.persistence.entities.AbstractOrmLiteEntity;

/**
 * Created by syk on 17.05.17.
 */

public interface OrmLiteEnityDAO<DataType extends AbstractOrmLiteEntity> extends Dao<DataType, Long> {
}
