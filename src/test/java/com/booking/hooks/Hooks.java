package com.booking.hooks;

import com.booking.utils.ConfigReader;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hooks {

    @Before
    public void setup() {
        RestAssured.baseURI =
                ConfigReader.getProperty("base.url");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}