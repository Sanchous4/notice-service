package com.example.notice_service.db.config


import com.example.notice_service.db.migration.V2__Fill_notice_table
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.migration.JavaMigration
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig {
    @Bean
    fun flyway(): Flyway =
        Flyway.configure()
            .dataSource("jdbc:postgresql://localhost:6543/notice-db", "user", "pass")
            .locations("classpath:db/migration") // keep SQL folder
            .javaMigrations(V2__Fill_notice_table() as JavaMigration)
            .baselineOnMigrate(true)
            .load()

    @Bean
    fun flywayInit(f: Flyway) = FlywayMigrationInitializer(f)
}