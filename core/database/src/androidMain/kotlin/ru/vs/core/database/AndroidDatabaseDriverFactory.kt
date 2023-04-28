package ru.vs.core.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.kodein.di.DirectDI
import org.kodein.di.instance

private class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override suspend fun create(schema: SqlSchema): SqlDriver {
        return AndroidSqliteDriver(schema, context, "database.db")
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseDriverFactory {
    return AndroidDatabaseDriverFactory(instance())
}