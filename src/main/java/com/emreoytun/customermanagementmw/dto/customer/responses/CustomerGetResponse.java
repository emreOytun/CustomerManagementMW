package com.emreoytun.customermanagementmw.dto.customer.responses;

import com.emreoytun.customermanagementmw.entities.concretes.Customer;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerGetResponse {

    private int id;
    private String userName;
    private String firstName;
    private String lastName;
}


