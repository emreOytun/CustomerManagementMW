package com.emreoytun.customermanagementmw.dto.customer.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddRequest {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 20)
    private String userName;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    private String password;

}
