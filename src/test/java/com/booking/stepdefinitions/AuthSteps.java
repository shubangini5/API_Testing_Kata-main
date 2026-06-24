package com.booking.stepdefinitions;

import com.booking.models.AuthRequest;
import com.booking.services.AuthService;
import com.booking.utils.ConfigReader;
import com.booking.utils.TestContext;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class AuthSteps {

    private final TestContext testContext;
    private final AuthService authService;

    public AuthSteps(TestContext testContext) {
        this.testContext = testContext;
        this.authService = new AuthService();
    }

    @Given("the user has valid admin credentials")
    public void theUserHasValidAdminCredentials() {

        AuthRequest request = new AuthRequest(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        testContext.setAuthRequest(request);
    }

    @Given("the user provides username {string} and password {string}")
    public void theUserProvidesUsernameAndPassword(String username,
                                                   String password) {

        AuthRequest request =
                new AuthRequest(username, password);

        testContext.setAuthRequest(request);
    }

    @When("the user sends a login request")
    public void theUserSendsALoginRequest() {

        testContext.setResponse(
                authService.login(
                        testContext.getAuthRequest()
                )
        );
    }

    @When("the user sends a GET request to the login endpoint")
    public void theUserSendsAGetRequestToTheLoginEndpoint() {

        testContext.setResponse(
                authService.loginUsingGet()
        );
    }

    @Then("the response contains an authentication token")
    public void theResponseContainsAnAuthenticationToken() {

        String token =
                testContext.getResponse()
                        .jsonPath()
                        .getString("token");

        assertNotNull(
                token,
                "Authentication token should not be null"
        );

        assertFalse(
                token.isEmpty(),
                "Authentication token should not be empty"
        );

        testContext.setToken(token);
    }

    @Then("the error message contains {string}")
    public void theErrorMessageContains(String expectedMessage) {

        String actualResponse =
                testContext.getResponse()
                        .asString();

        assertTrue(
                actualResponse.contains(expectedMessage),
                "Expected: " + expectedMessage +
                        " but actual response was: " + actualResponse
        );
    }

}