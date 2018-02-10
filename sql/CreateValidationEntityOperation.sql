CREATE TABLE validation_entity_operation
(
	validation_id VARCHAR(7) NOT NULL
		CONSTRAINT validation_fk
			REFERENCES validation,
	entity_id VARCHAR(50) NOT NULL
		CONSTRAINT entity_fk
			REFERENCES entity,
	operation_id VARCHAR(50) NOT NULL
		CONSTRAINT operation_fk
			REFERENCES operation,
	CONSTRAINT validation_entity_operation_pkey
		PRIMARY KEY (validation_id, entity_id, operation_id)
);