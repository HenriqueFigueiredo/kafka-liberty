package org.example.kafka.consumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.observable.TemperatureStreamObservable;

@ApplicationScoped
public class TemperatureConsumer {

  @Inject
  @ConfigProperty(name="kelvin.diff")
  private Double kelvinDiff;

  @Inject
  private TemperatureStreamObservable streamObservable;

  @Inject
  private KafkaConsumer<String, String> consumer;

  public void worker(@Observes @Initialized(ApplicationScoped.class) Object o) {
    final Runnable runnable = () -> readMessages();
    new Thread(runnable).start();
  }

  private void readMessages() {
    while (true) {
      this.readStream().ifPresent(message -> streamObservable.setTemperature(convertTemperature(message)));
    }
  }

  private Double convertTemperature(final String kelvin) {
      return Double.valueOf(kelvin) + kelvinDiff;
  }

  private Optional<String> readStream()  {
    final List<String> temperatureList = new ArrayList<>();
    consumer.poll(Duration.ofMillis(100)).forEach(record -> {temperatureList.add(record.value());});
    if (!temperatureList.isEmpty()) consumer.commitAsync();
    return temperatureList.stream().findFirst();
  }
}