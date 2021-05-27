package pl.mosenko.sunnypodlaskie.persistence.dao

import com.j256.ormlite.dao.*
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.FieldType
import com.j256.ormlite.stmt.GenericRowMapper
import com.j256.ormlite.stmt.PreparedDelete
import com.j256.ormlite.stmt.PreparedQuery
import com.j256.ormlite.stmt.PreparedUpdate
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.support.DatabaseConnection
import com.j256.ormlite.support.DatabaseResults
import io.reactivex.Observable
import java.util.concurrent.Callable

/**
 * Created by syk on 17.05.17.
 */
interface RxDao<T, ID> : Dao<T?, ID?> {
    open fun rxQueryForId(id: ID?): Observable<T?>?
    open fun rxQueryForFirst(preparedQuery: PreparedQuery<T?>?): Observable<T?>?
    open fun rxQueryForAll(): Observable<MutableList<T?>?>?
    open fun rxQueryForEq(fieldName: String?, value: Any?): Observable<MutableList<T?>?>?
    open fun rxQueryForMatching(matchObj: T?): Observable<MutableList<T?>?>?
    open fun rxQueryForMatchingArgs(matchObj: T?): Observable<MutableList<T?>?>?
    open fun rxQueryForFieldValues(fieldValues: MutableMap<String?, Any?>?): Observable<MutableList<T?>?>?
    open fun rxQueryForFieldValuesArgs(fieldValues: MutableMap<String?, Any?>?): Observable<MutableList<T?>?>?
    open fun rxQueryForSameId(data: T?): Observable<T?>?
    open fun rxQuery(preparedQuery: PreparedQuery<T?>?): Observable<MutableList<T?>?>?
    open fun rxCreate(data: T?): Observable<Int?>?
    open fun rxCreateIfNotExists(data: T?): Observable<T?>?
    open fun rxCreateOrUpdate(data: T?): Observable<CreateOrUpdateStatus?>?
    open fun rxUpdate(data: T?): Observable<Int?>?
    open fun rxUpdateId(data: T?, newId: ID?): Observable<Int?>?
    open fun rxUpdate(preparedUpdate: PreparedUpdate<T?>?): Observable<Int?>?
    open fun rxRefresh(data: T?): Observable<Int?>?
    open fun rxDelete(data: T?): Observable<Int?>?
    open fun rxDeleteById(id: ID?): Observable<Int?>?
    open fun rxDelete(datas: MutableCollection<T?>?): Observable<Int?>?
    open fun rxDeleteIds(ids: MutableCollection<ID?>?): Observable<Int?>?
    open fun rxDelete(preparedDelete: PreparedDelete<T?>?): Observable<Int?>?
    open fun rxIterator(): Observable<CloseableIterator<T?>?>?
    open fun rxIterator(resultFlags: Int): Observable<CloseableIterator<T?>?>?
    open fun rxIterator(preparedQuery: PreparedQuery<T?>?): Observable<CloseableIterator<T?>?>?
    open fun rxIterator(preparedQuery: PreparedQuery<T?>?, resultFlags: Int): Observable<CloseableIterator<T?>?>?
    open fun rxGetWrappedIterable(): Observable<CloseableWrappedIterable<T?>?>?
    open fun rxGetWrappedIterable(preparedQuery: PreparedQuery<T?>?): Observable<CloseableWrappedIterable<T?>?>?
    open fun rxQueryRaw(query: String?, vararg arguments: String?): Observable<GenericRawResults<Array<String?>?>?>?
    open fun <UO> rxQueryRaw(query: String?, mapper: RawRowMapper<UO?>?, vararg arguments: String?): Observable<GenericRawResults<UO?>?>?
    open fun <UO> rxQueryRaw(query: String?, columnTypes: Array<DataType?>?, mapper: RawRowObjectMapper<UO?>?,
                             vararg arguments: String?): Observable<GenericRawResults<UO?>?>?

    open fun rxQueryRaw(query: String?, columnTypes: Array<DataType?>?, vararg arguments: String?): Observable<GenericRawResults<Array<Any?>?>?>?
    open fun rxQueryRawValue(query: String?, vararg arguments: String?): Observable<Long?>?
    open fun rxExecuteRaw(statement: String?, vararg arguments: String?): Observable<Int?>?
    open fun rxExecuteRawNoArgs(statement: String?): Observable<Int?>?
    open fun rxUpdateRaw(statement: String?, vararg arguments: String?): Observable<Int?>?
    @Throws(Exception::class)
    open fun <CT> rxCallBatchTasks(callable: Callable<CT?>?): Observable<CT?>?
    open fun rxObjectsEqual(data1: T?, data2: T?): Observable<Boolean?>?
    open fun rxExtractId(data: T?): Observable<ID?>?
    open fun rxGetDataClass(): Observable<Class<T?>?>?
    open fun rxFindForeignFieldType(clazz: Class<*>?): Observable<FieldType?>?
    open fun rxIsUpdatable(): Observable<Boolean?>?
    open fun rxIsTableExists(): Observable<Boolean?>?
    open fun rxCountOf(): Observable<Long?>?
    open fun rxCountOf(preparedQuery: PreparedQuery<T?>?): Observable<Long?>?
    open fun <FT> rxGetEmptyForeignCollection(fieldName: String?): Observable<ForeignCollection<FT?>?>?
    open fun rxGetObjectCache(): Observable<ObjectCache?>?
    open fun rxMapSelectStarRow(results: DatabaseResults?): Observable<T?>?
    open fun rxGetSelectStarRowMapper(): Observable<GenericRowMapper<T?>?>?
    open fun rxGetRawRowMapper(): Observable<RawRowMapper<T?>?>?
    open fun rxIdExists(id: ID?): Observable<Boolean?>?
    open fun rxStartThreadConnection(): Observable<DatabaseConnection?>?
    open fun rxIsAutoCommit(connection: DatabaseConnection?): Observable<Boolean?>?
    open fun rxGetConnectionSource(): Observable<ConnectionSource?>?
}
