SELECT
  message_id AS id,
  text,
  version,
  commentary
FROM message_h
WHERE message_version_id = :id