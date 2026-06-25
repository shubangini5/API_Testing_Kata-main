package com.booking.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import java.util.logging.Logger;

public class Hooks {
    private static final Logger logger = Logger.getLogger(Hooks.class.getName());

    @Before
    public void setup() {

        String threadId = Thread.currentThread().toString();
        logger.info("TEST EXECUTING ON THREAD: " + threadId);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}