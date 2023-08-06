package com.emreoytun.customermanagementmw.service.authentication;

import com.emreoytun.customermanagementdata.dto.authentication.request.AuthenticationRequest;
import com.emreoytun.customermanagementdata.dto.authentication.response.AuthenticationResponse;
import com.emreoytun.customermanagementdata.dto.authentication.request.RegisterRequest;
import com.emreoytun.customermanagementdata.dto.user.UserDto;
import com.emreoytun.customermanagementdata.exceptions.CustomException;
import com.emreoytun.customermanagementmw.consumers.CustomerConsumer;
import com.emreoytun.customermanagementmw.consumers.UserConsumer;
import com.emreoytun.customermanagementmw.security.SecurityUser;
import com.emreoytun.customermanagementmw.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerConsumer customerConsumer;
    private final UserConsumer userConsumer;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        ResponseEntity<Boolean> checkExistsResponse = customerConsumer.checkExists(request.getUsername());

        // Check if given username exists before.
        if (checkExistsResponse.getBody()) {
            throw new CustomException("Username exists before");
        }

        // Encode the password before inserting to the database.
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save customer
        customerConsumer.addCustomer(request);

        // Fetch customer with id
        ResponseEntity<UserDto> userResponse = userConsumer.getUserByUsername(request.getUsername());

        UserDto userDto = userResponse.getBody();
        if (userDto == null) {
            throw new CustomException("There is a problem with saving the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String jwtToken = jwtService.generateToken(new SecurityUser(userDto));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        ResponseEntity<UserDto> response = userConsumer.getUserByUsername(request.getUsername());
        UserDto userDto = response.getBody();

        var jwtToken = jwtService.generateToken(new SecurityUser(userDto));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
