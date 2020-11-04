package com.chicago.mural.mural;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="mural",uniqueConstraints = {@UniqueConstraint(columnNames = {"mural_registration_id"})})
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Mural {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="mural_registration_id")
    private Integer mural_registration_id;

    @Column(name="artist_credit")
    private String artist_credit;

    @Column(name="artwork_title")
    private String artwork_title;

    @Column(name="media")
    private String media;

    @Column(name="year_installed")
    private String year_installed;

    @Column(name="year_restored")
    private String year_restored;

    @Column(name="location_description")
    private String location_description;

    @Column(name="street_address")
    private String street_address;

    @Column(name="zip")
    private String zip;

    @Column(name="ward")
    private String ward;

    @Column(name="community_area_number")
    private String community_area_number;

    @Column(name="affiliated_or_commissioning")
    private String affiliated_or_commissioning;

    @Column(name="description_of_artwork")
    private String description_of_artwork;

    @Column(name="latitude")
    private String latitude;

    @Column(name="longitude")
    private String longitude;
}
