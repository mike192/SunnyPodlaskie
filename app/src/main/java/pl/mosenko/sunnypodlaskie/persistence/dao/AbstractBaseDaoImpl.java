package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import pl.mosenko.sunnypodlaskie.persistence.entities.AbstractOrmLiteEntity;

/**
 * Created by syk on 18.05.17.
 */

public abstract class AbstractBaseDaoImpl<DataType extends AbstractOrmLiteEntity, IdType> extends RxBaseDaoImpl<DataType, Long> implements OrmLiteEnityDAO<DataType> {

    protected AbstractBaseDaoImpl(Class<DataType> dataClass) throws SQLException {
        super(dataClass);
    }

    protected AbstractBaseDaoImpl(ConnectionSource connectionSource, Class<DataType> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected AbstractBaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<DataType> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
