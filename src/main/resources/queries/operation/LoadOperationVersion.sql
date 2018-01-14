SELECT
  operation_id AS id,
  name,
  description,
  version,
  commentary
FROM operation_h
WHERE operation_version_id = :id