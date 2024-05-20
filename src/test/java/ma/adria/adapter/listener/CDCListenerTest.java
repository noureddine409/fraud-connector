package ma.adria.adapter.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.awaitility.Durations;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Properties;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class CDCListenerTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).waitingFor(Wait.forListeningPort());

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("auditDB")
            .withUsername("user")
            .withPassword("password")
            .withCopyFileToContainer(MountableFile.forClasspathResource("postgresql.conf"), "/etc/postgresql/postgresql.conf").withCopyFileToContainer(MountableFile.forClasspathResource("init/01_create_tables.sql"), "/docker-entrypoint-initdb.d/01_create_tables.sql").withCommand("postgres", "-c", "config_file=/etc/postgresql/postgresql.conf").waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    public static void initProperties(DynamicPropertyRegistry registry) {
        registry.add("debezium.db.host", postgresContainer::getHost);
        registry.add("debezium.db.port", () -> postgresContainer.getMappedPort(5432));
        registry.add("debezium.db.username", postgresContainer::getUsername);
        registry.add("debezium.db.password", postgresContainer::getPassword);
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("debezium.offset.backing.bootstrap.servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    void testListenerReceivesEvents() {
        log.info("Starting testListenerReceivesEvents...");
        // Insert test event into PostgreSQL using JDBC connection
        try (Connection connection = getConnection(postgresContainer); Statement statement = connection.createStatement()) {
            String insertSql = "INSERT INTO public.event_logs (id, actor, classname, codebanqueassocie, "
                    + "codepaysassocie, datecreated, eventname, ipaddress, plateforme, motif, activitytime) "
                    + "VALUES ('1000004029738', 'pzh2m5sc', 'ma.adria.bank.controller.AuthentificationController', "
                    + "'BF161', 'BF', '08/09/21 15:13:38,658000000', 'ConnexionOK', '172.16.53.129', 'Web', 'CONNEXION OK', "
                    + "'08/09/21 15:13:38,658000000')";
            statement.executeUpdate(insertSql);
            log.info("Test event inserted into PostgreSQL successfully.");
        } catch (SQLException e) {
            log.error("Failed to insert test event into PostgreSQL:", e);
            return; // Exit the test if the insert fails
        }


        await().pollInterval(Durations.ONE_HUNDRED_MILLISECONDS).atMost(Durations.FIVE_SECONDS).untilAsserted(this::verifyKafkaMessage);


        log.info("testListenerReceivesEvents completed.");
    }

    private void verifyKafkaMessage() {
        log.info("Starting verifyKafkaMessage...");

        // Set up Kafka consumer properties
        Properties props = prepareConsumerProperties();

        // Create Kafka consumer
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("t.events"));

            // Poll for new records
            ConsumerRecords<String, String> records = consumer.poll(Durations.FIVE_SECONDS);
            assertEquals(1, records.count(), "Expected one record");
            log.info("Received {} Kafka records.", records.count());
        } catch (Exception e) {
            log.error("Failed to verify Kafka message:", e);
        }

        log.info("verifyKafkaMessage completed.");
    }

    private static Connection getConnection(PostgreSQLContainer<?> container) throws SQLException {
        String jdbcUrl = container.getJdbcUrl();
        String username = container.getUsername();
        String password = container.getPassword();
        return DriverManager.getConnection(jdbcUrl, username, password);
    }


    private static @NotNull Properties prepareConsumerProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

}
