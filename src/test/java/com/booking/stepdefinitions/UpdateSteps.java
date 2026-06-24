package com.booking.stepdefinitions;

import com.booking.models.BookingDates;
import com.booking.models.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.DataMapper;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

public class UpdateSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public UpdateSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
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
//
//    private BookingRequest mapBookingRequest(DataTable dataTable) {
//
//        Map<String, String> row =
//                dataTable.asMaps(String.class, String.class).get(0);
//
//        BookingRequest request = new BookingRequest();
//
//        request.setRoomid(Integer.parseInt(row.get("roomid")));
//        request.setFirstname(row.get("firstname"));
//        request.setLastname(row.get("lastname"));
//        request.setDepositpaid(Boolean.parseBoolean(row.get("depositpaid")));
//        request.setEmail(row.get("email"));
//        request.setPhone(row.get("phone"));
//
//        BookingDates bookingDates =
//                new BookingDates(row.get("checkin"), row.get("checkout"));
//
//        request.setBookingdates(bookingDates);
//
//        return request;
//    }
}