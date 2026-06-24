package com.booking.stepdefinitions;

import com.booking.utils.TestContext;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {

    private final TestContext testContext;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {

        assertEquals(
                expectedStatusCode,
                testContext.getResponse().statusCode()
        );
    }

    @Then("the response body should be empty")
    public void responseBodyShouldBeEmpty() {

        assertTrue(
                testContext.getResponse()
                        .asString()
                        .trim()
                        .isEmpty()
        );
    }
}