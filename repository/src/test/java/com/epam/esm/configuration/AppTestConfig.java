package com.epam.esm.configuration;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.dao.gift_certificate.GiftCertificateDAOImpl;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dao.tag.TagDAOImpl;
import com.epam.esm.row_mapper.GiftCertificateRowMapper;
import com.epam.esm.row_mapper.TagRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * App Test Configuration
 */

@Configuration
public class AppTestConfig {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");
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

    @Bean
    public GiftCertificateDAO giftCertificateDAO(){
        return new GiftCertificateDAOImpl(jdbcTemplate(),parameterSource(),giftCertificateRowMapper());
    }

    @Bean
    public TagDAO tagDAO(){
        return new TagDAOImpl(jdbcTemplate(),tagRowMapper(),parameterSource());
    }

    @Bean
    GiftCertificateRowMapper giftCertificateRowMapper(){
        return new GiftCertificateRowMapper();
    }

    @Bean
    TagRowMapper tagRowMapper(){
        return new TagRowMapper();
    }
}
