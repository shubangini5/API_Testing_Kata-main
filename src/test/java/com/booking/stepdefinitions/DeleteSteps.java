package com.booking.stepdefinitions;

import com.booking.services.BookingService;
import com.booking.utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;


public class DeleteSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public DeleteSteps(TestContext testContext, BookingService bookingService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
    }

    @When("the user deletes the booking")
    public void theUserDeletesTheBooking() {
        testContext.setResponse(
                bookingService.deleteBooking(
                        testContext.getResponse().jsonPath().getInt("bookingid"),
                        testContext.getToken()
                )
        );
    }

    @When("the user deletes an already deleted booking")
    public void theUserDeletesDeletedBooking() {
        testContext.setResponse(
                bookingService.deleteBooking(
                        testContext.getBookingId(),
                        testContext.getToken()
                )
        );
    }

    @When("the user deletes the booking again")
    public void theUserAttemptsToDeleteTheBookingAgain() {

        testContext.setResponse(
                bookingService.deleteBooking(
                        testContext.getBookingId(),
                        testContext.getToken()
                )
        );
    }

    @When("the user deletes an existing booking ID {string} with {string} token")
    public void theUserPerformsDeleteOnBooking(String bookingId, String action) {
        String token;

        switch (action.toLowerCase()) {

            case "valid":
                token = testContext.getToken();
                break;

            case "no":
                token = null;
                break;

            case "invalid":
                token = "invalid-token";
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported action: " + action);
        }

        testContext.setResponse(
                bookingService.deleteBooking(
                        Integer.parseInt(bookingId),
                        token
                )
        );
    }

    @Then("the booking should be removed successfully")
    public void verifyStatusCode() {

        assertEquals(
                202,
                testContext.getResponse().statusCode()
        );
    }

    @Then("the deletion should fail")
    public void verifyNotFound() {

        assertEquals(
                404,
                testContext.getResponse().statusCode()
        );
    }

    @Then("the deletion should be denied")
    public void denyDeletion() {
        int status = testContext.getResponse().statusCode();

        assertTrue(
                status >= 400 && status < 500,
                "Expected 4xx status but got " + status
        );
    }

}