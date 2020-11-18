package org.example.observable;

import java.util.Observable;
import java.util.Observer;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

public class TemperatureSourceObserver implements Observer {

  private final SseEventSink sseEventSink;
  private final Sse sse;
  private SseBroadcaster sseBroadcaster;

  public TemperatureSourceObserver(final SseEventSink sseEventSink, Sse sse) {
    this.sseEventSink = sseEventSink;
    this.sse = sse;
    this.sseBroadcaster = sse.newBroadcaster();
    sseBroadcaster.register(sseEventSink);
  }

  @Override
  public void update(Observable observable, Object o) {
    if (this.sseEventSink.isClosed()) {
      observable.deleteObserver(this);
      return;
    }
    sseBroadcaster.broadcast(sse.newEvent(o.toString()));
  }
}