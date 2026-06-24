package com.booking.stepdefinitions;

import com.booking.models.AuthRequest;
import com.booking.services.AuthService;
import com.booking.services.BookingService;
import com.booking.utils.ConfigReader;
import com.booking.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrieveSteps {

    private final TestContext testContext;
    private final BookingService bookingService;
    private final AuthService authService;

    public RetrieveSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
        this.authService = new AuthService();
    }

    @Given("the user is authenticated")
    public void theUserIsAuthenticated() {
        generateAndStoreToken();
    }

    @When("the user retrieves the created booking")
    public void theUserRetrievesTheCreatedBooking() {

        testContext.setResponse(
                bookingService.getBooking(
                        testContext.getBookingId(),
                        testContext.getToken()
                )
        );
    }

    @When("the user performs {string} on booking {string}")
    public void theUserPerformsActionOnBooking(String action, String bookingId) {

        switch (action.toLowerCase()) {

            case "retrieve":
                testContext.setResponse(
                        bookingService.getBooking(
                                Integer.parseInt(bookingId),
                                testContext.getToken()
                        )
                );
                break;

            case "notoken":
                testContext.setResponse(
                        bookingService.getBooking(
                                Integer.parseInt(bookingId),
                                null
                        )
                );
                break;

            case "invalidtoken":
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

        assertEquals(row.get("roomid"),
                testContext.getResponse().jsonPath().getString("roomid"));

        assertEquals(row.get("firstname"),
                testContext.getResponse().jsonPath().getString("firstname"));

        assertEquals(row.get("lastname"),
                testContext.getResponse().jsonPath().getString("lastname"));

        assertEquals(row.get("depositpaid"),
                testContext.getResponse().jsonPath().getString("depositpaid"));

        assertEquals(row.get("email"),
                testContext.getResponse().jsonPath().getString("email"));

        assertEquals(row.get("phone"),
                testContext.getResponse().jsonPath().getString("phone"));

        assertEquals(row.get("checkin"),
                testContext.getResponse().jsonPath().getString("bookingdates.checkin"));

        assertEquals(row.get("checkout"),
                testContext.getResponse().jsonPath().getString("bookingdates.checkout"));
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
}