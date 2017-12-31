SELECT
  e.entity_id   AS id,
  e.name        AS name,
  e.description AS description,
  e.version     AS version,
  e.commentary  AS commentary
FROM entity e
  JOIN validation_entity_h ve ON e.entity_id = ve.entity_id
WHERE ve.validation_version_id = :id
