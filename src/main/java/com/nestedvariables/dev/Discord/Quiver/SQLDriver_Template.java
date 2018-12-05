package com.nestedvariables.dev.Discord.Quiver;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SQLDriver_Template {
  // CHANGE THE CLASS NAME ^ TO WHAT YOU NAMED THE FILE WITHOUT THE ".java"
    private static HikariDataSource ds;
    
    static{

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://<PUT YOUR MYSQL SERVER IP HERE>:<PUT YOUR MYSQL SERVER PORT HERE>/<PUT YOUR MYSQL DATABASE NAME HERE>");
        config.setUsername("PUT YOUR MYSQL SERVER USERNAME HERE");
        config.setPassword("PUT YOUR MYSQL SERVER PASSWORD HERE");  
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }
}