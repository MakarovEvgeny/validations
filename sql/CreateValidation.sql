CREATE TABLE validation
(
	validation_id VARCHAR(7) NOT NULL
		CONSTRAINT validation_pkey
			PRIMARY KEY,
	message_id VARCHAR(4) NOT NULL
		CONSTRAINT message_fk
			REFERENCES message,
	severity_id SMALLINT NOT NULL
		CONSTRAINT severity_fk
			REFERENCES severity,
	description TEXT NOT NULL,
	version SMALLINT DEFAULT 1 NOT NULL,
	commentary TEXT
);