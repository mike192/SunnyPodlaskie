package pl.mosenko.sunnypodlaskie.persistence.converters

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
