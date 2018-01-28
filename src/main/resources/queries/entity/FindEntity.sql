SELECT * FROM (
  SELECT
    entity_id AS id,
    name,
    description,
    version,
    commentary
  FROM entity ORDER BY entity_id
) wrapper