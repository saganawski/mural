package com.chicago.mural.mural.dto;

import com.chicago.mural.user.User;
import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MuralImageUploadDto {
    private Integer id;

    private User user;
    private String awsKey;
    private String awsBucketName;
    private Integer likes;
    private String awsUrl;
    private Integer updatedBy;
    private Date updatedDate;
    private Integer createdBy;
    private Date createdDate;

}
