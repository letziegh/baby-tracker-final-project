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

    private final ParentRepository parentRepository;

    public CustomOAuth2UserService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // Print all attributes
        attributes.keySet().forEach(key ->
                        System.out.println("Attribute Key: " + key + ", Attribute Value: " + attributes.get(key)));

        // Extract user details
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String username = (String) attributes.get("login"); // GitHub username

        // Use GitHub username if email is missing
        if (email == null || email.isEmpty()) {
            email = username + "@babytracker.com"; // Fake email for GitHub users
        }

        if (name == null || name.isEmpty()) {
            name = username; // Use GitHub username if name is missing
        }
        if(email == null || email.trim().isEmpty()){
            throw new RuntimeException("Email value cannot be null or empty");
        }
//        if (email == null || email.isEmpty()) {
//            throw new RuntimeException("Google OAuth2 did not return an email.");
//        }
        // Check if user exists in DB, otherwise create it
        String finalEmail = email;
        String finalName = name;
        Parent parent = parentRepository.findByEmail(email)
                .orElseGet(() -> parentRepository.save(new Parent(finalEmail, finalName)));


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "login"
        );
    }
}
