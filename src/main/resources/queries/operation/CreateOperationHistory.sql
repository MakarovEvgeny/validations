INSERT INTO operation_h (
  date,
  operation_id,
  name,
  description,
  version,
  commentary,
  login,
  ip
) VALUES (
  :date,
  :id,
  :name,
  :description,
  :version,
  :commentary,
  :login,
  inet(:ip)
)