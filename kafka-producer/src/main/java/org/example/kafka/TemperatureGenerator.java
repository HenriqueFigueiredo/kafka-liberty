package org.example.kafka;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TemperatureGenerator {

  @Inject
  @ConfigProperty(name="temperature.topic")
  private String temperatureTopic;

  @Inject
  @ConfigProperty(name="bootstrap.server")
  private String bootstrapServer;

  private Random random = new Random();

  public void worker(@Observes @Initialized(ApplicationScoped.class) Object o) throws Exception{
    final Runnable runnable = () -> produceRandomTemperature();
    new Thread(runnable).start();
  }

  private void produceRandomTemperature() {
    try (KafkaProducer<String, String> producer = new KafkaProducer<>(propertiesProducer())) {
      while (true) {sendMessage(producer);}
    }
  }

  private void sendMessage(KafkaProducer<String, String> producer) {
    final ProducerRecord<String , String> message = new ProducerRecord<>(temperatureTopic,
        UUID.randomUUID().toString(), String.valueOf(getRamdonTemperature()));
    producer.send(message);
    delay();
  }

  private int getRamdonTemperature() {
    final int min = 220;
    final int max = 320;
    return random.nextInt(max - min) + min;
  }

  private void delay() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private Properties propertiesProducer() {
    final Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return properties;
  }
}