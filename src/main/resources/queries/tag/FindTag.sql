SELECT * FROM (
  SELECT
    tag_id AS id,
    name,
    version,
    description,
    commentary
  FROM tag ORDER BY name
) wrapper
