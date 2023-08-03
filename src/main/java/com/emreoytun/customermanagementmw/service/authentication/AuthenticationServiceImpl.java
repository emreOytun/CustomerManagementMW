package com.emreoytun.customermanagementmw.service.authentication;

import com.emreoytun.customermanagementdata.dto.authentication.request.AuthenticationRequest;
import com.emreoytun.customermanagementdata.dto.authentication.response.AuthenticationResponse;
import com.emreoytun.customermanagementdata.dto.authentication.request.RegisterRequest;
import com.emreoytun.customermanagementdata.entities.Customer;
import com.emreoytun.customermanagementdata.entities.Role;
import com.emreoytun.customermanagementdata.entities.User;
import com.emreoytun.customermanagementdata.repository.CustomerDao;
import com.emreoytun.customermanagementdata.repository.RoleDao;
import com.emreoytun.customermanagementdata.repository.UserDao;
import com.emreoytun.customermanagementdata.exceptions.CustomerBusinessRulesException;
import com.emreoytun.customermanagementmw.security.SecurityUser;
import com.emreoytun.customermanagementmw.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerDao customerDao;
    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleDao roleDao;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userDao.existsByUsername(request.getUsername())) {
            throw new CustomerBusinessRulesException("Username exists before");
        }

        Customer c = new Customer();
        c.setFirstName(request.getFirstName());
        c.setLastName(request.getLastName());
        c.getUser().setUsername(request.getUsername());
        c.getUser().setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleDao.findByName("CUSTOMER");
        c.getUser().setRoles(new HashSet<>());
        c.getUser().getRoles().add(role);

        customerDao.save(c);

        String jwtToken = jwtService.generateToken(new SecurityUser(c.getUser()));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        User user = new User();
        user.setUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(new SecurityUser(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
