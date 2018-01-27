CREATE TABLE message
(
	message_id VARCHAR(4) NOT NULL
		CONSTRAINT message_pkey
			PRIMARY KEY,
	text TEXT NOT NULL,
	version SMALLINT DEFAULT 1 NOT NULL,
	commentary VARCHAR(500)
);