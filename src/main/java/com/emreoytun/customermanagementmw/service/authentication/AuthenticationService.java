package com.emreoytun.customermanagementmw.service.authentication;

import com.emreoytun.customermanagementdata.dto.authentication.request.AuthenticationRequest;
import com.emreoytun.customermanagementdata.dto.authentication.response.AuthenticationResponse;
import com.emreoytun.customermanagementdata.dto.authentication.request.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
