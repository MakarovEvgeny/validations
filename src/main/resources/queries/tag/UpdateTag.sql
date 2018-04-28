UPDATE tag SET
    name        = :name,
    version     = version + 1,
    description = :description,
    commentary  = :commentary
WHERE tag_id = :id AND version = :version
