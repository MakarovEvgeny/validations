SELECT
  veo.validation_id AS v_id,
  e.entity_id       AS e_id,
  e.name            AS e_name,
  e.description     AS e_description,
  e.version         AS e_version,
  e.commentary      AS e_commentary,

  o.operation_id    AS o_id,
  o.name            AS o_name,
  o.description     AS o_description,
  o.version         AS o_version,
  o.commentary      AS o_commentary

FROM validation_entity_operation veo
  JOIN entity e ON veo.entity_id = e.entity_id
  JOIN operation o ON veo.operation_id = o.operation_id
WHERE veo.validation_id IN (:ids)