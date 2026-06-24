package com.booking.stepdefinitions;

import com.booking.models.BookingDates;
import com.booking.models.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.DataMapper;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public BookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    @When("the user creates a booking with:")
    public void theUserCreatesABookingWith(DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

        testContext.setBookingRequest(request);

        Response response =
                bookingService.createBooking(request);

        testContext.setResponse(response);
    }

    @When("the user creates a booking with invalid data:")
    public void theUserCreatesABookingWithInvalidData(
            DataTable dataTable) {

        BookingRequest request =
                mapInvalidBookingRequest(dataTable);

        Response response =
                bookingService.createBooking(request);

        testContext.setResponse(response);
    }

    @When("the user sends a PATCH request to the create booking endpoint")
    public void theUserSendsAPatchRequestToTheCreateBookingEndpoint() {

        Response response =
                bookingService.createBookingUsingPatch();

        testContext.setResponse(response);
    }

    @Then("the booking should be created successfully")
    public void theBookingShouldBeCreatedSuccessfully() {

        assertEquals(
                201,
                testContext.getResponse().statusCode(),
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

    @Then("the booking id should be generated")
    public void bookingIdShouldBeGenerated() {

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
//
//    private BookingRequest mapBookingRequest(
//            DataTable dataTable) {
//
//        Map<String, String> row =
//                dataTable.asMaps(
//                                String.class,
//                                String.class)
//                        .get(0);
//
//        BookingRequest request = DataMapper.mapToBookingRequest(row);

//
//        request.setRoomid(
//                Integer.parseInt(
//                        row.get("roomid")));
//
//        request.setFirstname(
//                row.get("firstname"));
//
//        request.setLastname(
//                row.get("lastname"));
//
//        request.setDepositpaid(
//                Boolean.parseBoolean(
//                        row.get("depositpaid")));
//
//        request.setEmail(
//                row.get("email"));
//
//        request.setPhone(
//                row.get("phone"));
//
//        BookingDates bookingDates =
//                new BookingDates(
//                        row.get("checkin"),
//                        row.get("checkout"));
//
//        request.setBookingdates(
//                bookingDates);
//
//        return request;
//    }

    private BookingRequest mapInvalidBookingRequest(
            DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);
//
//        if (!"[null]".equals(row.get("roomid"))) {
//            request.setRoomid(
//                    Integer.parseInt(
//                            row.get("roomid")));
//        }
//
//        request.setFirstname(
//                "[empty]".equals(row.get("firstname"))
//                        ? ""
//                        : row.get("firstname"));
//
//        request.setLastname(
//                "[empty]".equals(row.get("lastname"))
//                        ? ""
//                        : row.get("lastname"));
//
//        request.setDepositpaid(
//                Boolean.parseBoolean(
//                        row.get("depositpaid")));
//
//        request.setEmail(
//                "[empty]".equals(row.get("email"))
//                        ? ""
//                        : row.get("email"));
//
//        request.setPhone(
//                "[empty]".equals(row.get("phone"))
//                        ? ""
//                        : row.get("phone"));
//
//        BookingDates bookingDates =
//                new BookingDates();
//
//        if (!"[empty]".equals(row.get("checkin"))) {
//            bookingDates.setCheckin(
//                    row.get("checkin"));
//        }
//
//        if (!"[empty]".equals(row.get("checkout"))) {
//            bookingDates.setCheckout(
//                    row.get("checkout"));
//        }
//
//        request.setBookingdates(
//                bookingDates);

        return request;
    }
}