# Camel Extensio For Quarkus - Change Data Capture Project

This project uses Quarkus with camel extension in order to consume messages from Kafka Topic emitted by Debezium, transform those messages into other JSON using AtlasMap, and finally send them to another Kafka Topic  

The application consist in two camel route:
- Fromcdc.public.known_fruits2fruits   
  Read messages from Topic public.known_fruits
- Fromfruits   
  Read messages from Topic fruits


## Packaging and deploy the application
### using maven
```shell script
./mvnw clean package
```

### using cli
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
mkdir ocp && cp target/*-runner.jar ocp/
oc new-build --name=ceq-cdc --binary=true -i=java:openjdk-11-ubi8
oc start-build ceq-cdc --from-dir=./ocp --follow
oc new-app ceq-cdc
oc expose svc/ceq-cdc
mvn clean && rm -rf ocp
```