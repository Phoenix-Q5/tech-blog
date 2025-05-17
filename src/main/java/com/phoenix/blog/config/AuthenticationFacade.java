package com.phoenix.blog.config;

import com.phoenix.blog.domain.User;
import com.phoenix.blog.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UserRepository userRepository;

    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getPrincipal())) {
            throw new IllegalStateException("No authenticated user found");
        }

        // We stored the Google-subject (sub) as the principal in our JwtService
        String googleSub = auth.getName();

        return userRepository.findByGoogleSub(googleSub)
                .orElseThrow(() -> new IllegalStateException(
                        "User not found for googleSub=" + googleSub));
    }
}
