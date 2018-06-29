SELECT
    tag_version_id AS id,
    date,
    username,
    description,
    commentary
FROM tag_h
WHERE tag_id = :id
ORDER BY date
