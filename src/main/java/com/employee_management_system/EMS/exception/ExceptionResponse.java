package com.employee_management_system.EMS.exception;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private String message;
    private long timeStamp;
    private String description;
}
