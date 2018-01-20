CREATE TABLE severity
(
	severity_id SMALLINT NOT NULL
		CONSTRAINT severity_pkey
			PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

INSERT INTO severity(severity_id, name) values(1, 'Ошибка'), (2, 'Предупреждение');