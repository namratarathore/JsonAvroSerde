
## Setup
In order to complete the exercises you will need to have a local installation of kafka running on your machine.

We will use docker to run a local kafka cluster.
1. Download https://www.docker.com/products/docker-desktop if you don't already have it installed
2. You might need to give docker more resources than default in order for everything to run smoothly. I have it set at 6 cores, 4G memory
3. If everything got installed correctly, running `docker ps` on the command line should return something like this:
```
CONTAINER ID IMAGE COMMAND CREATED STATUS PORTS NAMES
```
4. Navigate to wherever you downloaded this repository and run `docker-compose up -d` This will take some time the first time, but after images get downloaded `docker ps` should return something like:
```
CONTAINER ID   IMAGE                                   COMMAND                  CREATED          STATUS          PORTS                                            NAMES
38e437f331d7   confluentinc/cp-schema-registry:7.0.1   "/etc/confluent/dock…"   4 seconds ago    Up 2 seconds    0.0.0.0:8081->8081/tcp                           schema-registry
9401d55cf344   confluentinc/cp-server:7.0.1            "/etc/confluent/dock…"   12 seconds ago   Up 10 seconds   0.0.0.0:9092->9092/tcp, 0.0.0.0:9101->9101/tcp   broker
8052b5c510dc   confluentinc/cp-zookeeper:7.0.1         "/etc/confluent/dock…"   13 seconds ago   Up 11 seconds   2888/tcp, 0.0.0.0:2181->2181/tcp, 3888/tcp       zookeeper
```
5. In order to interact with our local kafka, we will use Confluent Platform binaries. https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html
  
  TLDR; `curl -O http://packages.confluent.io/archive/7.0/confluent-7.0.1.zip`

6. Verify kafka is running properly by listing all the topics in your local kafka. `./bin/kafka-topics --list --bootstrap-server localhost:9092`
You should see whole bunch of topics which are internal to Kafka/Confluent Platform 
```
__consumer_offsets
_confluent-command
_confluent-metrics
_confluent-telemetry-metrics
_confluent_balancer_api_state
_confluent_balancer_broker_samples
_confluent_balancer_partition_samples
_schemas
```

# Exercises

## Running the sample

Before beginning work on the actual exercises let's first make sure you can successfully run the sample program included in the repo.

First, run `mvn install` in the root of the project

Using the binaries included in Confluent download 
1. Create a kafka topic `./bin/kafka-topics --create --topic output-topic-string --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1`
2. Begin printing the contents of the topic into console` ./bin/kafka-console-consumer --topic output-topic-string --bootstrap-server localhost:9092 --property print.key=true --from-beginning`
3. Run main class of SampleProducerRunner
4. You should see `key	value` printed in the console of the `kafka-console-consumer`

## 1- Kafka Consumer
Using https://developer.confluent.io/tutorials/creating-first-apache-kafka-consumer-application/kafka.html as reference, create an application that imitates the behavior of `kafka-console-consumer`, aka prints key and value sent to a topic.
Print the contents of `output-topic-string` topic and feel free to modify `SampleProducerRunner` to produce more than a single message. 

## 2- Working with JSON
Sending raw strings does not facilitate writing clean code. In the `com.expediagroup.streamcourse.hw1.json` package you will find a class `ImportantBusinessEvent`. Implement a JsonRecordProducer & JsonRecordConsumer that serializes & deserializes
`ImportantBusinessEvent`. You will know `ImportantBusinessEvent` got deserialized properly if calling toString method on it prints something
like `ImportantBusinessEvent eventName is event and value is 1`.

If you want to be ambitious, its possible to write this code in a generic fashion. Aka, create a class `public class JsonRecordProducer<T>`
that implements a method:

```public void sendBlocking(String key, T value) ```

## 3- Working with AVRO
There are many disadvantages to using JSON as values for kafka topics. In the `com.expediagroup.streamcourse.hw1.avro` package,
implement a producer and consumer capable of serializing and deserializing Avro records.

Make sure the records are being serialized using Confluent Avro Format.
Hint: Your schema registry is running at: http://localhost:8081/subjects

## 4- Putting knowledge into practice
In any of the Consumers we have written so far, add the following feature:
- If user passes an argument `--where-we-left-off` subsequent runs of the consumer application should not reconsume already consumed messages
- If user passes an argument `--from-beginning` consumer application should read from the beginning of the topic