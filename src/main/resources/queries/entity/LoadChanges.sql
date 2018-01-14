SELECT
  entity_version_id AS id,
  date,
  username,
  commentary
FROM entity_h
WHERE entity_id = :id
ORDER BY date