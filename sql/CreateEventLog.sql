CREATE TABLE eventlog
(
    eventlog_id VARCHAR(25) NOT NULL
        CONSTRAINT eventlog_pkey
        PRIMARY KEY,
    eventlogtype_id SMALLINT NOT NULL
        CONSTRAINT eventlogtype_fk
        REFERENCES eventlogtype,
    operation TEXT NOT NULL,
    request JSONB NOT NULL,
    response JSONB,
    commentary TEXT,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    username VARCHAR(50)
        CONSTRAINT users_fk
        REFERENCES users,
    ip CIDR NOT NULL
);