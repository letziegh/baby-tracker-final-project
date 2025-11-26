package com.coderscampus.babytracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private ParentService parentService;

    @Mock
    private org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService delegate;

    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    private ClientRegistration buildClientRegistration(String registrationId) {
        return ClientRegistration.withRegistrationId(registrationId)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId("client-id")
                .authorizationUri("https://example.com/auth")
                .tokenUri("https://example.com/token")
                .redirectUri("https://example.com/redirect")
                .scope("email")
                .userInfoUri("https://example.com/userinfo")
                .userNameAttributeName("id")
                .build();
    }

    @Test
    void loadUser_shouldHandleGoogleUserAndSaveParent() {
        ClientRegistration registration = buildClientRegistration("google");
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", "google@example.com");
        attributes.put("name", "Google User");
        attributes.put("sub", "google-sub");

        OAuth2User delegateUser = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "sub"
        );

        OAuth2UserRequest request = new OAuth2UserRequest(registration, null);

        // We cannot directly mock the internal DefaultOAuth2UserService usage,
        // but we can still exercise the branching logic by calling the method
        // and asserting parentService interaction and returned attributes.

        // Call method
        OAuth2User result = customOAuth2UserService.loadUser(request);

        // saveParent should be called with email and name
        verify(parentService).saveParent("google@example.com", "Google User");

        assertThat(result.getAttributes().get("email")).isEqualTo("google@example.com");
    }

    @Test
    void loadUser_shouldThrowWhenEmailMissing() {
        ClientRegistration registration = buildClientRegistration("github");
        Map<String, Object> attributes = new HashMap<>();
        // No email, no login -> should fail

        OAuth2User delegateUser = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id"
        );

        OAuth2UserRequest request = new OAuth2UserRequest(registration, null);

        assertThatThrownBy(() -> customOAuth2UserService.loadUser(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email is required from OAuth2 provider");
    }
}


