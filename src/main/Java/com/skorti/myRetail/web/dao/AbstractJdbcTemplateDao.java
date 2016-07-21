package com.skorti.myRetail.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by skorti on 10/27/15.
 */
@Repository
public abstract class AbstractJdbcTemplateDao {

    private NamedParameterJdbcTemplate  jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
}
