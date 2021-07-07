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
interface RxDao<T, ID> : Dao<T, ID> {
    fun rxQueryForId(id: ID?): Observable<T?>?
    fun rxQueryForFirst(preparedQuery: PreparedQuery<T?>?): Observable<T?>?
    fun rxQueryForAll(): Observable<MutableList<T>>
    fun rxQueryForEq(fieldName: String?, value: Any?): Observable<MutableList<T?>?>?
    fun rxQueryForMatching(matchObj: T?): Observable<MutableList<T?>?>?
    fun rxQueryForMatchingArgs(matchObj: T?): Observable<MutableList<T?>?>?
    fun rxQueryForFieldValues(fieldValues: MutableMap<String?, Any?>?): Observable<MutableList<T?>?>?
    fun rxQueryForFieldValuesArgs(fieldValues: MutableMap<String?, Any?>?): Observable<MutableList<T?>?>?
    fun rxQueryForSameId(data: T?): Observable<T?>?
    fun rxQuery(preparedQuery: PreparedQuery<T?>?): Observable<MutableList<T?>?>?
    fun rxCreate(data: T?): Observable<Int?>?
    fun rxCreateIfNotExists(data: T?): Observable<T?>?
    fun rxCreateOrUpdate(data: T?): Observable<CreateOrUpdateStatus?>?
    fun rxUpdate(data: T?): Observable<Int?>?
    fun rxUpdateId(data: T?, newId: ID?): Observable<Int?>?
    fun rxUpdate(preparedUpdate: PreparedUpdate<T?>?): Observable<Int?>?
    fun rxRefresh(data: T?): Observable<Int?>?
    fun rxDelete(data: T?): Observable<Int?>?
    fun rxDeleteById(id: ID?): Observable<Int?>?
    fun rxDelete(datas: MutableCollection<T?>?): Observable<Int?>?
    fun rxDeleteIds(ids: MutableCollection<ID?>?): Observable<Int?>?
    fun rxDelete(preparedDelete: PreparedDelete<T?>?): Observable<Int?>?
    fun rxIterator(): Observable<CloseableIterator<T?>?>?
    fun rxIterator(resultFlags: Int): Observable<CloseableIterator<T?>?>?
    fun rxIterator(preparedQuery: PreparedQuery<T?>?): Observable<CloseableIterator<T?>?>?
    fun rxIterator(preparedQuery: PreparedQuery<T?>?, resultFlags: Int): Observable<CloseableIterator<T?>?>?
    fun rxGetWrappedIterable(): Observable<CloseableWrappedIterable<T?>?>?
    fun rxGetWrappedIterable(preparedQuery: PreparedQuery<T?>?): Observable<CloseableWrappedIterable<T?>?>?
    fun rxQueryRaw(query: String?, vararg arguments: String?): Observable<GenericRawResults<Array<String?>?>?>?
    fun <UO> rxQueryRaw(query: String?, mapper: RawRowMapper<UO?>?, vararg arguments: String?): Observable<GenericRawResults<UO?>?>?
    fun <UO> rxQueryRaw(query: String?, columnTypes: Array<DataType?>?, mapper: RawRowObjectMapper<UO?>?,
                        vararg arguments: String?): Observable<GenericRawResults<UO?>?>?

    fun rxQueryRaw(query: String?, columnTypes: Array<DataType?>?, vararg arguments: String?): Observable<GenericRawResults<Array<Any?>?>?>?
    fun rxQueryRawValue(query: String?, vararg arguments: String?): Observable<Long?>?
    fun rxExecuteRaw(statement: String?, vararg arguments: String?): Observable<Int?>?
    fun rxExecuteRawNoArgs(statement: String?): Observable<Int?>?
    fun rxUpdateRaw(statement: String?, vararg arguments: String?): Observable<Int?>?

    @Throws(Exception::class)
    fun <CT> rxCallBatchTasks(callable: Callable<CT?>?): Observable<CT?>?
    fun rxObjectsEqual(data1: T?, data2: T?): Observable<Boolean?>?
    fun rxExtractId(data: T?): Observable<ID?>?
    fun rxGetDataClass(): Observable<Class<T?>?>?
    fun rxFindForeignFieldType(clazz: Class<*>?): Observable<FieldType?>?
    fun rxIsUpdatable(): Observable<Boolean?>?
    fun rxIsTableExists(): Observable<Boolean?>?
    fun rxCountOf(): Observable<Long?>?
    fun rxCountOf(preparedQuery: PreparedQuery<T?>?): Observable<Long?>?
    fun <FT> rxGetEmptyForeignCollection(fieldName: String?): Observable<ForeignCollection<FT?>?>?
    fun rxGetObjectCache(): Observable<ObjectCache?>?
    fun rxMapSelectStarRow(results: DatabaseResults?): Observable<T?>?
    fun rxGetSelectStarRowMapper(): Observable<GenericRowMapper<T?>?>?
    fun rxGetRawRowMapper(): Observable<RawRowMapper<T?>?>?
    fun rxIdExists(id: ID?): Observable<Boolean?>?
    fun rxStartThreadConnection(): Observable<DatabaseConnection?>?
    fun rxIsAutoCommit(connection: DatabaseConnection?): Observable<Boolean?>?
    fun rxGetConnectionSource(): Observable<ConnectionSource?>?
}
