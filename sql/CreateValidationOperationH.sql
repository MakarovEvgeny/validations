CREATE TABLE validation_operation_h
(
	validation_version_id SMALLINT NOT NULL
		CONSTRAINT validation_operation_h_fk
			REFERENCES validation_h,
	operation_id VARCHAR(50) NOT NULL,
	CONSTRAINT validation_operation_h_pkey
		PRIMARY KEY (validation_version_id, operation_id)
);