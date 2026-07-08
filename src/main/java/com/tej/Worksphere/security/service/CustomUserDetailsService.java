package com.tej.Worksphere.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        String[] authorities = user.getRoles()
                .stream()
                .map(role -> "ROLE_" + role.getName())
                .toArray(String[]::new);

        System.out.println("Role count = " + user.getRoles().size());

        user.getRoles().forEach(role ->
                System.out.println(role.getName()));        for (String authority : authorities) {
            System.out.println(authority);
        }

        System.out.println("Authorities returned to Spring:");
org.springframework.security.core.userdetails.User springUser =
        (org.springframework.security.core.userdetails.User)
        org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

springUser.getAuthorities().forEach(a ->
        System.out.println(a.getAuthority()));
        
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}