package ru.vs.core.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.kodein.di.DirectDI

private class NativeDatabaseDriverFactory : DatabaseDriverFactory {
    override suspend fun create(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        return NativeSqliteDriver(schema, "database.db")
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return NativeDatabaseDriverFactory()
}