## Introduction
This project is an example of shared MDC between executed sub threads with Spring Boot.

A main thread calls a service to manage multi threading and use a correlation id to set in the MDC for logging.
An annotation `@CorrelationIdMdc` has also been created to setup the correlation id in the main thread.

## Run the project
### Application
You can just run the Spring Boot project and check the logs.
