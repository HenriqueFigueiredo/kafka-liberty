#!/bin/bash

docker exec -it kafkaliberty_broker_1 /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic temperature
