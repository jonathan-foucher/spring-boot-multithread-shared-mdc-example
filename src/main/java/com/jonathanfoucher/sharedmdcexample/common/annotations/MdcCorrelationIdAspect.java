package com.jonathanfoucher.sharedmdcexample.common.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Order(1)
@Component
@Slf4j
public class MdcCorrelationIdAspect {
    private static final String CORRELATION_ID_MDC_KEY = "correlation-id";

    @Before("@annotation(CorrelationIdMdc)")
    public void setCorrelationId() {
        MDC.put(CORRELATION_ID_MDC_KEY, UUID.randomUUID().toString());
        log.info("Correlation id set");
    }

    @After("@annotation(CorrelationIdMdc)")
    public void removeCorrelationId() {
        MDC.remove(CORRELATION_ID_MDC_KEY);
        log.info("Correlation id removed");
    }
}
