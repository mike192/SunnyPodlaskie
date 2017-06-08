package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 06.06.17.
 */

public class WeatherConditionEntityDAO extends AbstractBaseDaoImpl<WeatherConditionEntity, Long> {

    public WeatherConditionEntityDAO(Class<WeatherConditionEntity> dataClass) throws SQLException {
        super(dataClass);
    }
    public WeatherConditionEntityDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, WeatherConditionEntity.class);
    }

    public WeatherConditionEntityDAO(ConnectionSource connectionSource, DatabaseTableConfig<WeatherConditionEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}