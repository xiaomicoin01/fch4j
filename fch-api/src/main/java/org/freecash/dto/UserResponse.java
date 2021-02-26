package org.freecash.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserResponse {
    private String username;
    private String address;
    private String cidAddress;
}
