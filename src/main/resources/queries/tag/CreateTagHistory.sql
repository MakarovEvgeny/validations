INSERT INTO tag_h (
    tag_id,
    name,
    version,
    date,
    description,
    commentary,
    username,
    ip
) VALUES (
    :id,
    :name,
    :version,
    :date,
    :description,
    :commentary,
    :username,
    inet(:ip)
)
