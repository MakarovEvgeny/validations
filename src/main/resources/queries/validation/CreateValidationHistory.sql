INSERT INTO validation_h (
  validation_id,
  severity_id,
  date,
  message_id,
  description,
  version,
  commentary,
  login,
  ip
) VALUES (
  :id,
  :severityId,
  :date,
  :messageId,
  :description,
  :version,
  :commentary,
  :login,
  inet(:ip)
)