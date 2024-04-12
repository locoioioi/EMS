package com.employee_management_system.EMS.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}
