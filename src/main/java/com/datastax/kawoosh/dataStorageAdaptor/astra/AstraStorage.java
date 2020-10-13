package com.datastax.kawoosh.dataStorageAdaptor.astra;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.dataStorageAdaptor.astra.mapper.ConfigDao;
import com.datastax.kawoosh.dataStorageAdaptor.astra.mapper.ConfigRow;
import com.datastax.kawoosh.dataStorageAdaptor.astra.mapper.ConfigRowMapper;
import com.datastax.kawoosh.dataStorageAdaptor.astra.mapper.ConfigRowMapperBuilder;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.MappedAsyncPagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import jersey.repackaged.com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class AstraStorage extends DataStorage {

    private final ConfigDao configDao;

    public AstraStorage(Cluster cluster) {
        super(cluster);
        CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get("secure-connect-kawoosh.zip"))
                .withAuthCredentials("kawoosh", "kawoosh")
                .withKeyspace("kawoosh")
                .build();

        String tableName = cluster.getClusterName();

        SimpleStatement build = SchemaBuilder.createTable(tableName)
                .ifNotExists()
                .withPartitionKey("year", DataTypes.TEXT)
                .withPartitionKey("quarter", DataTypes.TEXT)
                .withPartitionKey("environment_type", DataTypes.TEXT)
                .withPartitionKey("conf_name", DataTypes.TEXT)
                .withClusteringColumn("node_ip", DataTypes.TEXT)
                .withColumn("value", DataTypes.TEXT)
                .withColumn("file_path", DataTypes.TEXT)
                .build()
                .setTimeout(Duration.ofMillis(10000));

        session.execute(build);

        ConfigRowMapper mapper = new ConfigRowMapperBuilder(session).build();
        configDao = mapper.configDao(CqlIdentifier.fromCql("kawoosh"),
                CqlIdentifier.fromCql(tableName.toLowerCase()));
    }

    @SneakyThrows
    @Override
    public CompletableFuture<List<Config>> read(String confName) {
        CompletableFuture<MappedAsyncPagingIterable<Config>> mappedAsyncPagingIterableCompletableFuture = configDao.findById(cluster.getYear(),
                cluster.getQuarter(),
                cluster.getEnvironmentType(),
                confName)
                .thenApply(configRowMappedAsyncPagingIterable -> configRowMappedAsyncPagingIterable
                        .map(c -> new Config(c.getNodeIp(), c.getFilePath(), c.getConfName(), c.getValue())));
        List<Config> configs = Lists.newArrayList();
        MappedAsyncPagingIterable<Config> configMappedAsyncPagingIterable = mappedAsyncPagingIterableCompletableFuture.get();

        while(true) {
            configMappedAsyncPagingIterable.currentPage().forEach(c -> configs.add(c));
            if(configMappedAsyncPagingIterable.hasMorePages())
                configMappedAsyncPagingIterable.fetchNextPage();
            else
                break;
        }

        return CompletableFuture.completedFuture(configs);
    }

    @Override
    public CompletableFuture<Boolean> write(Config conf) {
        ConfigRow configRow = new ConfigRow(
                cluster.getYear(),
                cluster.getQuarter(),
                cluster.getEnvironmentType(),
                conf.getConfName(),
                conf.getNodeIp(),
                conf.getValue(),
                conf.getFilename());
        configDao.save(configRow);
        return CompletableFuture.completedFuture(true);
    }
}
