package com.ows.uploadfileim.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ows.uploadfileim.service.FileMetaService;



@Component
public class FileMetaHealth extends AbstractHealthIndicator {
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private FileMetaService fileMetaService;
    
    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        if (this.dataSource == null) {
            builder.down().withDetail("DB Driver", "Not available");
            return;
        }
        
        try {
            String dbDriver = jdbcTemplate.execute((Connection conn) -> conn.getMetaData().getDriverName());
            builder.up().withDetail("DB Driver", dbDriver);
            
            int count = jdbcTemplate.execute((Statement stm) -> {
                ResultSet rs = stm.executeQuery("select count(*) from FILEMETA");
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;
            });
            builder.withDetail("FileMeta Count", count);
        } catch (DataAccessException ex) {
            builder.down(ex);
        }
    }

}
