package com.emreoytun.customermanagementmw.cache;

import com.emreoytun.customermanagementmw.security.SecurityUser;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserCache implements Serializable {
    private static final long serialVersionUID = 6700488849764243266L;

    private UserDetails userDetails;
    private Date expireTime;
}
