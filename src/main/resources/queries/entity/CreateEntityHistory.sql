INSERT INTO entity_h (
  date,
  entity_id,
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