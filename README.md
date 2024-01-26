# spring-cloud-stream-test-binder-issue-demo
This is a demo of the problem where the `OutputDestination` cannot be Dependency Injected (DI) when using the Test Binder, `StreamBridge`, and `OutputDestination` without registering a message handler.

## Environment
* Java 17
* Spring Boot 3.1.1
* Spring Cloud 2022.0.3
* Spring Cloud Stream 4.0.3

## Demo
[source code](./issue-demo/src/test/java/com/example/AppTest.java)
### How to run
```bash
$ cd issue-demo
$ mvn clean test
```
### Excerpt from the results
```bash
[ERROR] Errors:
[ERROR]   AppTest.test » UnsatisfiedDependency Error creating bean with name 'com.example.AppTest': Unsatisfied dependency expressed through field 'outputDestination': No qualifying bean of type 'org.springframework.cloud.stream.binder.test.OutputDestination' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
```

## Workaround 1
[source code](./workaround-1/src/test/java/com/example/AppTest.java)

Use `@Import(TestChannelBinderConfiguration.class)` (the previous method).
### How to run
```bash
$ cd workaround-1
$ mvn clean test
```

## Workaround 2
[source code](./workaround-2/src/test/java/com/example/AppTest.java)

Use `@Lazy` to delay DI.
This method requires calling `StreamBridge#send` before using the `OutputDestination` variable.
### How to run
```bash
$ cd workaround-2
$ mvn clean test
```
