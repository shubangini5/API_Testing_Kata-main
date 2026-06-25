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

public class BookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public BookingSteps(TestContext testContext, BookingService bookingService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
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
    public void theUserCreatesABookingWithInvalidData(DataTable dataTable) {
        createBooking(dataTable);
    }

    @When("the user tries to book the same room again on overlapping days")
    public void theUserCreatesABookingWithOverlappingDays(DataTable dataTable) {
        createBooking(dataTable);
    }

    @When("the user attempts to create a booking using an unsupported request method")
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

    @Then("the user should receive a booking ID")
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

    @Then("the booking should not be created")
    public void verifyBadBooking() {
        assertErrorStatus();
    }

    @Then("the booking request should be rejected")
    public void verifyConflictBooking() {
        assertErrorStatus();
    }

    private void assertErrorStatus() {
        int status = testContext.getResponse().statusCode();
        assertTrue(
                status >= 400 && status < 500,
                "Expected 4xx status but got " + status
        );
    }

    private void createBooking(DataTable dataTable) {

        BookingRequest request = DataMapper.mapToBookingRequest(dataTable);

        Response response =
                bookingService.createBooking(request);

        testContext.setResponse(response);
    }


}