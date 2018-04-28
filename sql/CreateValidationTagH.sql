CREATE TABLE validation_tag_h
(
    validation_version_id SMALLINT NOT NULL
        CONSTRAINT validation_h_fk
        REFERENCES validation_h,
    tag_version_id SMALLINT NOT NULL
        CONSTRAINT tag_h_fk
        REFERENCES tag_h,
    CONSTRAINT validation_tag_h_pkey
    PRIMARY KEY (validation_version_id, tag_version_id)
);
