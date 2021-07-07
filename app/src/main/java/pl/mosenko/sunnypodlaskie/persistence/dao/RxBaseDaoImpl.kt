package pl.mosenko.sunnypodlaskie.persistence.dao

import com.j256.ormlite.dao.*
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus
import com.j256.ormlite.field.FieldType
import com.j256.ormlite.stmt.GenericRowMapper
import com.j256.ormlite.stmt.PreparedDelete
import com.j256.ormlite.stmt.PreparedQuery
import com.j256.ormlite.stmt.PreparedUpdate
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.support.DatabaseConnection
import com.j256.ormlite.support.DatabaseResults
import com.j256.ormlite.table.DatabaseTableConfig
import io.reactivex.Observable
import pl.mosenko.sunnypodlaskie.persistence.entities.BaseOrmLiteEntity
import java.util.concurrent.Callable

/**
 * Created by syk on 17.05.17.
 */
abstract class RxBaseDaoImpl<DataType : BaseOrmLiteEntity, IdType> : BaseDaoImpl<DataType, IdType>, RxDao<DataType, IdType> {
    protected constructor(dataClass: Class<DataType>) : super(dataClass)
    protected constructor(connectionSource: ConnectionSource, dataClass: Class<DataType>) : super(connectionSource, dataClass)
    protected constructor(connectionSource: ConnectionSource, tableConfig: DatabaseTableConfig<DataType>) : super(connectionSource, tableConfig)

    override fun rxQueryForId(id: IdType?): Observable<DataType?> {
        return Observable.fromCallable { queryForId(id) }
    }

    override fun rxQueryForFirst(preparedQuery: PreparedQuery<DataType?>?): Observable<DataType?> {
        return Observable.fromCallable { queryForFirst(preparedQuery) }
    }

    override fun rxQueryForAll(): Observable<MutableList<DataType>> {
        return Observable.fromCallable { queryForAll() }
    }

    override fun rxQueryForEq(fieldName: String?, value: Any?): Observable<MutableList<DataType?>?> {
        return Observable.fromCallable { queryForEq(fieldName, value) }
    }

    override fun rxQueryForMatching(matchObj: DataType?): Observable<MutableList<DataType?>?> {
        return Observable.fromCallable { queryForMatching(matchObj) }
    }

    override fun rxQueryForMatchingArgs(matchObj: DataType?): Observable<MutableList<DataType?>?> {
        return Observable.fromCallable { queryForMatchingArgs(matchObj) }
    }

    override fun rxQueryForFieldValues(fieldValues: MutableMap<String?, Any?>?): Observable<MutableList<DataType?>?> {
        return Observable.fromCallable { queryForFieldValues(fieldValues) }
    }

    override fun rxQueryForFieldValuesArgs(fieldValues: MutableMap<String?, Any?>?): Observable<MutableList<DataType?>?> {
        return Observable.fromCallable { queryForFieldValues(fieldValues) }
    }

    override fun rxQueryForSameId(data: DataType?): Observable<DataType?> {
        return Observable.fromCallable { queryForSameId(data) }
    }

    override fun rxQuery(preparedQuery: PreparedQuery<DataType?>?): Observable<MutableList<DataType?>?> {
        return Observable.fromCallable { query(preparedQuery) }
    }

    override fun rxCreate(data: DataType?): Observable<Int?> {
        return Observable.fromCallable { create(data) }
    }

    override fun rxCreateIfNotExists(data: DataType?): Observable<DataType?> {
        return Observable.fromCallable { createIfNotExists(data) }
    }

    override fun rxCreateOrUpdate(data: DataType?): Observable<CreateOrUpdateStatus?> {
        return Observable.fromCallable { createOrUpdate(data) }
    }

    override fun rxUpdate(data: DataType?): Observable<Int?> {
        return Observable.fromCallable { update(data) }
    }

    override fun rxUpdateId(data: DataType?, newId: IdType?): Observable<Int?> {
        return Observable.fromCallable { updateId(data, newId) }
    }

    override fun rxUpdate(preparedUpdate: PreparedUpdate<DataType?>?): Observable<Int?> {
        return Observable.fromCallable { update(preparedUpdate) }
    }

    override fun rxRefresh(data: DataType?): Observable<Int?> {
        return Observable.fromCallable { refresh(data) }
    }

    override fun rxDelete(data: DataType?): Observable<Int?> {
        return Observable.fromCallable { delete(data) }
    }

    override fun rxDeleteById(id: IdType?): Observable<Int?> {
        return Observable.fromCallable { deleteById(id) }
    }

    override fun rxDelete(datas: MutableCollection<DataType?>?): Observable<Int?> {
        return Observable.fromCallable { delete(datas) }
    }

    override fun rxDeleteIds(ids: MutableCollection<IdType?>?): Observable<Int?> {
        return Observable.fromCallable { deleteIds(ids) }
    }

    override fun rxDelete(preparedDelete: PreparedDelete<DataType?>?): Observable<Int?> {
        return Observable.fromCallable { delete(preparedDelete) }
    }

    override fun rxIterator(): Observable<CloseableIterator<DataType?>?> {
        return Observable.fromCallable { iterator() }
    }

    override fun rxIterator(resultFlags: Int): Observable<CloseableIterator<DataType?>?> {
        return Observable.fromCallable { iterator(resultFlags) }
    }

