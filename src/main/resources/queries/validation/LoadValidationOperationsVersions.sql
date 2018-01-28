SELECT
  o.operation_id AS id,
  o.name         AS name,
  o.description  AS description,
  o.version      AS version,
  o.commentary   AS commentary
FROM operation_h o
  JOIN validation_operation_h vo ON o.operation_version_id = vo.operation_version_id
WHERE vo.validation_version_id = :id