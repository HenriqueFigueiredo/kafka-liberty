package org.example.kafka;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.observable.TemperatureSourceObserver;
import org.example.observable.TemperatureStreamObservable;

@RequestScoped
@Path("/temperature")
public class TemperatureResource {

  @Inject
  @ConfigProperty(name="temperature.name")
  private String temperatureName;

  @Inject
  private TemperatureStreamObservable streamObservable;

  @GET
  @Path("/name")
  @Produces(MediaType.TEXT_PLAIN)
  public String getTemperatureName() {
    return temperatureName;
  }

  @GET
  @Path("/stream")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void listen(@Context SseEventSink sseEventSink, @Context Sse sse) {
    streamObservable.addObserver(new TemperatureSourceObserver(sseEventSink, sse));
  }
}