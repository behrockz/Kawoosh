package com.datastax.kawoosh.dataStorageAdaptor.astra.mapper;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;


@Dao
public interface ConfigDao {
    @Select
    PagingIterable<ConfigRow> findById(String year, String quarter, String environmentType, String configName);

    @Insert
    void save(ConfigRow configRow);

    @Delete
    void delete(ConfigRow product);
}