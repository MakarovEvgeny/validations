INSERT INTO validation_tag_h (validation_version_id, tag_version_id)
VALUES (
    :validationVersionId,
    (SELECT tag_version_id FROM tag_h WHERE tag_id = :tagId ORDER BY DATE DESC LIMIT 1)
)
