SELECT
  date,
  username,
  commentary
FROM message_h
WHERE message_id = :id
ORDER BY date