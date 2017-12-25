SELECT
  validation_version_id AS id,
  date,
  username,
  commentary
FROM validation_h
WHERE validation_id = :id
ORDER BY date