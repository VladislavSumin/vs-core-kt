package ru.vs.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import kotlinx.coroutines.await
import org.kodein.di.DirectDI

private class JsDatabaseDriverFactory : DatabaseDriverFactory {
    override suspend fun create(schema: SqlSchema): SqlDriver {
        return initSqlDriver(schema).await()
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return JsDatabaseDriverFactory()
}