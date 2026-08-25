package com.tailorcvpro.backend.shared.security;

import com.tailorcvpro.backend.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    // 256-bit Base64 secret key for testing
    private static final String TEST_SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long TEST_EXPIRATION_MS = 86400000L; // 24h

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();

        // Inject values into the private fields of jwtTokenProvider
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", TEST_EXPIRATION_MS);
    }


    @Test
    @DisplayName("Should generate token when valid user details are provided")
    public void testGenerateToken() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        // WHEN
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String tokenGenerated = jwtTokenProvider.generateToken(customUserDetails);
        assertThat(tokenGenerated).isNotNull();
        assertThat(tokenGenerated).isNotEmpty();
    }
}
