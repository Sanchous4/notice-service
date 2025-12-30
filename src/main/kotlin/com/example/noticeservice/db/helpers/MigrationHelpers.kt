package com.example.noticeservice.db.helpers

import org.apache.commons.csv.CSVFormat
import java.sql.Connection
import java.sql.SQLException

/**
 * Helper functions for database migrations.
 */
object MigrationHelpers {
    /**
     * Holds SQL query builders used in migration helpers.
     */
    private object QUERIES {
        fun countRows(tableName: String): String = "SELECT COUNT(*) FROM $tableName"
    }

    /**
     * Checks if the given table is empty.
     *
     * @param connection database connection
     * @param tableName table name to check
     * @return `true` if the table has no rows, `false` otherwise
     * @throws SQLException if the query fails
     */
    @Suppress("SqlSourceToSinkFlow")
    @Throws(SQLException::class)
    fun isEmptyTable(
        connection: Connection,
        tableName: String,
    ): Boolean {
        val query = QUERIES.countRows(tableName)

        connection.createStatement().use { stmt ->
            stmt.executeQuery(query).use { rs ->
                rs.next()
                val count = rs.getLong(1)
                return count == 0L
            }
        }
    }

    /**
     * Provides a default [CSVFormat] for migration files.
     *
     * @return a CSV format with headers, trimming, and empty line handling
     */
    fun getDefaultCSVFormat(): CSVFormat =
        CSVFormat.DEFAULT
            .builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setTrim(true)
            .setIgnoreEmptyLines(true)
            .setAllowMissingColumnNames(false)
            .build()
}
