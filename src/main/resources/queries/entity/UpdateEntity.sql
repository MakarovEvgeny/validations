UPDATE entity SET
  name        = :name,
  description = :description,
  version     = version + 1,
  commentary  = :commentary
WHERE entity_id = :id AND version = :version