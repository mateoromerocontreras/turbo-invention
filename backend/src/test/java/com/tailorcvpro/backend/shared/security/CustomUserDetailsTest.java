package com.tailorcvpro.backend.shared.security;

import com.tailorcvpro.backend.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomUserDetailsTest {

    @Test
    @DisplayName("Should assign ROLE_ADMIN when user is superuser")
    void shouldReturnAdminAuthorityWhenSuperuserIsTrue() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setIsSuperuser(true);
        // WHEN
        CustomUserDetails result = new CustomUserDetails(user);
        List<GrantedAuthority> authorities = new ArrayList<>(result.getAuthorities());
        // THEN
        assertThat(authorities).containsExactly(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    @DisplayName("When user.setIsSuperuser(false) (or null), assert that authorities contain \"ROLE_USER\"")
    void shouldReturnUserAuthorityWhenSuperuserIsFalse() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setIsSuperuser(false);
        // WHEN
        CustomUserDetails result = new CustomUserDetails(user);
        List<GrantedAuthority> authorities = new ArrayList<>(result.getAuthorities());
        // THEN
        assertThat(authorities).containsExactly(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    @DisplayName("When user.setIsActive(true) it should return isEnable() true")
    void shouldReturnEnable() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setIsActive(true);
        // WHEN
        CustomUserDetails customerUserDetails = new CustomUserDetails(user);
        boolean result = customerUserDetails.isEnabled();
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When user.setIsActive(false) it should return isEnable() false")
    void shouldReturnEnableFalse() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setIsActive(false);
        // WHEN
        CustomUserDetails customerUserDetails = new CustomUserDetails(user);
        boolean result = customerUserDetails.isEnabled();
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("When user.setIsActive(true) it should return isCredentialsNonExpired() true")
    void shouldReturnisCredentialsNonExpired() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setIsActive(true);
        // WHEN
        CustomUserDetails customerUserDetails = new CustomUserDetails(user);
        boolean result = customerUserDetails.isCredentialsNonExpired();
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When user.setIsActive(false) it should return isCredentialsNonExpired() false")
    void shouldReturnisCredentialsNonExpiredFalse() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setIsActive(false);
        // WHEN
        CustomUserDetails customerUserDetails = new CustomUserDetails(user);
        boolean result = customerUserDetails.isCredentialsNonExpired();
        assertThat(result).isFalse();
    }
}
