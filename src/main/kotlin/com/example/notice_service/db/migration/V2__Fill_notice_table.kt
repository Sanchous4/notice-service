package com.example.notice_service.db.migration

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.sql.Connection
import java.sql.SQLException

@Suppress("unused", "ClassName")
class V2__Fill_notice_table : BaseJavaMigration() {

    override fun migrate(context: Context) {
        val conn = context.connection

        try {
            ensureUniqueConstraint(conn)
            BufferedReader(sourceReader()).use { reader ->
                val format = CSVFormat.DEFAULT.builder()
                    .setHeader()                 // use first row as header
                    .setSkipHeaderRecord(true)
                    .setTrim(true)
                    .setIgnoreEmptyLines(true)
                    .setAllowMissingColumnNames(false)
                    .build()

                CSVParser(reader, format).use { parser ->
                    val upsertSql = """
                        INSERT INTO notice (title, content)
                        VALUES (?, ?)
                        ON CONFLICT (title) DO UPDATE
                        SET content = EXCLUDED.content
                    """.trimIndent()

                    conn.prepareStatement(upsertSql).use { ps ->
                        var count = 0
                        val batchSize = 500

                        for (rec in parser) {
                            val title = rec.get("title")?.trim().orEmpty()
                            val content = rec.get("content")?.trim().orEmpty()

                            if (title.isBlank()) continue  // skip bad row

                            ps.setString(1, title)
                            ps.setString(2, content)
                            ps.addBatch()
                            count++

                            if (count % batchSize == 0) {
                                ps.executeBatch()
                            }
                        }
                        ps.executeBatch()
                    }
                }
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    private fun ensureUniqueConstraint(conn: Connection) {
        val ddl = """
            DO $$
            BEGIN
              IF NOT EXISTS (
                SELECT 1
                FROM   information_schema.table_constraints
                WHERE  table_name = 'notice'
                  AND  constraint_type = 'UNIQUE'
                  AND  constraint_name = 'uq_notice_title'
              ) THEN
                ALTER TABLE notice ADD CONSTRAINT uq_notice_title UNIQUE (title);
              END IF;
            END $$;
        """.trimIndent()
        conn.createStatement().use { it.execute(ddl) }
    }

    private fun sourceReader(): InputStreamReader {
        // 1) Override via system property or env if supplied
        val sysPath = System.getProperty("seed.notice.path")?.takeIf { it.isNotBlank() }
            ?: System.getenv("SEED_NOTICE_PATH")?.takeIf { it.isNotBlank() }

        if (sysPath != null) {
            val path = Path.of(sysPath)
            val inStream = Files.newInputStream(path)
            return InputStreamReader(inStream, StandardCharsets.UTF_8)
        }

        // 2) Fallback to classpath resource
        val resourcePath = "db/data/notice.csv"
        val inStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            ?: error("CSV not found. Provide -Dseed.notice.path=... or place $resourcePath on classpath.")
        return InputStreamReader(inStream, StandardCharsets.UTF_8)
    }

    private fun rollbackQuietly(conn: Connection) {
        try {
            conn.rollback()
        } catch (_: SQLException) { /* hush */
        }
    }
}