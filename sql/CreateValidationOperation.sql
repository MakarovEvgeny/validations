CREATE TABLE validation_operation
(
	validation_id VARCHAR(7) NOT NULL
		CONSTRAINT validatioin_fk
			REFERENCES validation,
	operation_id VARCHAR(50) NOT NULL
		CONSTRAINT operation_fk
			REFERENCES operation,
	CONSTRAINT validation_operation_pkey
		PRIMARY KEY (validation_id, operation_id)
);