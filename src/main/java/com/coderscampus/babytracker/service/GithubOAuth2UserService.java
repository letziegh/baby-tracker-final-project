// package com.coderscampus.babytracker.service;

// import com.coderscampus.babytracker.domain.Parent;
// import com.coderscampus.babytracker.repository.ParentRepository;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.Map;

// @Service("GitHub")
// public class GithubOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

//     private final ParentRepository parentRepository;

//     public GithubOAuth2UserService(ParentRepository parentRepository) {
//         this.parentRepository = parentRepository;
//     }

//     @Override
//     @Transactional
//     public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//         OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//         Map<String, Object> attributes = oAuth2User.getAttributes();

//         String username = (String) attributes.get("login"); // GitHub username
//         String name = (String) attributes.get("name");
//         String email = (String) attributes.get("email");

//         if (email == null || email.isEmpty()) {
//             email = username + "@github.com"; // Generate fake email
//         }
//         if (name == null || name.isEmpty()) {
//             name = username; // Use GitHub username as fallback
//         }

//         // Save user if not exists
//         String finalEmail = email;
//         String finalName = name;
//         Parent parent = parentRepository.findByEmail(email)
//                 .orElseGet(() -> {
//                     Parent newParent = new Parent(finalEmail, finalName);
//                     try {
//                         System.out.println("Attempting to save new parent: Email=" + finalEmail + ", Name=" + finalName); // Before save
//                         Parent savedParent = parentRepository.save(newParent);
//                         System.out.println("Successfully saved new parent with ID: " + savedParent.getId()); // After save
//                         return savedParent;
//                     } catch (Exception e) {
//                         System.out.println("ERROR: Exception during parentRepository.save() for email: " + finalEmail); // Error message
//                         e.printStackTrace(); // Print stack trace
//                         throw new RuntimeException("Error saving parent information.", e); // Re-throw
//                     }
//                 });

//         return oAuth2User;
//     }
// }
// //        Parent parent = parentRepository.findByEmail(email)
// //                .orElseGet(() -> parentRepository.save(new Parent(finalEmail, finalName)));
// //
// //        return oAuth2User;
// //    }
// //}
