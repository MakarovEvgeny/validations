SELECT
  e.entity_id    AS e_id,
  e.name         AS e_name,
  e.description  AS e_description,
  e.version      AS e_version,
  e.commentary   AS e_commentary,

  o.operation_id AS o_id,
  o.name         AS o_name,
  o.description  AS o_description,
  o.version      AS o_version,
  o.commentary   AS o_commentary

FROM validation_entity_operation_h veo
  JOIN entity_h e ON veo.entity_version_id = e.entity_version_id
  JOIN operation_h o ON veo.operation_version_id = o.operation_version_id
WHERE veo.validation_version_id = :id