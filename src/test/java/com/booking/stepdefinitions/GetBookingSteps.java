package com.booking.stepdefinitions;

import com.booking.models.AuthRequest;
import com.booking.models.BookingResponse;
import com.booking.services.AuthService;
import com.booking.services.BookingService;
import com.booking.utils.ConfigReader;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Step definitions for retrieve booking scenarios.
public class GetBookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;
    private final AuthService authService;

    public GetBookingSteps(TestContext testContext, BookingService bookingService, AuthService authService) {
        this.testContext = testContext;
        this.bookingService = bookingService;
        this.authService = authService;
    }

    @Given("the user is authenticated")
    public void theUserIsAuthenticated() {
        generateAndStoreToken();
    }

    @When("the user views the booking")
    public void theUserRetrievesTheCreatedBooking() {

        testContext.setResponse(
                bookingService.getBooking(
                        testContext.getBookingId(),
                        testContext.getToken()
                )
        );
    }

    @When("the user retrieves an existing booking ID {string} with {string} token")
    public void theUserPerformsActionOnBooking(String bookingId, String action) {

        switch (action.toLowerCase()) {
            case "no":
                testContext.setResponse(
                        bookingService.getBooking(
                                Integer.parseInt(bookingId),
                                null
                        )
                );
                break;

            case "invalid":
                testContext.setResponse(
                        bookingService.getBooking(
                                Integer.parseInt(bookingId),
                                "invalid-token"
                        )
                );
                break;

            default:
                throw new IllegalArgumentException("Unsupported action: " + action);
        }
    }

    @When("the user views the booking ID {string}")
    public void theUserRetrievesBooking(String bookingId) {
                testContext.setResponse(
                        bookingService.getBooking(
                                Integer.parseInt(bookingId),
                                testContext.getToken()
                        )
                );
    }

    @When("the user sends a POST request to the retrieve booking endpoint")
    public void theUserSendsAPostRequestToTheRetrieveBookingEndpoint() {
        testContext.setResponse(
                bookingService.retrieveBookingUsingPost()
        );
    }

    @Then("the booking details should match:")
    public void theBookingDetailsShouldMatch(DataTable dataTable) {

        List<Map<String, String>> expected =
                dataTable.asMaps(String.class, String.class);

        Map<String, String> row = expected.get(0);

        BookingResponse bookingResponse =
                testContext.getResponse()
                        .as(BookingResponse.class);

        assertEquals(row.get("roomid"),
                bookingResponse.getRoomid().toString());

        assertEquals(row.get("firstname"),
                bookingResponse.getFirstname());

        assertEquals(row.get("lastname"),
                bookingResponse.getLastname());

        assertEquals(row.get("depositpaid"),
                bookingResponse.getDepositpaid().toString());

        assertEquals(row.get("email"),
                bookingResponse.getEmail());

        assertEquals(row.get("phone"),
                bookingResponse.getPhone());

        assertEquals(row.get("checkin"),
                bookingResponse.getBookingdates().getCheckin());

        assertEquals(row.get("checkout"),
                bookingResponse.getBookingdates().getCheckout());
    }

    private void generateAndStoreToken() {

        String token =
                authService.login(
                                new AuthRequest(
                                        ConfigReader.getProperty("username"),
                                        ConfigReader.getProperty("password")
                                )
                        )
                        .jsonPath()
                        .getString("token");

        testContext.setToken(token);
    }



    @Then("the booking should not be found")
    public void denyDeletion() {
        int status = testContext.getResponse().statusCode();
        assertTrue(
                status >= 400 && status < 500,
                "Expected 4xx status but got " + status
        );
    }

    // Validates the API contract independently of field values.
    @And("the response matches with json schema {string}")
    public void theResponseMatchesWithJsonSchema(String schemaFileName) {
        testContext.getResponse().then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath
                        ("jsonSchema/" + schemaFileName));
    }



}