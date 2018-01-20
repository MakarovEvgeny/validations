CREATE TABLE message_h
(
	message_version_id SMALLSERIAL NOT NULL
		CONSTRAINT message_h_pkey
			PRIMARY KEY,
	message_id VARCHAR(4) NOT NULL,
	text TEXT,
	version SMALLINT NOT NULL,
	date TIMESTAMP WITH TIME ZONE NOT NULL,
	commentary VARCHAR(500),
	username VARCHAR(50),
	ip CIDR
);