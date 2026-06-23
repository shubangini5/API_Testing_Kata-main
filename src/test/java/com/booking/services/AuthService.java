package com.booking.services;

import com.booking.client.ApiClient;
import com.booking.constants.Endpoints;
import com.booking.models.AuthRequest;
import io.restassured.response.Response;

public class AuthService {

    private final ApiClient apiClient;

    public AuthService() {
        this.apiClient = new ApiClient();
    }

    public Response login(AuthRequest request) {
        return apiClient.post(
                Endpoints.AUTH_LOGIN,
                request
        );
    }
}