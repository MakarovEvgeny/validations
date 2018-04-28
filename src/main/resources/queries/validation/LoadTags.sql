SELECT
  vt.validation_id AS v_id,
  t.tag_id AS id,
  t.name,
  t.version,
  t.description,
  t.commentary
FROM validation_tag vt
  JOIN tag t ON vt.tag_id = t.tag_id
WHERE vt.validation_id IN (:ids)
