package com.jonathanfoucher.sharedmdcexample.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleService {
    private final ThreadRunnerService threadRunnerService;

    @Scheduled(cron = "0 */1 * * * *")
    public void tasksExecution() {
        MDC.put("correlation-id", UUID.randomUUID().toString());
        log.info("START");

        threadRunnerService.run(() -> someTask(3000, 1));
        log.info("Single task completed");

        List<Runnable> runnables = new ArrayList<>();
        runnables.add(() -> someTask(3000, 2));
        runnables.add(() -> someTask(2000, 3));
        runnables.add(() -> someTask(4000, 4));
        threadRunnerService.run(runnables);
        log.info("Multiple tasks completed");

        log.info("END");
    }

    private void someTask(int sleepTime, int taskNumber) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException exception) {
            log.error(exception.getMessage(), exception);
            Thread.currentThread().interrupt();
        }
        log.info("Inside sub thread, executing task number {}", taskNumber);
    }
}
