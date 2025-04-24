package com.coderscampus.babytracker.service;
import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.repository.ParentRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final ParentService parentService;

    public CustomOAuth2UserService(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String email = null;
        String name = null;
        String nameAttributeKey;
        
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            
            // For Google, use "sub" as the nameAttributeKey
            nameAttributeKey = "sub";
            
            // Print Google attributes for debugging
            System.out.println("Google attributes: " + attributes);
        } else if ("github".equals(registrationId)) {
            String login = (String) attributes.get("login");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            
            if (email == null) {
                email = login + "@github.com";
            }
            name = name != null ? name : login;
            nameAttributeKey = "login";
        } else {
            // For other providers, find a non-null attribute to use as the key
            nameAttributeKey = attributes.keySet().stream()
                    .filter(key -> attributes.get(key) != null)
                    .findFirst()
                    .orElse("id");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required from OAuth2 provider");
        }

        parentService.saveParent(email, name);
        
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                nameAttributeKey
        );
    }
}

// @Service
// public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

//     private final GoogleOAuth2Service googleOAuth2Service;
//     private final GithubOAuth2UserService githubOAuth2UserService;


//     public CustomOAuth2UserService(GoogleOAuth2Service googleOAuth2Service,
//                                    GithubOAuth2UserService githubOAuth2UserService) {
//         this.googleOAuth2Service = googleOAuth2Service;
//         this.githubOAuth2UserService = githubOAuth2UserService;
//     }

//     @Override
//     public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//         String provider = userRequest.getClientRegistration().getRegistrationId();

//         if ("google".equalsIgnoreCase(provider)) {
//             return googleOAuth2Service.loadUser(userRequest);
//         } else if ("github".equalsIgnoreCase(provider)) {
//             return githubOAuth2UserService.loadUser(userRequest);
//         } else {
//             throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
//         }
//     }
// }
//@Service
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final ParentRepository parentRepository;
//
//    public CustomOAuth2UserService(ParentRepository parentRepository) {
//        this.parentRepository = parentRepository;
//    }
//
//    @Override
//    @Transactional
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        // Print all attributes
//        attributes.keySet().forEach(key ->
//                        System.out.println("Attribute Key: " + key + ", Attribute Value: " + attributes.get(key)));
//
//        // Extract user details
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//        String username = (String) attributes.get("login"); // GitHub username
//
//        // Use GitHub username if email is missing
//        if (email == null || email.isEmpty()) {
//            email = username + "@babytracker.com"; // Fake email for GitHub users
//        }
//
//        if (name == null || name.isEmpty()) {
//            name = username; // Use GitHub username if name is missing
//        }
//        if(email == null || email.trim().isEmpty()){
//            throw new RuntimeException("Email value cannot be null or empty");
//        }
////        if (email == null || email.isEmpty()) {
////            throw new RuntimeException("Google OAuth2 did not return an email.");
////        }
//        // Check if user exists in DB, otherwise create it
//        String finalEmail = email;
//        String finalName = name;
//        Parent parent = parentRepository.findByEmail(email)
//                .orElseGet(() -> parentRepository.save(new Parent(finalEmail, finalName)));
//
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                attributes,
//                "login"
//        );
//    }
//}
