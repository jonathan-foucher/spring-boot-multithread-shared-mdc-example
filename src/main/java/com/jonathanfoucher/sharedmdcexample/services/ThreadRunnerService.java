package com.jonathanfoucher.sharedmdcexample.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadRunnerService {
    private static final int NUMBER_OF_THREADS = 2;
    private ExecutorService executorService;

    @PostConstruct
    public void postConstruct() {
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @PreDestroy
    public void preDestroy() {
        executorService.shutdown();
    }

    public void run(@NonNull Runnable runnable) {
        runWithParentThreadMdcContext(runnable).join();
    }

    public void run(@NonNull List<Runnable> runnables) {
        List<CompletableFuture<Void>> completableFutures = runnables.stream()
                .map(this::runWithParentThreadMdcContext)
                .toList();

        completableFutures.forEach(CompletableFuture::join);
    }

    private CompletableFuture<Void> runWithParentThreadMdcContext(@NonNull Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return CompletableFuture.runAsync(() -> {
            MDC.setContextMap(contextMap);
            runnable.run();
            MDC.clear();
        }, executorService);
    }
}
