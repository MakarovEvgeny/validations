CREATE TABLE validation_h
(
	validation_version_id SMALLSERIAL NOT NULL
		CONSTRAINT validation_h_pkey
			PRIMARY KEY,
	validation_id VARCHAR(7) NOT NULL,
	date TIMESTAMP WITH TIME ZONE NOT NULL,
	message_id VARCHAR(4),
	description TEXT,
	version SMALLINT NOT NULL,
	commentary VARCHAR(500),
	severity_id SMALLINT NOT NULL,
	username VARCHAR(50),
	ip CIDR
);