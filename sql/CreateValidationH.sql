CREATE TABLE validation_h
(
	validation_version_id SMALLSERIAL NOT NULL
		CONSTRAINT validation_h_pkey
			PRIMARY KEY,
	validation_id VARCHAR(7) NOT NULL,
	date TIMESTAMP WITH TIME ZONE NOT NULL,
	message_version_id SMALLINT NOT NULL
	  CONSTRAINT message_fk
	    REFERENCES message_h,
	description TEXT NOT NULL,
	version SMALLINT NOT NULL,
	commentary VARCHAR(500),
	severity_id SMALLINT NOT NULL
	  CONSTRAINT severity_fk
	    REFERENCES severity,
	username VARCHAR(50) NOT NULL,
	ip CIDR NOT NULL
);