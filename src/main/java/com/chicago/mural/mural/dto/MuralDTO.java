package com.chicago.mural.mural.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MuralDTO {
    private Integer id;
    private Integer mural_registration_id;
    private String artist_credit;
    private String artwork_title;
    private String media;
    private String year_installed;
    private String year_restored;
    private String location_description;
    private String street_address;
    private String zip;
    private String ward;
    private String community_area_number;
    private String affiliated_or_commissioning;
    private String description_of_artwork;
    private String latitude;
    private String longitude;

    private List<MuralImageUploadDto> muralImageUploads = new ArrayList<>();
}
