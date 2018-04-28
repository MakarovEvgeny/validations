SELECT
  t.tag_id AS id,
  t.name,
  t.version,
  t.description,
  t.commentary
FROM validation_tag_h vth
  JOIN tag_h t ON vth.tag_version_id = t.tag_version_id
WHERE vth.validation_version_id = :id
