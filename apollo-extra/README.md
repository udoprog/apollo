# Apollo Extra

This Apollo library contains some utilities that may make your life easier.

## com.spotify.apollo.concurrent

Defines a couple of utilities that make it easier to move between `ListenableFuture`s and
`CompletionStage`s. Example usage:

```java
    ListenableFuture<Message> future = listenableFutureClient.send(myRequest);

    CompletionStage<Response<String>> response = Util.asStage(future)
        .thenApply(message -> Response.forPayload(message.data()));
```

Also defines the 
[`ExecutorServiceCloser`](src/main/java/com/spotify/apollo/concurrent/ExecutorServiceCloser.java) 
utility, which makes it convenient to register application-specific
`ExecutorService` instances with the Apollo `Closer` for lifecycle 
management.

## com.spotify.apollo.route

Contains some serializer middlewares, and utilities for versioning endpoints.

## com.spotify.apollo.logging

Contains logging utilities, or more generally, a solution that allows
subscribing for notifications of request outcomes, with a default 
implementation that logs using the Apache HTTPD 'combined' format.

To send this to an access log file, use a configuration similar to:

```
    <appender name="ACCESSLOG" class="ch.qos.logback.core.FileAppender">
        <file>/path/to/access.log</file>
        <encoder>
            <pattern>%msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.spotify.apollo.logging.RequestLoggingDecorator" level="INFO">
        <appender-ref ref="ACCESSLOG"/>
    </logger>
```
