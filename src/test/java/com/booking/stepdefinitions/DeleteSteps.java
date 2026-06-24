package com.booking.stepdefinitions;

import com.booking.services.BookingService;
import com.booking.utils.TestContext;
import io.cucumber.java.en.When;

public class DeleteSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public DeleteSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    @When("the user deletes the booking")
    public void theUserDeletesTheBooking() {

        testContext.setResponse(
                bookingService.deleteBooking(
                        testContext.getBookingId(),
                        testContext.getToken()
                )
        );
    }

    @When("the user attempts to delete the booking again")
    public void theUserAttemptsToDeleteTheBookingAgain() {

        testContext.setResponse(
                bookingService.deleteBooking(
                        testContext.getBookingId(),
                        testContext.getToken()
                )
        );
    }

    @When("the user performs delete {string} on booking {string}")
    public void theUserPerformsDeleteOnBooking(
            String action,
            String bookingId) {

        String token;

        switch (action.toLowerCase()) {

            case "validtoken":
                token = testContext.getToken();
                break;

            case "notoken":
                token = null;
                break;

            case "invalidtoken":
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
}