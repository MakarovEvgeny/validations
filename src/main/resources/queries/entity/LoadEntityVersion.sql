SELECT
  entity_id AS id,
  name,
  description,
  version,
  commentary
FROM entity_h
WHERE entity_version_id = :id