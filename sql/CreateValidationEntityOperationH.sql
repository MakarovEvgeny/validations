CREATE TABLE validation_entity_operation_h
(
	validation_version_id SMALLINT NOT NULL
		CONSTRAINT validation_h_fk
			REFERENCES validation_h,
	entity_version_id SMALLINT NOT NULL
		CONSTRAINT entity_h_fk
			REFERENCES entity_h,
	operation_version_id SMALLINT NOT NULL
		CONSTRAINT operation_h_fk
			REFERENCES operation_h,
	CONSTRAINT validation_entity_operation_h_pkey
		PRIMARY KEY (validation_version_id, entity_version_id, operation_version_id)
);