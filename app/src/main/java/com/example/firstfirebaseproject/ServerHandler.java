package com.example.firstfirebaseproject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class ServerHandler {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String SERVER_URL = "http://127.0.0.1:8000/create-payment-intent/"; // Replace with your server URL

    private OkHttpClient client = new OkHttpClient();

    public String createPaymentIntent(String amount, String currency) throws IOException {
        // Create JSON payload
        String json = "{\"amount\": \"" + amount + "\", \"currency\": \"" + currency + "\"}";
        RequestBody body = RequestBody.create(JSON, json);

        // Create request
        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(body)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Retrieve the response data
                String responseData = response.body().string();
                return responseData;
            } else {
                throw new IOException("Unexpected response code: " + response.code());
            }
        }
    }
}
