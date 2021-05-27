package pl.mosenko.sunnypodlaskie.persistence.entities

/**
 * Created by syk on 06.06.17.
 */
interface BaseOrmLiteEntity {
    open fun getId(): Long?
}
