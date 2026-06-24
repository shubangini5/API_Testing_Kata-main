package com.booking.stepdefinitions;

import com.booking.models.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.DataMapper;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class UpdateSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public UpdateSteps(TestContext testContext, BookingService bookingService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
    }

    @When("the user updates the booking with:")
    public void theUserUpdatesTheBookingWith(DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

        Response response = bookingService.updateBooking(
                testContext.getBookingId(),
                request,
                testContext.getToken()
        );

        testContext.setResponse(response);
    }

    @When("the user performs update {string} on booking {string} with:")
    public void theUserPerformsUpdateOnBookingWith(String action,
                                                   String bookingId,
                                                   DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

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
                throw new IllegalArgumentException("Unsupported action: " + action);
        }

        Response response = bookingService.updateBooking(
                Integer.parseInt(bookingId),
                request,
                token
        );

        testContext.setResponse(response);
    }

}