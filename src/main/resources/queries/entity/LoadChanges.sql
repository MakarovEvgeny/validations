SELECT
  date,
  username,
  commentary
FROM entity_h
WHERE entity_id = :id
ORDER BY date