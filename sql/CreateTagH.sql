CREATE TABLE tag_h
(
    tag_version_id SMALLSERIAL NOT NULL
        CONSTRAINT tag_h_pkey
        PRIMARY KEY,
    tag_id VARCHAR(50) NOT NULL,
    name VARCHAR(30) NOT NULL,
    version SMALLINT NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    commentary VARCHAR(500) NOT NULL,
    description VARCHAR(500) NOT NULL,
    username VARCHAR(50) NOT NULL
        CONSTRAINT users_fk
        REFERENCES users,
    ip CIDR NOT NULL
);
