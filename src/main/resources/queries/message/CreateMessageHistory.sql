INSERT INTO message_h (
  message_id,
  text,
  version,
  date,
  commentary,
  username,
  ip
) VALUES (
  :id,
  :text,
  :version,
  :date,
  :commentary,
  :username,
  inet(:ip)
)