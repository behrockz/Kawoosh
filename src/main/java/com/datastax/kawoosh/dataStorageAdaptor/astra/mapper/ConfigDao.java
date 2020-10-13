package com.datastax.kawoosh.dataStorageAdaptor.astra.mapper;

import com.datastax.oss.driver.api.core.MappedAsyncPagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;

import java.util.concurrent.CompletableFuture;


@Dao
public interface ConfigDao {
    @Select
    CompletableFuture<MappedAsyncPagingIterable<ConfigRow>> findById(String year, String quarter, String environmentType, String configName);

    @Insert
    void save(ConfigRow configRow);

    @Delete
    void delete(ConfigRow product);
}