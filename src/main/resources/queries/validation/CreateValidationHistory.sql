INSERT INTO validation_h (
  validation_id,
  severity_id,
  date,
  message_id,
  description,
  version,
  commentary,
  username,
  ip
) VALUES (
  :id,
  :severityId,
  :date,
  :messageId,
  :description,
  :version,
  :commentary,
  :username,
  inet(:ip)
)