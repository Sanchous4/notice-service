package com.example.noticeservice.db.config

import com.example.noticeservice.db.migration.V2__Fill_notice_table
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.migration.JavaMigration
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configures Flyway for database migrations.
 */
@Configuration
class FlywayConfig {

    /**
     * Creates and configures a [Flyway] instance.
     *
     * @return a configured [Flyway] object.
     */
    @Bean
    fun flyway(): Flyway =
        Flyway.configure()
            .baselineVersion("0")
            .dataSource("jdbc:postgresql://localhost:6543/notice-db", "user", "pass")
            .locations("classpath:db/migration")
            .javaMigrations(V2__Fill_notice_table() as JavaMigration)
            .baselineOnMigrate(true)
            .load()

    /**
     * Initializes Flyway migration during startup.
     *
     * @param f the [Flyway] instance created by [flyway].
     * @return a [FlywayMigrationInitializer] bean.
     */
    @Bean
    fun flywayInit(f: Flyway): FlywayMigrationInitializer {
        f.repair()
        return FlywayMigrationInitializer(f)
    }


}
