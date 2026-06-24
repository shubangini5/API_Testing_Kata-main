package com.booking.client;

import com.booking.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private final RequestSpecification baseSpec;

    public ApiClient(){
        this.baseSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("base.url"))
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

    }

    public Response post(String endpoint, Object requestBody) {

        if (requestBody == null) {
            return RestAssured
                    .given()
                    .spec(baseSpec)
                    .when()
                    .post(endpoint);
        }

        return RestAssured
                .given()
                .spec(baseSpec)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public Response get(String endpoint) {

        return RestAssured
                .given()
                .spec(baseSpec)
                .when()
                .get(endpoint);
    }

    public Response get(String endpoint, String token) {

        if (token == null || token.trim().isEmpty()) {
            return RestAssured
                    .given()
                    .spec(baseSpec)
                    .when()
                    .get(endpoint);
        }

        return RestAssured
                .given()
                .spec(baseSpec)
                .header("Cookie", "token=" + token)
                .when()
                .get(endpoint);
    }

    public Response put(String endpoint, Object requestBody, String token) {

        return RestAssured
                .given()
                .spec(baseSpec)
                .header("Cookie", "token=" + token)
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public Response patch(String endpoint, Object requestBody, String token) {

        return RestAssured
                .given()
                .spec(baseSpec)
                .header("Cookie", "token=" + token)
                .body(requestBody)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response patch(String endpoint) {

        return RestAssured
                .given()
                .spec(baseSpec)
                .when()
                .patch(endpoint);
    }

    public Response delete(String endpoint, String token) {

        return RestAssured
                .given()
                .spec(baseSpec)
                .header("Cookie", "token=" + token)
                .when()
                .delete(endpoint);
    }
}