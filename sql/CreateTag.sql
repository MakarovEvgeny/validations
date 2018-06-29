CREATE TABLE tag
(
    tag_id VARCHAR(50) NOT NULL
        CONSTRAINT tag_pkey
        PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    version SMALLINT DEFAULT 1 NOT NULL,
    description VARCHAR(500) NOT NULL,
    commentary VARCHAR(500) NOT NULL
);
