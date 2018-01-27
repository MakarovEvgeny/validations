CREATE TABLE operation_h
(
	operation_version_id SMALLSERIAL NOT NULL
		CONSTRAINT operation_h_pkey
			PRIMARY KEY,
	operation_id VARCHAR(50) NOT NULL,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(250),
	version SMALLINT NOT NULL,
	date TIMESTAMP WITH TIME ZONE NOT NULL,
	commentary VARCHAR(500),
	username VARCHAR(50) NOT NULL,
	ip CIDR NOT NULL
);