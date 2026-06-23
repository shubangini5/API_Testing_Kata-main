package com.booking.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiClient {

    public Response post(String endpoint, Object requestBody) {

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public Response get(String endpoint) {

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
    }

    public Response put(String endpoint,
                        Object requestBody,
                        String token) {

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public Response patch(String endpoint,
                          Object requestBody,
                          String token) {

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    public Response delete(String endpoint,
                           String token) {

        return RestAssured
                .given()
                .cookie("token", token)
                .when()
                .delete(endpoint);
    }
}