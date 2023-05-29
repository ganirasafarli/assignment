package com.copaco.assignment.domian.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserDTO {
    private String username;
    private String password;
}
