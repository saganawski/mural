CREATE TABLE mural_image_upload (
    id int NOT NULL AUTO_INCREMENT,
    mural_id int(11) DEFAULT NULL,
    user_id int(11) DEFAULT NULL,
	aws_key varchar(512) default null,
	aws_bucket_name varchar(254) default null,
	likes int default null,
	aws_url varchar(1200) DEFAULT NULL,
    created_by int(11) DEFAULT '-1',
    created_date datetime DEFAULT CURRENT_TIMESTAMP,
    updated_by int(11) DEFAULT '-1',
    updated_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (mural_id) REFERENCES mural(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);