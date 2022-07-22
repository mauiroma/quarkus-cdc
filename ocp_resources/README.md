# Create Resource on OCP
- Requirements   
AMQStreams Operator installed

```
oc apply -f 00_Postgresql.yaml
oc apply -f 01_Kafka.yaml
oc apply -f 02_ImageStream.yaml
oc apply -f 03_KafkaConnect.yaml
oc apply -f 04_KafkaConnector.yaml
```

## Verify
In order to verify that topic `cdc.public.known_fruits` exists
```
oc rsh cdc-amq-streams-kafka-0 
./bin/kafka-topics.sh --list --bootstrap-server=localhost:9092
```
In order to verify that consumer group `ceq-cdc` exists and is associated to topic `cdc.public.known_fruits` and `fruits`
```
oc rsh cdc-amq-streams-kafka-0 
./bin/kafka-consumer-groups.sh --bootstrap-server=localhost:9092 --describe --group=ceq-cdc
```



# HTTP Bridge
Optionally you can configure Kafka Bridge in order to consume messages via `curl`
```
oc apply -f 05_KafkaBridge.yaml
oc apply -f 06_RouteKafkaBridge.yaml
```

## Create Consumer
export ROUTE=http://cdc-http-route-cdc.apps.playground.rhocplab.com/

curl -X POST $ROUTE/consumers/cdc-group \
  -H 'content-type: application/vnd.kafka.v2+json' \
  -d '{
    "name": "cdc-http-consumer",
    "format": "json",
    "auto.offset.reset": "earliest",
    "enable.auto.commit": false
  }'


# Subscribe Consumer
curl -X POST $ROUTE/consumers/cdc-group/instances/cdc-http-consumer/subscription \
  -H 'content-type: application/vnd.kafka.v2+json' \
  -d '{
    "topics": [
        "fruits"
    ]
}'

# Consuming
curl $ROUTE/consumers/cdc-group/instances/cdc-http-consumer/records \
  -H 'accept: application/vnd.kafka.json.v2+json'

# Delete Consumer
curl -X DELETE $ROUTE/consumers/cdc-group/instances/cdc-http-consumer