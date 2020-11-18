package org.example.observable;

import java.util.Observable;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemperatureStreamObservable extends Observable {

  public void setTemperature(final Double temperature) {
    setChanged();
    notifyObservers(temperature);
  }
}