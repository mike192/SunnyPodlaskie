package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 16.05.17.
 */

public class WeatherDataEntityDAO extends AbstractBaseDaoImpl<WeatherDataEntity, Long> {

    public WeatherDataEntityDAO(Class<WeatherDataEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public WeatherDataEntityDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, WeatherDataEntity.class);
    }

    public WeatherDataEntityDAO(ConnectionSource connectionSource, DatabaseTableConfig<WeatherDataEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
