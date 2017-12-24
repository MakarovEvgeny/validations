SELECT
  date,
  username,
  commentary
FROM operation_h
WHERE operation_id = :id
ORDER BY date