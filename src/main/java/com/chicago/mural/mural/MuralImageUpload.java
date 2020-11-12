package com.chicago.mural.mural;

import com.chicago.mural.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="mural_image_upload")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MuralImageUpload {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "mural_id")
    private Mural mural;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="aws_key")
    private String awsKey;

    @Column(name="aws_bucket_name")
    private String awsBucketName;

    @Column(name="likes")
    private Integer likes;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Override
    public String toString() {
        return "MuralImageUpload{" +
                "id=" + id +
                ", mural=" + mural.getId() +
                ", user=" + user +
                ", awsKey='" + awsKey + '\'' +
                ", awsBucketName='" + awsBucketName + '\'' +
                ", likes=" + likes +
                ", updatedBy=" + updatedBy +
                ", updatedDate=" + updatedDate +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                '}';
    }
}
