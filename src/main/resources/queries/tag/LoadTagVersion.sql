SELECT
    tag_id AS id,
    name,
    version,
    description,
    commentary
FROM tag_h
WHERE tag_version_id = :id
