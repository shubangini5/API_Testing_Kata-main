package com.booking.stepdefinitions;

import com.booking.models.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.DataMapper;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public UpdateSteps(TestContext testContext, BookingService bookingService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
    }

    @When("the user updates the booking dates:")
    public void theUserUpdatesTheBookingDates(DataTable dataTable) {
        updateBooking(dataTable);
    }

    @When("the user updates the guest information:")
    public void theUserUpdatesTheGuestBooking(DataTable dataTable) {
        updateBooking(dataTable);
    }

    @When("the user provides invalid details:")
    public void theUserUpdatesInvalidDetails(DataTable dataTable) {
        updateBooking(dataTable);
    }

    @When("the user updates an existing booking ID {string} with {string} token")
    public void theUserPerformsUpdateOnBookingWith(String bookingId,
                                                   String action,
                                                   DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

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
                throw new IllegalArgumentException("Unsupported action: " + action);
        }

        Response response = bookingService.updateBooking(
                Integer.parseInt(bookingId),
                request,
                token
        );

        testContext.setResponse(response);
    }


    @Then("the booking should be updated successfully")
    public void verifyStatusCode() {
        assertEquals(
                200,
                testContext.getResponse().statusCode()
        );
    }

    @Then("the update should be denied")
    public void denyUpdate() {
        assertErrorStatus();
    }

    @Then("the update should fail")
    public void failUpdate() {
        assertErrorStatus();
    }

    private void assertErrorStatus() {
        int status = testContext.getResponse().statusCode();
        assertTrue(
                status >= 400 && status < 500,
                "Expected 4xx status but got " + status
        );
    }

    private void updateBooking(DataTable dataTable) {
        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

        Response response = bookingService.updateBooking(
                testContext.getResponse().jsonPath().getInt("bookingid"),
                request,
                testContext.getToken()
        );

        testContext.setResponse(response);
    }

}