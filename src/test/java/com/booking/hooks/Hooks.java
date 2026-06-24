package com.booking.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import java.util.logging.Logger;

public class Hooks {
    private static final Logger logger = Logger.getLogger(Hooks.class.getName());

    @Before
    public void setup() {

        String threadName = Thread.currentThread().getName();
        String threadId = String.valueOf(Thread.currentThread().getId());

///        logger.info("═══════════════════════════════════════════════════════");
        logger.info("TEST EXECUTING ON THREAD: " + threadName);
        logger.info("THREAD ID: " + threadId);
///        logger.info("═══════════════════════════════════════════════════════");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}