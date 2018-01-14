INSERT INTO entity_h (
  date,
  entity_id,
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