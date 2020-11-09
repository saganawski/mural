package com.chicago.mural.securtiy.service;

import com.chicago.mural.securtiy.UserPrincipal;
import com.chicago.mural.user.User;
import com.chicago.mural.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        final UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}
