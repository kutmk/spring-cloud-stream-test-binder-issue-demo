package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

@SpringBootTest
public class AppTest {
  @Autowired
  @Lazy // Delay the injection until outputDestination is used
  private OutputDestination outputDestination;

  @Autowired
  private StreamBridge streamBridge;

  private static final String DESTINATION = "demoDestination";

  @Test
  public void test() throws InterruptedException {
    streamBridge.send(DESTINATION, "Hello World!"); // The Bean is created here
    Message<byte[]> message = outputDestination.receive(5000, DESTINATION); // outputDestination is injected here
    Assert.notNull(message, "message must not be null");
  }
}
