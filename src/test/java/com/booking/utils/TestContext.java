package com.booking.utils;

import com.booking.models.AuthRequest;
import com.booking.models.BookingRequest;
import io.restassured.response.Response;

public class TestContext {

    private AuthRequest authRequest;
    private BookingRequest bookingRequest;
    private Response response;
    private Integer bookingId;
    private String token;

    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    public void setAuthRequest(AuthRequest authRequest) {
        this.authRequest = authRequest;
    }

    public BookingRequest getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(BookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}