package org.example.configuration;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.example.kafka.TemperatureResource;

@ApplicationScoped
@ApplicationPath("v1")
public class ApplicationConfiguration extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> resources = new HashSet<>();
    resources.add(TemperatureResource.class);
    return resources;
  }
}