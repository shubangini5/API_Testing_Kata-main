package com.booking.services;

import com.booking.client.ApiClient;
import com.booking.constants.Endpoints;
import com.booking.models.BookingRequest;
import io.restassured.response.Response;

public class BookingService {

    private final ApiClient apiClient;

    public BookingService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response createBooking(BookingRequest request) {
        return apiClient.post(Endpoints.BOOKING, request);
    }

    public Response createBookingUsingGet() {
        return apiClient.get(Endpoints.BOOKING);
    }

    public Response createBookingUsingPatch() {
        return apiClient.patch(Endpoints.BOOKING);
    }

    public Response getBooking(Integer bookingId,
                               String token) {

        return apiClient.get(
                Endpoints.BOOKING + "/" + bookingId,
                token
        );
    }

    public Response retrieveBookingUsingPost() {

        return apiClient.post(
                Endpoints.BOOKING + "/1",
                null
        );
    }
    public Response updateBooking(Integer bookingId,
                                  BookingRequest request,
                                  String token) {

        return apiClient.put(
                Endpoints.BOOKING + "/" + bookingId,
                request,
                token
        );
    }
    public Response partialUpdateBooking(Integer bookingId,
                                         Object request,
                                         String token) {

        return apiClient.patch(
                Endpoints.BOOKING + "/" + bookingId,
                request,
                token
        );
    }
    public Response deleteBooking(Integer bookingId,
                                  String token) {

        return apiClient.delete(
                Endpoints.BOOKING + "/" + bookingId,
                token
        );
    }
}
