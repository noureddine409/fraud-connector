package ma.adria.adapter.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Debezium connector.
 */
@Configuration
public class DebeziumConnectorConfig {

    @Value("${debezium.db.host}")
    private String dbHost;
    @Value("${debezium.db.port}")
    private String dbPort;
    @Value("${debezium.db.username}")
    private String postgresUsername;
    @Value("${debezium.db.password}")
    private String postgresPassword;
    @Value("${debezium.db.name}")
    private String dbName;
    @Value("${debezium.schema.include.list}")
    private String schemaIncludeList;
    @Value("${debezium.table.include.list}")
    private String tableIncludeList;
    @Value("${debezium.connector.name}")
    private String connectorName;
    @Value("${debezium.connector.class}")
    private Class<?> connectorClass;
    @Value("${debezium.offset.storage}")
    private Class<?> offsetStorage;
    @Value("${debezium.plugin.name}")
    private String pluginName;
    @Value("${debezium.topic.prefix}")
    private String topicPrefix;
    @Value("${debezium.offset.flush.interval.ms}")
    private String offsetFlushIntervalMs;
    @Value("${debezium.errors.log.include.messages}")
    private boolean writeToLogs;

    @Value("${debezium.skipped-operations}")
    private String skippedOperations;

    @Value("${debezium.offset.storage.partitions}")
    private int offsetStoragePartitions;

    @Value("${debezium.offset.storage.replication.factor}")
    private int offsetStorageReplicationFactor;

    @Value("${debezium.offset.backing.topic}")
    private String offsetBackingTopic;

    @Value("${debezium.offset.backing.bootstrap.servers}")
    private String offsetBackingOffsetBootstrapServer;


    @Bean
    public io.debezium.config.Configuration postgresConnector() {

        return io.debezium.config.Configuration.create()
                //This sets the name of the Debezium connector instance. It’s used for logging and metrics.
                .with("name", connectorName)
                //This specifies the Java class for the connector. Debezium uses this to create the connector instance.
                .with("connector.class", connectorClass.getName())
                .with("bootstrap.servers", offsetBackingOffsetBootstrapServer)
                .with("offset.storage.topic", offsetBackingTopic)
                .with("offset.storage.partitions", offsetStoragePartitions)
                .with("offset.storage.replication.factor", offsetStorageReplicationFactor)
                .with("offset.storage", offsetStorage.getName())
                .with("offset.flush.interval.ms", offsetFlushIntervalMs)
                //Connect Debezium connector to the source DB
                .with("database.hostname", dbHost)
                .with("database.port", dbPort)
                .with("database.user", postgresUsername)
                .with("database.password", postgresPassword)
                .with("database.dbname", dbName)
                .with("database.server.name", dbName)
                .with("plugin.name", pluginName)
                .with("schema.include.list", schemaIncludeList)
                .with("table.include.list", tableIncludeList)
                .with("topic.prefix", topicPrefix)
                .with("errors.log.include.messages", writeToLogs)
                // skip operations
                .with("skipped.operations", skippedOperations)
                .build();
    }

}
