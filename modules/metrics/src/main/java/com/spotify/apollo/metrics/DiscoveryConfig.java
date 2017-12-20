package com.spotify.apollo.metrics;

import com.spotify.ffwd.http.HttpDiscovery;
import com.typesafe.config.Config;
import java.util.Collections;

public interface DiscoveryConfig {
  /**
   * Build an HTTP discovery component.
   */
  HttpDiscovery toHttpDiscovery();

  static DiscoveryConfig fromConfig(final Config config) {
    final String type = config.getString("type");

    switch (type) {
      case "static":
        final String host = config.getString("host");
        final int port = config.getInt("port");
        return new Static(host, port);
      case "srv":
        final String record = config.getString("record");
        return new Srv(record);
      default:
        throw new RuntimeException("Unrecognized discovery type: " + type);
    }
  }

  class Static implements DiscoveryConfig {
    private final String host;
    private final int port;

    Static(final String host, final int port) {
      this.host = host;
      this.port = port;
    }

    String getHost() {
      return host;
    }

    int getPort() {
      return port;
    }

    @Override
    public HttpDiscovery toHttpDiscovery() {
      final HttpDiscovery.HostAndPort server = new HttpDiscovery.HostAndPort(host, port);
      return new HttpDiscovery.Static(Collections.singletonList(server));
    }
  }

  class Srv implements DiscoveryConfig {
    private final String record;

    Srv(final String record) {
      this.record = record;
    }

    String getRecord() {
      return record;
    }

    @Override
    public HttpDiscovery toHttpDiscovery() {
      return new HttpDiscovery.Srv(record);
    }
  }
}
