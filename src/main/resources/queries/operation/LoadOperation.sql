SELECT
  operation_id AS id,
  name,
  description,
  version,
  commentary
FROM operation
WHERE operation_id = :id