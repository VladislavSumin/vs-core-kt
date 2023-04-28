package ru.vs.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.kodein.di.DirectDI

private class JvmDatabaseDriverFactory : DatabaseDriverFactory {
    override suspend fun create(schema: SqlSchema): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        schema.create(driver)
        return driver
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return JvmDatabaseDriverFactory()
}
