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

    public AuthSteps(TestContext testContext, AuthService authService) {
        this.testContext = testContext;
        this.authService = authService;
    }

    @Given("the user has valid credentials")
    public void theUserHasValidAdminCredentials() {

        AuthRequest request = new AuthRequest(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        testContext.setAuthRequest(request);
    }

    @Given("the user has credentials with username {string} and password {string}")
    public void theUserProvidesUsernameAndPassword(String username,
                                                   String password) {

        AuthRequest request =
                new AuthRequest(username, password);

        testContext.setAuthRequest(request);
    }

    @When("the user logs in")
    public void theUserSendsALoginRequest() {

        testContext.setResponse(
                authService.login(
                        testContext.getAuthRequest()
                )
        );
    }

    @When("the user attempts to authenticate using an unsupported request method")
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


    @Then("the user should be authenticated")
    public void verifySuccessAuthentication() {

        assertEquals(
                200,
                testContext.getResponse().statusCode()
        );
    }

    @Then("the authentication should fail")
    public void verifyFailureAuthentication() {

        assertEquals(
                401,
                testContext.getResponse().statusCode()
        );
    }






}