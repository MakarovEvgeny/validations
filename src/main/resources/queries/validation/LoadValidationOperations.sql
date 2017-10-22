SELECT
  o.operation_id AS id,
  o.name         AS name,
  o.description  AS description,
  o.version      AS version,
  o.commentary   AS commentary
FROM operation o
  JOIN validation_operation vo ON o.operation_id = vo.operation_id
WHERE vo.validation_id = :id