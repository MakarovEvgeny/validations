SELECT
  e.entity_id   AS id,
  e.name        AS name,
  e.description AS description,
  e.version     AS version,
  e.commentary  AS commentary
FROM entity_h e
  JOIN validation_entity_h ve ON e.entity_version_id = ve.entity_version_id
WHERE ve.validation_version_id = :id
