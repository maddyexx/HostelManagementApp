package com.example.firstfirebaseproject;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
public class CreatePaymentIntentTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String amount = params[0];
        String currency = params[1];

        try {
            ServerHandler serverHandler = new ServerHandler();
            return serverHandler.createPaymentIntent(amount, currency);
        } catch (IOException e) {
            Log.e(TAG, "Error communicating with the server: " + e.getMessage());
            return null;
        }
    }
    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                // Parse the response data
                JSONObject responseJson = new JSONObject(result);
                String clientSecret = responseJson.getString("clientSecret");

                // Use the clientSecret to complete the payment flow with the Stripe Android SDK
                // Implement your payment flow logic here
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing response JSON: " + e.getMessage());
            }
        } else {
            // Handle the error case
            // Implement error handling logic here
        }
    }
}
