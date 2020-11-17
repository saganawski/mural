package com.chicago.mural.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String confirmPassword;
    private Boolean isActive;
    private String roles;
    private String permissions;
    private Integer updatedBy;
    private Date updatedDate;
    private Integer createdBy;
    private Date createdDate;

}
