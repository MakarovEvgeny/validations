SELECT * FROM (
  SELECT
    operation_id AS id,
    name,
    description,
    version,
    commentary
  FROM operation ORDER BY operation_id
) wrapper