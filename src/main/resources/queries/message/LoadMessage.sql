SELECT
  message_id AS id,
  text,
  version,
  commentary
FROM message
WHERE message_id = :id