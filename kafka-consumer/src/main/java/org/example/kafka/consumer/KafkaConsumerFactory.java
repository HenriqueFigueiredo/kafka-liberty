package org.example.kafka.consumer;

import java.util.List;
import java.util.Properties;
import static java.util.stream.Collectors.toList;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class KafkaConsumerFactory {

  @Inject
  @ConfigProperty(name="bootstrap.server")
  private String bootstrapServer;

  @Inject
  @ConfigProperty(name="temperature.topic")
  private String temperatureTopic;

  @Produces
  public KafkaConsumer<String, String> create() {
    final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(propertiesConsumer());
    final List<PartitionInfo> partitionInfos = consumer.partitionsFor(temperatureTopic);
    consumer.assign(partitionInfos.stream().map(x -> new TopicPartition(x.topic(), x.partition())).collect(toList()));
    return consumer;
  }

  public void close(@Disposes KafkaConsumer<String, String> kafkaConsumer) {
    kafkaConsumer.close();
  }

  private Properties propertiesConsumer() {
    final Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
    return properties;
  }
}