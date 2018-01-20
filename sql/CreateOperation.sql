CREATE TABLE operation
(
	operation_id VARCHAR(50) NOT NULL
		CONSTRAINT operation_pkey
			PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(250),
	version SMALLINT DEFAULT 1 NOT NULL,
	commentary VARCHAR(500)
);