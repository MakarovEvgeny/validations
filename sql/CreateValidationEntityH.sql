CREATE TABLE validation_entity_h
(
	validation_version_id SMALLINT NOT NULL
		CONSTRAINT validation_operation_h_fk
			REFERENCES validation_h,
	entity_id VARCHAR(50) NOT NULL,
	CONSTRAINT validation_entity_h_pkey
		PRIMARY KEY (validation_version_id, entity_id)
);