    override fun rxIterator(preparedQuery: PreparedQuery<DataType?>?): Observable<CloseableIterator<DataType?>?> {
        return Observable.fromCallable { iterator(preparedQuery) }
    }

    override fun rxIterator(preparedQuery: PreparedQuery<DataType?>?, resultFlags: Int): Observable<CloseableIterator<DataType?>?> {
        return Observable.fromCallable { iterator(preparedQuery, resultFlags) }
    }

    override fun rxGetWrappedIterable(): Observable<CloseableWrappedIterable<DataType?>?> {
        return Observable.fromCallable { wrappedIterable }
    }

    override fun rxGetWrappedIterable(preparedQuery: PreparedQuery<DataType?>?): Observable<CloseableWrappedIterable<DataType?>?> {
        return Observable.fromCallable { getWrappedIterable(preparedQuery) }
    }

    override fun rxQueryRaw(query: String?, vararg arguments: String?): Observable<GenericRawResults<Array<String?>?>?> {
        return Observable.fromCallable { queryRaw(query, *arguments) }
    }

    override fun <UO> rxQueryRaw(query: String?, mapper: RawRowMapper<UO?>?, vararg arguments: String?): Observable<GenericRawResults<UO?>?> {
        return Observable.fromCallable { queryRaw(query, mapper, *arguments) }
    }

    override fun <UO> rxQueryRaw(query: String?, columnTypes: Array<com.j256.ormlite.field.DataType?>?, mapper: RawRowObjectMapper<UO?>?,
                                 vararg arguments: String?): Observable<GenericRawResults<UO?>?>? {
        return Observable.fromCallable { queryRaw(query, columnTypes, mapper, *arguments) }

    }

    override fun rxQueryRaw(query: String?, columnTypes: Array<com.j256.ormlite.field.DataType?>?, vararg arguments: String?): Observable<GenericRawResults<Array<Any?>?>?> {
        return Observable.fromCallable { queryRaw(query, columnTypes, *arguments) }
    }

    override fun rxQueryRawValue(query: String?, vararg arguments: String?): Observable<Long?> {
        return Observable.fromCallable { queryRawValue(query, *arguments) }
    }

    override fun rxExecuteRaw(statement: String?, vararg arguments: String?): Observable<Int?> {
        return Observable.fromCallable { executeRaw(statement, *arguments) }
    }

    override fun rxExecuteRawNoArgs(statement: String?): Observable<Int?> {
        return Observable.fromCallable { executeRawNoArgs(statement) }
    }

    override fun rxUpdateRaw(statement: String?, vararg arguments: String?): Observable<Int?> {
        return Observable.fromCallable { updateRaw(statement, *arguments) }
    }

    @Throws(Exception::class)
    override fun <CT> rxCallBatchTasks(callable: Callable<CT?>?): Observable<CT?> {
        return Observable.fromCallable { callBatchTasks(callable) }
    }

    override fun rxObjectsEqual(data1: DataType?, data2: DataType?): Observable<Boolean?> {
        return Observable.fromCallable { objectsEqual(data1, data2) }
    }

    override fun rxExtractId(data: DataType?): Observable<IdType?> {
        return Observable.fromCallable { extractId(data) }
    }

    override fun rxGetDataClass(): Observable<Class<DataType?>?> {
        return Observable.fromCallable { getDataClass() }
    }

    override fun rxFindForeignFieldType(clazz: Class<*>?): Observable<FieldType?> {
        return Observable.fromCallable { findForeignFieldType(clazz) }
    }

    override fun rxIsUpdatable(): Observable<Boolean?> {
        return Observable.fromCallable { isUpdatable }
    }

    override fun rxIsTableExists(): Observable<Boolean?> {
        return Observable.fromCallable { isTableExists }
    }

    override fun rxCountOf(): Observable<Long?> {
        return Observable.fromCallable { countOf() }
    }

    override fun rxCountOf(preparedQuery: PreparedQuery<DataType?>?): Observable<Long?> {
        return Observable.fromCallable { countOf(preparedQuery) }
    }

    override fun <FT> rxGetEmptyForeignCollection(fieldName: String?): Observable<ForeignCollection<FT?>?> {
        return Observable.fromCallable { getEmptyForeignCollection(fieldName) }
    }

    override fun rxGetObjectCache(): Observable<ObjectCache?> {
        return Observable.fromCallable { objectCache }
    }

    override fun rxMapSelectStarRow(results: DatabaseResults?): Observable<DataType?> {
        return Observable.fromCallable { mapSelectStarRow(results) }
    }

    override fun rxGetSelectStarRowMapper(): Observable<GenericRowMapper<DataType?>?> {
        return Observable.fromCallable { selectStarRowMapper }
    }

    override fun rxGetRawRowMapper(): Observable<RawRowMapper<DataType?>?> {
        return Observable.fromCallable { rawRowMapper }
    }

    override fun rxIdExists(id: IdType?): Observable<Boolean?> {
        return Observable.fromCallable { idExists(id) }
    }

    override fun rxStartThreadConnection(): Observable<DatabaseConnection?> {
        return Observable.fromCallable { startThreadConnection() }
    }

    override fun rxIsAutoCommit(connection: DatabaseConnection?): Observable<Boolean?> {
        return Observable.fromCallable { isAutoCommit(connection) }
    }

    override fun rxGetConnectionSource(): Observable<ConnectionSource?> {
        return Observable.fromCallable { getConnectionSource() }
    }
}
