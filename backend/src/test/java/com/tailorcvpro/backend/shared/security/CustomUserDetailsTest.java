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
        // GIVEN: User entity with isSuperuser = true
        User user = new User();
        user.setId(1L);
        user.setIsSuperuser(true);
        // WHEN: CustomUserDetails customUserDetails = new CustomUserDetails(user);
        CustomUserDetails result = new CustomUserDetails(user);
        List<GrantedAuthority> authorities = new ArrayList<>(result.getAuthorities());
        // THEN: Assert customUserDetails.getAuthorities() contains SimpleGrantedAuthority("ROLE_ADMIN")
        assertThat(authorities).containsExactly(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
