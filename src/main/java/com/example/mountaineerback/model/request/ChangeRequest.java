package com.example.mountaineerback.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRequest {
    private String username;
    private String trueName;
    private String email;
    private String password;
    private String newPassword ;

}
