INSERT INTO message_h (
  message_id,
  text,
  version,
  date,
  commentary,
  login,
  ip
) VALUES (
  :id,
  :text,
  :version,
  :date,
  :commentary,
  :login,
  inet(:ip)
)