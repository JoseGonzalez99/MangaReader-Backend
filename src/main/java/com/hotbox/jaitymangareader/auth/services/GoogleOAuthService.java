package com.hotbox.jaitymangareader.auth.services;

import com.hotbox.jaitymangareader.user.entity.AppUser;
import com.hotbox.jaitymangareader.user.entity.UserContext;
import com.hotbox.jaitymangareader.user.repository.AppUserRepository;
import com.hotbox.jaitymangareader.user.repository.UserContextRepository;
import com.hotbox.jaitymangareader.config.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final AppUserRepository userRepository;
    private final UserContextRepository contextRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser processOAuthUser(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String fullName = (String) attributes.get("name");
        String pictureUrl = (String) attributes.get("picture");

        return userRepository.findByEmail(email).orElseGet(() -> {
            AppUser newUser = AppUser.builder()
                    .email(email)
                    .password(passwordEncoder.encode("oauth")) // no se usará
                    .role(Role.CLIENT)
                    .enabled(true)
                    .createdAt(Instant.now())
                    .fullName(fullName)
                    .photoUrl(pictureUrl)
                    .build();

            AppUser saved = userRepository.save(newUser);

             UserContext userContext= new UserContext();
             userContext.setUserId(saved.getId());
            contextRepository.save(userContext); // inicializa contexto

            return saved;
        });
    }
}
