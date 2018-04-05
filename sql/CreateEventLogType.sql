CREATE TABLE eventlogtype
(
    eventlogtype_id SMALLINT NOT NULL
        CONSTRAINT eventlogtype_pkey
        PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO eventlogtype(eventlogtype_id, name) values
    (1, 'Поиск'),
    (2, 'Создание'),
    (3, 'Обновление'),
    (4, 'Удаление'),
    (5, 'Отчет');