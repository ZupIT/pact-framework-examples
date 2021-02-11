
Entrar no Docker Bash
docker exec -it kafka_kafka_1 bash

#Criar um Topico
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic client_creation

# Delete Topic
#kafka-topics.sh --delete --zookeeper zookeeper:2181 --topic client_creation

kafka-console-producer.sh --broker-list localhost:9092 --topic client_creation
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic client_creation