SELECT
    tag_id AS id,
    name,
    version,
    description,
    commentary
FROM tag
WHERE tag_id IN (:ids)
