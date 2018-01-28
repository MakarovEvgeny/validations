SELECT * FROM (
  SELECT
    message_id AS id,
    text,
    version,
    commentary
  FROM message ORDER BY message_id
) wrapper