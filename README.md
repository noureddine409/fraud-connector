# Audit Database CDC to Kafka

This project utilizes Debezium to capture change data from an audit database and publish new insert events to Kafka topics. It involves setting up a connector configuration to monitor specified tables, processing captured events, and publishing them to Kafka topics based on a defined classification.

## Components
- **Debezium Connector Configuration**
Configures the Debezium connector to monitor the audit database for changes and specifies the Kafka topics for event publication.
- **Debezium Engine**
Powers the change data capture (CDC) process, capturing database events and forwarding them for processing.
- **CDC Listener**
Listens for change events captured by the Debezium engine and processes them accordingly.
- **Event Classifier**
Classifies inserted rows into specific event types based on predefined rules.
- **Event Mapper**:
Maps events to appropriate formats based on their classification.
- **Kafka Producer**
Publishes the mapped events to designated Kafka topics.

## Configuration Details
 
- Database Configuration (application.properties):
Specifies connection details for the audit database and Debezium connector properties.
- Debezium Connector Configuration (application.properties):
Defines the connector name, type, offset storage, topic prefix, and other related settings.
- Kafka Configuration (application.properties):
Configures Kafka connection details such as bootstrap servers and topic settings.

## Services (Docker Compose)
- **PostgreSQL**: Runs the PostgreSQL database instance configured for auditing.
- **Zookeeper and Kafka**: Provides Kafka services necessary for event messaging.
- **Kafdrop**: Offers a web UI to monitor Kafka topics.

## Running Locally

### Prerequisites
- Java 17 or higher
- Maven
- Docker and docker compose

### Installation

1. Clone the repository:
  ```bash
  git clone https://noureddinelachgar@bitbucket.org/adria-fraud-detection/adapter.git
  ```
2. run docker compose file
```bash
  docker compose up -d
  ```
3. Build the project:
  ```bash
  mvn clean install
  ```
4. Run the application:
  ```bash
  mvn spring-boot:run
  ```
### Usage

1. execute sql query on table event_logs 
```sql
INSERT INTO public.event_logs (
    id,
    actor,
    classname,
    codebanqueassocie,
    codepaysassocie,
    datecreated,
    eventname,
    ipaddress,
    lastupdated,
    newvalue,
    oldvalue,
    persistedobjectid,
    persistedobjectversion,
    plateforme,
    propertyname,
    uri,
    ref1,
    ref2,
    ref3,
    ipaddress2,
    motif,
    mac_address,
    activitytime,
    contrat_id,
    xforwarded
)
VALUES (
    '1000004029738',
    'pzh2m5sc',
    'ma.adria.bank.controller.AuthentificationController',
    'BF161',
    'BF',
    '24/10/23 11:35:01,794000000',
    'ConnexionOK',
    '172.16.53.129',
    NULL,
    NULL,
    NULL,
    NULL,
    '0',
    'Web',
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    'CONNEXION OK',
    NULL,
    NULL,
    '475233962.0',
    NULL
);
```
2. you can visualize the new event in kafka broker via kafdrop ui http:localhost:9000/
