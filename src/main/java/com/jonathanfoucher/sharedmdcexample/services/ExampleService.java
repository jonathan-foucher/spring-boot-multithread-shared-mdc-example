package com.jonathanfoucher.sharedmdcexample.services;

import com.jonathanfoucher.sharedmdcexample.common.annotations.CorrelationIdMdc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleService {
    private final ThreadRunnerService threadRunnerService;

    @Scheduled(cron = "0 */1 * * * *")
    @CorrelationIdMdc
    public void tasksExecution() {
        log.info("START");

        threadRunnerService.run(() -> someTask(3000, 1));
        log.info("Single task completed");

        List<Runnable> runnables = new ArrayList<>();
        runnables.add(() -> someTask(3000, 3));
        runnables.add(() -> someTask(2000, 2));
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
