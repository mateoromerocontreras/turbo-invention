package com.tailorcvpro.backend.shared.security;

import com.tailorcvpro.backend.user.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
        // THEN
        assertThat(tokenGenerated).isNotNull();
        assertThat(tokenGenerated).isNotEmpty();
    }

    @Test
    @DisplayName("Should get email when extract user name receives a valid token")
    public void testExtractUsername() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        // WHEN
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String tokenGenerated = jwtTokenProvider.generateToken(customUserDetails);
        String username = jwtTokenProvider.extractUsername(tokenGenerated);
        //THEN
        assertThat(username).isEqualTo("test@mail.com");
    }

    @Test
    @DisplayName("Should return true when isTokenValid receives valid token and user")
    public void testIsTokenValid() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        // WHEN
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String tokenGenerated = jwtTokenProvider.generateToken(customUserDetails);
        boolean result = jwtTokenProvider.isTokenValid(tokenGenerated, customUserDetails);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false when isTokenValid receives invalid token and user")
    public void testIsTokenInvalid() {
        // GIVEN
        User userA = new User();
        userA.setId(1L);
        userA.setEmail("testA@mail.com");

        User userB = new User();
        userB.setId(2L);
        userB.setEmail("testB@mail.com");
        // WHEN
        CustomUserDetails customUserDetailsA = new CustomUserDetails(userA);
        CustomUserDetails customUserDetailsB = new CustomUserDetails(userB);
        String tokenGenerated = jwtTokenProvider.generateToken(customUserDetailsA);
        boolean result = jwtTokenProvider.isTokenValid(tokenGenerated, customUserDetailsB);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should throw an ExpiredJwtException with an expired token")
    public void testIsTokenExpired() {
        // GIVEN
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", -10000L);
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        // WHEN
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String tokenGenerated = jwtTokenProvider.generateToken(customUserDetails);
        // THEN
        assertThatThrownBy(() -> jwtTokenProvider.isTokenValid(tokenGenerated, customUserDetails))
                .isInstanceOf(ExpiredJwtException.class);
    }
}
