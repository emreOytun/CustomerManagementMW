package com.emreoytun.customermanagementmw.service.security;

import com.emreoytun.customermanagementdata.dto.user.UserDto;
import com.emreoytun.customermanagementmw.consumers.UserConsumer;
import com.emreoytun.customermanagementmw.helper.HttpStatusChecker;
import com.emreoytun.customermanagementmw.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserConsumer userConsumer;
    private final Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<UserDto> userResponse = userConsumer.getUserByUsername(username);
        if (!HttpStatusChecker.checkIfHttpOk(userResponse.getStatusCode())) {
            logger.error("There is an error while loading the username: " + username);
            throw new UsernameNotFoundException("Username not found : " + username);
        }

        UserDto user = userResponse.getBody();
        if (user == null) {
            logger.error("Username not found : " + username);
            throw new UsernameNotFoundException("Username not found : " + username);
        }

        return new SecurityUser(user);
    }
}
