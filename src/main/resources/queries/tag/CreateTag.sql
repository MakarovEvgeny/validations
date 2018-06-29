INSERT INTO tag (
    tag_id,
    name,
    description,
    commentary
) VALUES (
    uuid_generate_v4()::text,
    :name,
    :description,
    :commentary
)
