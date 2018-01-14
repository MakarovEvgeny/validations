INSERT INTO operation_h (
  date,
  operation_id,
  name,
  description,
  version,
  commentary,
  username,
  ip
) VALUES (
  :date,
  :id,
  :name,
  :description,
  :version,
  :commentary,
  :username,
  inet(:ip)
)