SELECT
  date,
  username,
  commentary
FROM validation_h
WHERE validation_id = :id
ORDER BY date