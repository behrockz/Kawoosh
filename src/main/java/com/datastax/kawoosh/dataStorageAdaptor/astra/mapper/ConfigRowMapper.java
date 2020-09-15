package com.datastax.kawoosh.dataStorageAdaptor.astra.mapper;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface ConfigRowMapper {
    @DaoFactory
    ConfigDao configDao(@DaoKeyspace CqlIdentifier keyspace, @DaoTable CqlIdentifier table);
}