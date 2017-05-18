package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 16.05.17.
 */

public class DAOWeatherDataEntity extends AbstractBaseDaoImpl<WeatherDataEntity, Long> {
    protected DAOWeatherDataEntity(Class<WeatherDataEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    protected DAOWeatherDataEntity(ConnectionSource connectionSource, Class<WeatherDataEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected DAOWeatherDataEntity(ConnectionSource connectionSource, DatabaseTableConfig<WeatherDataEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
