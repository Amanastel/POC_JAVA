package com.token.token;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

//    private static final String CLIENT_ID = "250134009581-jbve639tu19bbdb2ce779ert7j1n8vc2.apps.googleusercontent.com";
//    private static final String CLIENT_ID = "998445802997-8f6pk8bs81lq5kr6qrtlpgm45sf7p5rt.apps.googleusercontent.com";
    private static final String CLIENT_ID = "250134009581-jbve639tu19bbdb2ce779ert7j1n8vc2.apps.googleusercontent.com";
    @GetMapping("/verifyToken/{idTokenString}")
    public Map<String, Object> verifyToken(@PathVariable String idTokenString) {
        // Create a new instance of GsonFactory
        JsonFactory jsonFactory = new GsonFactory();

        // Create a new instance of NetHttpTransport
        HttpTransport transport = new NetHttpTransport();

        // Create a new instance of GoogleIdTokenVerifier
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        // Verify the idToken
        GoogleIdToken token;
        try {
            token = verifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException e) {
            // Token verification failed
            return Collections.singletonMap("error", "Token verification failed");
        }

        // Check if the token is null
        if (token == null) {
            // Token is invalid
            return Collections.singletonMap("error", "Invalid token");
        }

        // Extract user information
//        Payload payload = token.getPayload();
//        String userId = payload.getSubject();
//        String userEmail = payload.getEmail();
//
//        // Additional checks if needed
//        // Check userId and userEmail against your database
//        // Create a new user if needed
//        // Set up a session
//
//
//        // Return successful message
//        return "Token verified. User ID: " + userId + ", Email: " + userEmail;


//        // Extract user information
//        Payload payload = token.getPayload();
//
//        // Return the entire payload as a JSON object
//        return new Gson().toJson(payload);

        // Extract user information
        Payload payload = token.getPayload();

        // Convert the payload to a Map
        Map<String, Object> payloadMap = new HashMap<>();
        for (String claimName : payload.keySet()) {
            payloadMap.put(claimName, payload.get(claimName));
        }

        // Return the payload as a Map
        return payloadMap;
    }

//    @GetMapping("/verifyToken/{idTokenString}")
//    public String verifyToken(@PathVariable String idTokenString) {
//        System.out.println("idTokenString: "+idTokenString);
//
//        // Check if the idTokenString is null or empty
//        if (idTokenString == null || idTokenString.isEmpty()) {
//            return "Invalid token";
//        }
//        // Create a new instance of GsonFactory
//        JsonFactory jsonFactory = new GsonFactory();
//
//        // Create a new instance of NetHttpTransport
//        HttpTransport transport = new NetHttpTransport();
//
//        // Create a new instance of GoogleIdTokenVerifier
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                .setAudience(Collections.singletonList(CLIENT_ID))
//                .build();
//
//        // Verify the idToken
//        GoogleIdToken token;
//        try {
//            token = verifier.verify(idTokenString);
//        } catch (GeneralSecurityException | IOException e) {
//            // Token verification failed
//            return "Token verification failed";
//        }
//
//        // Check if the token is null
//        if (token == null) {
//            // Token is invalid
//            return "Invalid token";
//        }
//
//        // Extract user information
////        Payload payload = token.getPayload();
////        String userId = payload.getSubject();
////        String userEmail = payload.getEmail();
////
////        // Additional checks if needed
////        // Check userId and userEmail against your database
////        // Create a new user if needed
////        // Set up a session
////
////
////        // Return successful message
////        return "Token verified. User ID: " + userId + ", Email: " + userEmail;
//
//
//        // Extract user information
//        Payload payload = token.getPayload();
//
//        // Return the entire payload as a JSON object
//        return new Gson().toJson(payload);
//    }
}
