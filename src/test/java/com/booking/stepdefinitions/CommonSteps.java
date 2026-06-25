package com.booking.stepdefinitions;

import com.booking.models.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.DataMapper;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

// Common reusable step definitions shared across features.
public class CommonSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public CommonSteps(TestContext testContext, BookingService bookingService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {

        assertEquals(
                expectedStatusCode,
                testContext.getResponse().statusCode()
        );
    }

    @Then("the user should see the error message {string}")
    public void theErrorMessageContains(String expectedMessage) {

        String actualResponse =
                testContext.getResponse()
                        .asString();

        assertTrue(
                actualResponse.contains(expectedMessage),
                "Expected: " + expectedMessage +
                        " but actual response was: " + actualResponse
        );
    }

    @Then("no response should be returned")
    public void responseBodyShouldBeEmpty() {

        assertTrue(
                testContext.getResponse()
                        .asString()
                        .trim()
                        .isEmpty()
        );
    }

    @Then("the authentication request should be rejected")
    public void verifyFailureMethod() {

        assertEquals(
                405,
                testContext.getResponse().statusCode()
        );
    }

    // Creates a dedicated booking for the current scenario.
    // This prevents data sharing during parallel execution.
    @When("the user has an existing booking")
    public void theUserCreatesABookingWith(DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

        testContext.setBookingRequest(request);

        Response response =
                bookingService.createBooking(request);

        testContext.setResponse(response);
        Integer bookingId = response.jsonPath()
                .getObject("bookingid", Integer.class);

        if (bookingId == null) {
            throw new AssertionError(
                    "Booking ID was not returned in response: " + response.asString()
            );
        }

        testContext.setBookingId(bookingId);
    }
}