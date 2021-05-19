package com.cs492e.vocali

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URISyntaxException
import javax.sql.DataSource


@Configuration
class MainConfig {
    @Bean
    @Throws(URISyntaxException::class)
    fun dataSource(): DataSource {
        val dbUrl = System.getenv("JDBC_DATABASE_URL")
        val username = System.getenv("JDBC_DATABASE_USERNAME")
        val password = System.getenv("JDBC_DATABASE_PASSWORD")

        return HikariConfig().apply {
            this.jdbcUrl = dbUrl
            this.username = username
            this.password = password
        }.run { HikariDataSource(this) }
    }
}