package com.interviewtracker.service;

import com.interviewtracker.entity.User;
import com.interviewtracker.repository.UserRepository;
import com.interviewtracker.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class JwtService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public JwtService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return jwtUtil.isTokenValid(token, userDetails.getUsername());
    }
}
