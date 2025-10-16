package com.example.noticeservice.db.migration

import com.example.noticeservice.db.helpers.MigrationHelpers
import mu.KotlinLogging
import org.apache.commons.csv.CSVParser
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import kotlin.use

/**
 * Flyway migration to fill the `notice` table with mock data.
 *
 * Loads data from a CSV file and upserts it into the database.
 * Skips execution if the table is not empty.
 */
@Suppress("unused", "ClassName")
class V2__Fill_notice_table : BaseJavaMigration() {

    /**
     * Logger instance for this migration.
     */
    private val logger = KotlinLogging.logger {}

    /**
     * Constants used by this migration.
     */
    private companion object {
        /**
         * Number of rows to insert per batch.
         */
        private const val BATCH_SIZE = 500

        /**
         * Target table name for this migration.
         */
        private const val TABLE_NAME = "notice"
    }

    /**
     * SQL statements used in this migration.
     */
    private object SQL_QUERIES {
        /**
         * Upsert statement for the `notice` table.
         *
         * Inserts new records or updates existing ones based on the `title` column.
         */
        // language=SQL
        const val INSERT_NOTICE = """
            INSERT INTO notice (title, content)
            VALUES (?, ?)
        """
    }

    /**
     * Executes the migration:
     * - Skips if the table is not empty
     * - Reads data from CSV
     * - Performs batch upserts
     *
     * @param context Contains a connector and a config to the database
     */
    @Suppress("TooGenericExceptionCaught")
    override fun migrate(context: Context) {
        val connection = context.connection
        connection.autoCommit = false

        if (!MigrationHelpers.isEmptyTable(connection, TABLE_NAME)) {
            logger.info { "Notice table is not empty, skipping migration." }
            return
        }

        try {
            insertCSVDataIntoTable(connection)
            connection.commit()
        } catch (e: SQLException) {
            logger.error(e) { "Failed to migrate $TABLE_NAME table. SQL Exception arose:" }
            connection.rollback()
            throw e
        } catch (e: Exception) {
            logger.error(e) { "Failed to migrate $TABLE_NAME. Unknown Exception arose:" }
            connection.rollback()
        }
    }

    /**
     * Reads CSV file and enriches transaction with the data from the file.
     *
     * @param connection Connector of the database
     */
    private fun insertCSVDataIntoTable(connection: Connection) {
        BufferedReader(sourceReader()).use { reader ->
            val format = MigrationHelpers.getDefaultCSVFormat()
            CSVParser(reader, format).use { parser ->
                connection.prepareStatement(SQL_QUERIES.INSERT_NOTICE).use { ps ->
                    addDataToTransaction(parser, ps)
                }
            }
        }
    }

    /**
     * Enriches transaction with the data from the CSV file.
     *
     * @param parser CSV parser with source records
     * @param ps prepared statement for upserts
     */
    private fun addDataToTransaction(parser: CSVParser, ps: PreparedStatement) {
        var count = 0

        for (rec in parser) {
            val title = rec.get("title")?.trim().orEmpty()
            val content = rec.get("content")?.trim().orEmpty()

            if (title.isBlank()) continue  // skip bad row

            ps.setString(1, title)
            ps.setString(2, content)
            ps.addBatch()
            count++

            if (count % BATCH_SIZE == 0) {
                ps.executeBatch()
            }
        }

        ps.executeBatch()
    }

    /**
     * Opens the CSV file as an [InputStreamReader].
     *
     * @throws IllegalStateException if the file is not found.
     */
    private fun sourceReader(): InputStreamReader {
        val resourcePath = "db/data/notice.csv"
        val inStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            ?: error("CSV not found $resourcePath on classpath.")
        return InputStreamReader(inStream, StandardCharsets.UTF_8)
    }
}
