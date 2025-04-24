// package com.coderscampus.babytracker.service;

// import com.coderscampus.babytracker.domain.Parent;
// import com.coderscampus.babytracker.repository.ParentRepository;
// import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.Map;

// @Service("Google")
// public class GoogleOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

//     //  private final ParentRepository parentRepository;
//     private final ParentService parentService;


//     //    @Value("${spring.security.oauth2.client.registration.google.client-id}")
// //    private String GOOGLE_CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID"; // Replace with your actual Google Client ID
//     @Value("${spring.security.oauth2.client.registration.google.client-id}")
//     private String googleClientId;

//     //    public GoogleOAuth2Service(ParentRepository parentRepository) {
// //        this.parentRepository = parentRepository;
// //    }
//     public GoogleOAuth2Service(ParentService parentService) {
//         this.parentService = parentService;
//     }

//     @Override
//     public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//         OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//         Map<String, Object> attributes = oAuth2User.getAttributes();
//         // Get ID token safely
//         Object idTokenObj = userRequest.getAdditionalParameters().get("id_token");
//         String idToken = idTokenObj != null ? idTokenObj.toString() : null;

//         if (idToken == null) {
//             throw new RuntimeException("Google ID Token is missing.");
//         }

//         GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyToken(idToken, googleClientId);
//         if (payload == null) {
//             throw new RuntimeException("Invalid Google ID Token");
//         }

//         String email = payload.getEmail();
//         String name = (String) payload.get("name");

//         if (!payload.getEmailVerified()) {
//             throw new RuntimeException("Email not verified by Google.");
//         } 

//         System.out.println("Saving Google User: " + email + " - " + name);
//         parentService.saveParent(email, name);

//         return oAuth2User;
//     }
// }
// //        String idToken = userRequest.getAdditionalParameters().get("id_token").toString();
// //        GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyToken(idToken, googleClientId);
// //
// //        if (payload == null) {
// //            throw new RuntimeException("Invalid Google ID Token");
// //        }
// //
// //        String email = payload.getEmail();
// //        String name = (String) payload.get("name");
// //
// //        if (!payload.getEmailVerified()) {
// //            throw new RuntimeException("Email not verified by Google.");
// //        }
// //
// //        parentService.saveParent(email, name);
// //
// //        return oAuth2User;
// //    }
// //}

// //CODE BELOW WORKED BUT DIDNT SAVE USER INFO IN MYSQL
// //    @Override
// //    @Transactional
// //    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
// //        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
// //        Map<String, Object> attributes = oAuth2User.getAttributes();
// //
//         //String idToken = (String) attributes.get("id_token");
// //        String idToken = userRequest.getAdditionalParameters().get("id_token").toString();
// //
// //        GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyToken(idToken, googleClientId);

// //        if (payload == null) {
// //            System.out.println("ERROR: Invalid Google ID Token"); // System.out.println for error
// //
// //            throw new RuntimeException("Invalid Google ID Token");
// //        }
// //
// //        // Extract user details
// //        String email = payload.getEmail();
// //        boolean emailVerified = payload.getEmailVerified();
// //        String name = (String) payload.get("name");
// //        //String pictureUrl = (String) payload.get("picture");
// //
// //        if (!emailVerified) {
// //            System.out.println("ERROR: Email not verified by Google."); // System.out.println for error
// //
// //            throw new RuntimeException("Email not verified by Google.");
// //        }
// //        Parent parent = parentRepository.findByEmail(email)
// //                .orElseGet(() -> {
// //                    Parent newParent = new Parent(email, name);
// //                    System.out.println("Attempting to save new parent: Email=" + email + ", Name=" + name);
// //                    return parentRepository.save(newParent);
// //                });
// //        parentService.saveParent(email, name);
// //
// //        return oAuth2User;
// //    }
// //}
// //Below is code that worked previously but does not save google login info
// //        Parent parent = parentRepository.findByEmail(email)
// //                .orElseGet(() -> {
// //                    Parent newParent = new Parent(email, name);
// //                    try {
// //                        System.out.println("Attempting to save new parent: Email=" + email + ", Name=" + name); // Before save
// //                        Parent savedParent = parentRepository.save(newParent);
// //                        System.out.println("Successfully saved new parent with ID: " + savedParent.getId()); // After save
// //                        return savedParent;
// //                    } catch (Exception e) {
// //                        System.out.println("ERROR: Exception during parentRepository.save() for email: " + email); // Error message
// //                        e.printStackTrace(); // Print full stack trace to console (for debugging)
// //                        throw new RuntimeException("Error saving parent information.", e); // Re-throw
// //                    }
// //                });
// //
// //        attributes.forEach((key, value) -> System.out.println("OAuth2 Attribute: " + key + " -> " + value));

//     //    return oAuth2User;
// //    }
// //}
// //        // Check if user exists in DB, otherwise create it
// //        Parent parent = parentRepository.findByEmail(email)
// //                .orElseGet(() -> parentRepository.save(new Parent(email, name)));
// //
// //        attributes.forEach((key, value) -> System.out.println("OAuth2 Attribute: " + key + " -> " + value));
// //
// //        return oAuth2User;
// //
// //    }
// //}

// //    @Override
// //    @Transactional
// //    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
// //        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
// //        Map<String, Object> attributes = oAuth2User.getAttributes();
// //
// //        String email = (String) attributes.get("email");
// //        String name = (String) attributes.get("name");
// //        String pictureUrl = (String) attributes.get("picture");
// //
// //        if (email == null || email.isEmpty()) {
// //            throw new RuntimeException("Google OAuth2 did not return an email.");
// //        }
// //
// //        // Save user if not exists
// //        Parent parent = parentRepository.findByEmail(email)
// //                .orElseGet(() -> parentRepository.save(new Parent(email, name)));
// //
// //        return oAuth2User;
// //    }
// //}