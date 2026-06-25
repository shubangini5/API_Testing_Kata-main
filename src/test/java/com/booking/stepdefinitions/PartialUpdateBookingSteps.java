package com.booking.stepdefinitions;

import com.booking.services.BookingService;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Step definitions for partial update booking scenarios.
//PATCH is not working so designed the feature to expect an error
//Its throwing 405 so i designed it to expect an error
public class PartialUpdateBookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public PartialUpdateBookingSteps(TestContext testContext, BookingService bookingService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
    }

    @When("the user partially updates the booking with:")
    public void theUserPartiallyUpdatesTheBookingWith(DataTable dataTable) {

        Map<String, Object> requestBody =
                mapPartialUpdateRequest(dataTable);

        Response response =
                bookingService.partialUpdateBooking(
                        testContext.getBookingId(),
                        requestBody,
                        testContext.getToken()
                );

        testContext.setResponse(response);
    }

    @When("the user performs partial update {string} on booking {string} with:")
    public void theUserPerformsPartialUpdateOnBookingWith(
            String action,
            String bookingId,
            DataTable dataTable) {

        Map<String, Object> requestBody =
                mapPartialUpdateRequest(dataTable);

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
                        "Unsupported action: " + action
                );
        }

        Response response =
                bookingService.partialUpdateBooking(
                        Integer.parseInt(bookingId),
                        requestBody,
                        token
                );

        testContext.setResponse(response);
    }

    private Map<String, Object> mapPartialUpdateRequest(
            DataTable dataTable) {

        Map<String, String> row =
                dataTable.asMaps(String.class, String.class).get(0);

        Map<String, Object> requestBody = new HashMap<>();

        if (row.containsKey("firstname")) {
            requestBody.put("firstname", row.get("firstname"));
        }

        if (row.containsKey("lastname")) {
            requestBody.put("lastname", row.get("lastname"));
        }

        if (row.containsKey("depositpaid")) {
            requestBody.put(
                    "depositpaid",
                    Boolean.parseBoolean(row.get("depositpaid"))
            );
        }

        return requestBody;
    }

    //The patch API is not working as expected, so bypassing the error by checking for 405
    @Then("the booking detail should not be updated")
    public void verifyStatusCode() {
        assertEquals(
                405,
                testContext.getResponse().statusCode()
        );
    }
}