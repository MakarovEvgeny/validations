UPDATE operation SET
  name        = :name,
  description = :description,
  version     = version + 1,
  commentary  = :commentary
WHERE operation_id = :id AND version = :version