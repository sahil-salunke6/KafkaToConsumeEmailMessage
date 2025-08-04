#!/bin/bash
export CLASSPATH=$CLASSPATH:$(echo libs/*.jar | tr ' ' ':')

echo "Starting Zookeeper..."
bash bin/zookeeper-server-start.sh -daemon config/zookeeper.properties

echo "Starting Kafka Broker..."
bash bin/kafka-server-start.sh config/server.properties
