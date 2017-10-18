SELECT
  entity_id AS id,
  name,
  description,
  version,
  commentary
FROM entity
WHERE entity_id = :id