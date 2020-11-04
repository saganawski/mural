CREATE SCHEMA IF NOT EXISTS mural_directory;

CREATE TABLE mural (
    id int NOT NULL AUTO_INCREMENT,
    mural_registration_id int DEFAULT NULL UNIQUE,
	artist_credit varchar(254) default null,
	artwork_title varchar(254) default null,
	media varchar(254) default null,
	year_installed varchar(254) default null,
	year_restored varchar(254) default null,
	location_description varchar(254) default null,
    street_address varchar(254) default null,
    zip varchar(254) default null,
    ward varchar(254) default null,
    community_area_number varchar(254) default null,
    affiliated_or_commissioning varchar(254) default null,
    description_of_artwork varchar(4000) default null,
    latitude varchar(254) default null,
    longitude varchar(254) default null,
    created_by int(11) DEFAULT '-1',
    created_date datetime DEFAULT CURRENT_TIMESTAMP,
    updated_by int(11) DEFAULT '-1',
    updated_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);