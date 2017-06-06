package pl.mosenko.sunnypodlaskie.persistence.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import pl.mosenko.sunnypodlaskie.persistence.entities.BaseOrmLiteEntity;

/**
 * Created by syk on 17.05.17.
 */

public abstract class RxBaseDaoImpl<DataType extends BaseOrmLiteEntity, IdType> extends BaseDaoImpl<DataType, IdType> implements RxDao<DataType, IdType> {
    protected RxBaseDaoImpl(Class<DataType> dataClass) throws SQLException {
        super(dataClass);
    }

    protected RxBaseDaoImpl(ConnectionSource connectionSource, Class<DataType> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected RxBaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<DataType> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public Observable<DataType> rxQueryForId(final IdType id) {
        return Observable.fromCallable(() -> queryForId(id));
    }

    @Override
    public Observable<DataType> rxQueryForFirst(final PreparedQuery<DataType> preparedQuery) {
        return Observable.fromCallable(() -> queryForFirst(preparedQuery));
    }

    @Override
    public Observable<List<DataType>> rxQueryForAll() {
        return Observable.fromCallable(() -> queryForAll());
    }

    @Override
    public Observable<List<DataType>> rxQueryForEq(final String fieldName, final Object value) {
        return Observable.fromCallable(() -> queryForEq(fieldName, value));
    }

    @Override
    public Observable<List<DataType>> rxQueryForMatching(final DataType matchObj) {
        return Observable.fromCallable(() -> queryForMatching(matchObj));
    }

    @Override
    public Observable<List<DataType>> rxQueryForMatchingArgs(final DataType matchObj) {
        return Observable.fromCallable(() -> queryForMatchingArgs(matchObj));
    }

    @Override
    public Observable<List<DataType>> rxQueryForFieldValues(final Map<String, Object> fieldValues) {
        return Observable.fromCallable(() -> queryForFieldValues(fieldValues));
    }

    @Override
    public Observable<List<DataType>> rxQueryForFieldValuesArgs(final Map<String, Object> fieldValues) {
        return Observable.fromCallable(() -> queryForFieldValues(fieldValues));
    }

    @Override
    public Observable<DataType> rxQueryForSameId(final DataType data) {
        return Observable.fromCallable(() -> queryForSameId(data));
    }

    @Override
    public Observable<List<DataType>> rxQuery(final PreparedQuery<DataType> preparedQuery) {
        return Observable.fromCallable(() -> query(preparedQuery));
    }

    @Override
    public Observable<Integer> rxCreate(final DataType data) {
        return Observable.fromCallable(() -> create(data));
    }

    @Override
    public Observable<DataType> rxCreateIfNotExists(final DataType data) {
        return Observable.fromCallable(() -> createIfNotExists(data));
    }

    @Override
    public Observable<CreateOrUpdateStatus> rxCreateOrUpdate(final DataType data) {
        return Observable.fromCallable(() -> createOrUpdate(data));
    }

    @Override
    public Observable<Integer> rxUpdate(final DataType data) {
        return Observable.fromCallable(() -> update(data));
    }

    @Override
    public Observable<Integer> rxUpdateId(final DataType data, final IdType newId) {
        return Observable.fromCallable(() -> updateId(data, newId));
    }

    @Override
    public Observable<Integer> rxUpdate(final PreparedUpdate<DataType> preparedUpdate) {
        return Observable.fromCallable(() -> update(preparedUpdate));
    }

    @Override
    public Observable<Integer> rxRefresh(final DataType data) {
        return Observable.fromCallable(() -> refresh(data));
    }

    @Override
    public Observable<Integer> rxDelete(final DataType data) {
        return Observable.fromCallable(() -> delete(data));
    }

    @Override
    public Observable<Integer> rxDeleteById(final IdType id) {
        return Observable.fromCallable(() -> deleteById(id));
    }

    @Override
    public Observable<Integer> rxDelete(final Collection<DataType> datas) {
        return Observable.fromCallable(() -> delete(datas));
    }

    @Override
    public Observable<Integer> rxDeleteIds(final Collection<IdType> ids) {
        return Observable.fromCallable(() -> deleteIds(ids));
    }

    @Override
    public Observable<Integer> rxDelete(final PreparedDelete<DataType> preparedDelete) {
        return Observable.fromCallable(() -> delete(preparedDelete));
    }

    @Override
    public Observable<CloseableIterator<DataType>> rxIterator() {
        return Observable.fromCallable(() -> iterator());
    }

    @Override
    public Observable<CloseableIterator<DataType>> rxIterator(final int resultFlags) {
        return Observable.fromCallable(() -> iterator(resultFlags));
    }

    @Override
    public Observable<CloseableIterator<DataType>> rxIterator(final PreparedQuery<DataType> preparedQuery) {
        return Observable.fromCallable(() -> iterator(preparedQuery));
    }

    @Override
    public Observable<CloseableIterator<DataType>> rxIterator(final PreparedQuery<DataType> preparedQuery, final int resultFlags) {
        return Observable.fromCallable(() -> iterator(preparedQuery, resultFlags));
    }

    @Override
    public Observable<CloseableWrappedIterable<DataType>> rxGetWrappedIterable() {
        return Observable.fromCallable(() -> getWrappedIterable());
    }

    @Override
    public Observable<CloseableWrappedIterable<DataType>> rxGetWrappedIterable(final PreparedQuery<DataType> preparedQuery) {
        return Observable.fromCallable(() -> getWrappedIterable(preparedQuery));
    }

    @Override
    public Observable<GenericRawResults<String[]>> rxQueryRaw(final String query, final String... arguments) {
        return Observable.fromCallable(() -> queryRaw(query, arguments));
    }

    @Override
    public <UO> Observable<GenericRawResults<UO>> rxQueryRaw(final String query, final RawRowMapper<UO> mapper, final String... arguments) {
        return Observable.fromCallable(() -> queryRaw(query, mapper, arguments));
    }

    @Override
    public <UO> Observable<GenericRawResults<UO>> rxQueryRaw(final String query, final com.j256.ormlite.field.DataType[] columnTypes, final RawRowObjectMapper<UO> mapper,
                                                             final String... arguments) {
        return Observable.fromCallable(() -> queryRaw(query, columnTypes, mapper, arguments));
    }

    @Override
    public Observable<GenericRawResults<Object[]>> rxQueryRaw(final String query, final com.j256.ormlite.field.DataType[] columnTypes, final String... arguments) {
        return Observable.fromCallable(() -> queryRaw(query, columnTypes, arguments));
    }

    @Override
    public Observable<Long> rxQueryRawValue(final String query, final String... arguments) {
        return Observable.fromCallable(() -> queryRawValue(query, arguments));
    }

    @Override
    public Observable<Integer> rxExecuteRaw(final String statement, final String... arguments) {
        return Observable.fromCallable(() -> executeRaw(statement, arguments));
    }

    @Override
    public Observable<Integer> rxExecuteRawNoArgs(final String statement) {
        return Observable.fromCallable(() -> executeRawNoArgs(statement));
    }

    @Override
    public Observable<Integer> rxUpdateRaw(final String statement, final String... arguments) {
        return Observable.fromCallable(() -> updateRaw(statement, arguments));
    }

    @Override
    public <CT> Observable<CT> rxCallBatchTasks(final Callable<CT> callable) throws Exception {
        return Observable.fromCallable(() -> callBatchTasks(callable));
    }

    @Override
    public Observable<Boolean> rxObjectsEqual(final DataType data1, final DataType data2) {
        return Observable.fromCallable(() -> objectsEqual(data1, data2));
    }

    @Override
    public Observable<IdType> rxExtractId(final DataType data) {
        return Observable.fromCallable(() -> extractId(data));
    }

    @Override
    public Observable<Class<DataType>> rxGetDataClass() {
        return Observable.fromCallable(() -> getDataClass());
    }

    @Override
    public Observable<FieldType> rxFindForeignFieldType(final Class<?> clazz) {
        return Observable.fromCallable(() -> findForeignFieldType(clazz));
    }

    @Override
    public Observable<Boolean> rxIsUpdatable() {
        return Observable.fromCallable(() -> isUpdatable());
    }

    @Override
    public Observable<Boolean> rxIsTableExists() {
        return Observable.fromCallable(() -> isTableExists());
    }

    @Override
    public Observable<Long> rxCountOf() {
        return Observable.fromCallable(() -> countOf());
    }

    @Override
    public Observable<Long> rxCountOf(final PreparedQuery<DataType> preparedQuery) {
        return Observable.fromCallable(() -> countOf(preparedQuery));
    }

    @Override
    public <FT> Observable<ForeignCollection<FT>> rxGetEmptyForeignCollection(final String fieldName) {
        return Observable.fromCallable(() -> getEmptyForeignCollection(fieldName));
    }

    @Override
    public Observable<ObjectCache> rxGetObjectCache() {
        return Observable.fromCallable(() -> getObjectCache());
    }

    @Override
    public Observable<DataType> rxMapSelectStarRow(final DatabaseResults results) {
        return Observable.fromCallable(() -> mapSelectStarRow(results));
    }

    @Override
    public Observable<GenericRowMapper<DataType>> rxGetSelectStarRowMapper() {
        return Observable.fromCallable(() -> getSelectStarRowMapper());
    }

    @Override
    public Observable<RawRowMapper<DataType>> rxGetRawRowMapper() {
        return Observable.fromCallable(() -> getRawRowMapper());
    }

    @Override
    public Observable<Boolean> rxIdExists(final IdType id) {
        return Observable.fromCallable(() -> idExists(id));
    }

    @Override
    public Observable<DatabaseConnection> rxStartThreadConnection() {
        return Observable.fromCallable(() -> startThreadConnection());
    }

    @Override
    public Observable<Boolean> rxIsAutoCommit(final DatabaseConnection connection) {
        return Observable.fromCallable(() -> isAutoCommit(connection));
    }

    @Override
    public Observable<ConnectionSource> rxGetConnectionSource() {
        return Observable.fromCallable(() -> getConnectionSource());
    }
}