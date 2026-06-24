package com.booking.stepdefinitions;

import com.booking.models.BookingDates;
import com.booking.models.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class BookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public BookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    @When("the user creates a booking with:")
    public void theUserCreatesABookingWith(DataTable dataTable) {

        BookingRequest request = mapBookingRequest(dataTable);

        testContext.setBookingRequest(request);

        Response response =
                bookingService.createBooking(request);

        testContext.setResponse(response);
    }

    @Then("the booking should be created successfully")
    public void theBookingShouldBeCreatedSuccessfully() {

        assertEquals(
                testContext.getResponse().statusCode(),
                201,
                "Booking was not created successfully"
        );

        Integer bookingId =
                testContext.getResponse()
                        .jsonPath()
                        .getInt("bookingid");

        assertNotNull(
                bookingId,
                "Booking ID was not generated"
        );

        testContext.setBookingId(bookingId);
    }

    private BookingRequest mapBookingRequest(DataTable dataTable) {

        Map<String, String> row =
                dataTable.asMaps(String.class, String.class)
                        .get(0);

        BookingRequest request = new BookingRequest();

        request.setRoomid(
                Integer.parseInt(row.get("roomid")));

        request.setFirstname(
                row.get("firstname"));

        request.setLastname(
                row.get("lastname"));

        request.setDepositpaid(
                Boolean.parseBoolean(
                        row.get("depositpaid")));

        request.setEmail(
                row.get("email"));

        request.setPhone(
                row.get("phone"));

        BookingDates bookingDates =
                new BookingDates(
                        row.get("checkin"),
                        row.get("checkout"));

        request.setBookingdates(
                bookingDates);

        return request;
    }
}