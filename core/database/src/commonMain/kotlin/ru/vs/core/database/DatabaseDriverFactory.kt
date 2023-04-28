package ru.vs.core.database

import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.db.SqlDriver
import org.kodein.di.DirectDI

interface DatabaseDriverFactory {
    /**
     * Create [SqlDriver] driver for database
     * If database not exist initialize it with given [SqlSchema]
     */
    suspend fun create(schema: SqlSchema): SqlDriver
}

/**
 * Creates platform specific instance of [DatabaseDriverFactory]
 * [DirectDI] access need to access platform specific objects (android context for example)
 */
internal expect fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory
