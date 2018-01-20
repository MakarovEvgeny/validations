CREATE TABLE validation_entity
(
	validation_id VARCHAR(7) NOT NULL
		CONSTRAINT validation_fk
			REFERENCES validation,
	entity_id VARCHAR(50) NOT NULL
		CONSTRAINT entity_fk
			REFERENCES entity,
	CONSTRAINT validation_entity_pkey
		PRIMARY KEY (validation_id, entity_id)
);