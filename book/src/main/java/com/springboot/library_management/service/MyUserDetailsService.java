
package com.springboot.library_management.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Value("${app.auth.username}")
    private String username;

    @Value("${app.auth.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String requestedUsername)
            throws UsernameNotFoundException {

        if (!username.equals(requestedUsername)) {
            throw new UsernameNotFoundException(
                    "User not found: " + requestedUsername);
        }

        return User.withUsername(username)
                .password(password)
                .authorities("USER")
                .build();
    }
}
