# Quarkus CDC demo:

This project is a quick sample to demostrate how to implements Change Data Capture with AMQ Streams (Kafka) and Debezium


## Artifacts
 - [ocp_resources](ocp_resources/README.md) manifest used to create Kafka objects
 - [hibernate](apps/hibernate/README.md) Hibernate ORM to perform the CRUD operations on the database
 - [ceq-cdc](apps/ceq-cdc/README.md) Camel Extension for Quarkus to read messages emitted by Debezium, transform it and finally send to another Kafka Topic
 
 ## Deployment stages
 First of all use [ocp_resources](ocp_resources) in order to create OCP objects.   
 After all objects are created, you can deploy both applications [hibernate](apps/hibernate/README.md) and [ceq-cdc](apps/ceq-cdc/README.md)
 
## Requirements

- Ocp 4 Cluster
- AMQ Streams Operator installed