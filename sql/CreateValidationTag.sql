CREATE TABLE validation_tag
(
    validation_id VARCHAR(7) NOT NULL
        CONSTRAINT validation_fk
        REFERENCES validation,
    tag_id VARCHAR(50) NOT NULL
        CONSTRAINT entity_fk
        REFERENCES tag,
    CONSTRAINT validation_tag_pkey
    PRIMARY KEY (validation_id, tag_id)
);
