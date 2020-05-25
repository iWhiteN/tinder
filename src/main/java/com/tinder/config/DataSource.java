package com.tinder.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DataSource {
    private static HikariDataSource dataSource;

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {


            try (InputStream input = DataSource.class.getClassLoader().getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                HikariConfig config = new HikariConfig();
                config.setDriverClassName("db.driver");
                config.setJdbcUrl(prop.getProperty("db.url"));
                config.setUsername(prop.getProperty("db.user"));
                config.setPassword(prop.getProperty("db.password"));
                config.setIdleTimeout(10000);
                config.setMinimumIdle(5);
                config.setMaxLifetime(100000);
                config.setMaximumPoolSize(100);
                dataSource = new HikariDataSource(config);
                System.out.println("1231231232131231");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return dataSource;
    }
}
