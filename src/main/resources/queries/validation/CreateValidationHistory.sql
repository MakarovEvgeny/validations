INSERT INTO validation_h (
  validation_id,
  severity_id,
  date,
  message_version_id,
  description,
  version,
  commentary,
  username,
  ip
) VALUES (
  :id,
  :severityId,
  :date,
  (SELECT message_version_id FROM message_h WHERE message_id = :messageId ORDER BY DATE DESC LIMIT 1),
  :description,
  :version,
  :commentary,
  :username,
  inet(:ip)
)