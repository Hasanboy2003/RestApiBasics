package com.epam.esm.configuration;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Bean Configuration
 */

@Configuration
@ComponentScan("com.epam.esm")
public class BeanConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/gift_certificate");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root123");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }


    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public MapSqlParameterSource parameterSource() {
        return new MapSqlParameterSource();
    }

}
