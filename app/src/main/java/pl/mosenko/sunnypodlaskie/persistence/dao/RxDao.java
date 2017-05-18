package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by syk on 17.05.17.
 */

public interface RxDao<T, ID> extends Dao<T, ID> {

    Observable<T> rxQueryForId(final ID id);

    Observable<T> rxQueryForFirst(final PreparedQuery<T> preparedQuery);

    Observable<List<T>> rxQueryForAll();

    Observable<List<T>> rxQueryForEq(final String fieldName, final Object value);

    Observable<List<T>> rxQueryForMatching(final T matchObj);

    Observable<List<T>> rxQueryForMatchingArgs(final T matchObj);

    Observable<List<T>> rxQueryForFieldValues(final Map<String, Object> fieldValues);

    Observable<List<T>> rxQueryForFieldValuesArgs(final Map<String, Object> fieldValues);

    Observable<T> rxQueryForSameId(final T data);

    Observable<List<T>> rxQuery(final PreparedQuery<T> preparedQuery);

    Observable<Integer> rxCreate(final T data);

    Observable<T> rxCreateIfNotExists(final T data);

    Observable<CreateOrUpdateStatus> rxCreateOrUpdate(final T data);

    Observable<Integer> rxUpdate(final T data);

    Observable<Integer> rxUpdateId(final T data, final ID newId);

    Observable<Integer> rxUpdate(final PreparedUpdate<T> preparedUpdate);

    Observable<Integer> rxRefresh(final T data);

    Observable<Integer> rxDelete(final T data);

    Observable<Integer> rxDeleteById(final ID id);

    Observable<Integer> rxDelete(final Collection<T> datas);

    Observable<Integer> rxDeleteIds(final Collection<ID> ids);

    Observable<Integer> rxDelete(final PreparedDelete<T> preparedDelete);

    Observable<CloseableIterator<T>> rxIterator();

    Observable<CloseableIterator<T>> rxIterator(final int resultFlags);

    Observable<CloseableIterator<T>> rxIterator(final PreparedQuery<T> preparedQuery);

    Observable<CloseableIterator<T>> rxIterator(final PreparedQuery<T> preparedQuery, final int resultFlags);

    Observable<CloseableWrappedIterable<T>> rxGetWrappedIterable();

    Observable<CloseableWrappedIterable<T>> rxGetWrappedIterable(final PreparedQuery<T> preparedQuery);

    Observable<GenericRawResults<String[]>> rxQueryRaw(final String query, final String... arguments);

    <UO> Observable<GenericRawResults<UO>> rxQueryRaw(final String query, final RawRowMapper<UO> mapper, final String... arguments);

    <UO> Observable<GenericRawResults<UO>> rxQueryRaw(final String query, final DataType[] columnTypes, final RawRowObjectMapper<UO> mapper,
                                                      final String... arguments);

    Observable<GenericRawResults<Object[]>> rxQueryRaw(final String query, final DataType[] columnTypes, final String... arguments);

    Observable<Long> rxQueryRawValue(final String query, final String... arguments);

    Observable<Integer> rxExecuteRaw(final String statement, final String... arguments);

    Observable<Integer> rxExecuteRawNoArgs(final String statement);

    Observable<Integer> rxUpdateRaw(final String statement, final String... arguments);

    <CT> Observable<CT> rxCallBatchTasks(final Callable<CT> callable) throws Exception;

    Observable<Boolean> rxObjectsEqual(final T data1, final T data2);

    Observable<ID> rxExtractId(final T data);

    Observable<Class<T>> rxGetDataClass();

    Observable<FieldType> rxFindForeignFieldType(final Class<?> clazz);

    Observable<Boolean> rxIsUpdatable();

    Observable<Boolean> rxIsTableExists();

    Observable<Long> rxCountOf();

    Observable<Long> rxCountOf(final PreparedQuery<T> preparedQuery);

    <FT> Observable<ForeignCollection<FT>> rxGetEmptyForeignCollection(final String fieldName);

    Observable<ObjectCache> rxGetObjectCache();

    Observable<T> rxMapSelectStarRow(final DatabaseResults results);

    Observable<GenericRowMapper<T>> rxGetSelectStarRowMapper();

    Observable<RawRowMapper<T>> rxGetRawRowMapper();

    Observable<Boolean> rxIdExists(final ID id);

    Observable<DatabaseConnection> rxStartThreadConnection();

    Observable<Boolean> rxIsAutoCommit(final DatabaseConnection connection);

    Observable<ConnectionSource> rxGetConnectionSource();
}
