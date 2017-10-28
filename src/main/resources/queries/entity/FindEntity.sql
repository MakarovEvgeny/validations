SELECT * FROM (
  SELECT
    entity_id AS id,
    name,
    description,
    version,
    commentary
  FROM entity
) wrapper