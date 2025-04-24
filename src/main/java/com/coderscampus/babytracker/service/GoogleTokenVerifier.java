// package com.coderscampus.babytracker.service;


// import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
// import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
// import com.google.api.client.http.javanet.NetHttpTransport;
// import com.google.api.client.json.JsonFactory;
// import com.google.api.client.json.gson.GsonFactory;

// import java.util.Collections;

// public class GoogleTokenVerifier {

//     private static final String GOOGLE_ISSUER = "https://accounts.google.com";
//     private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

//     public static GoogleIdToken.Payload verifyToken(String idToken, String clientId) {
//         try {
//             GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
//                     .setAudience(Collections.singletonList(clientId)) // Verify Client ID
//                     .setIssuer(GOOGLE_ISSUER) // Verify Google as the issuer
//                     .build();

//             GoogleIdToken googleIdToken = verifier.verify(idToken);
//             if (googleIdToken != null) {
//                 return googleIdToken.getPayload(); // Extract user info
//             } else {
//                 System.out.println("Invalid ID Token");
//             }
//         } catch (Exception e) {
//             System.out.println("Error verifying Google ID token: " + e.getMessage());
//         }
//         return null;
//     }
// }

