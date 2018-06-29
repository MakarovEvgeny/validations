SELECT
    v.validation_id                    AS code,
    m.message_id                       AS messageCode,
    v.description                      AS description,
    s.name                             AS severity,
    string_agg(DISTINCT en.name, ', ') AS entities,
    string_agg(DISTINCT op.name, ', ') AS operations,
    string_agg(DISTINCT t.name, ', ')  AS tags
FROM validation v
    JOIN validation_entity_operation veo on v.validation_id = veo.validation_id
    JOIN operation op on veo.operation_id = op.operation_id
    JOIN entity en on veo.entity_id = en.entity_id
    JOIN message m on v.message_id = m.message_id
    JOIN severity s on v.severity_id = s.severity_id
    LEFT JOIN validation_tag vt on vt.validation_id = v.validation_id
    LEFT JOIN tag t on t.tag_id = vt.tag_id
GROUP BY v.validation_id, en.entity_id, m.message_id, s.severity_id
ORDER BY v.validation_id, en.entity_id
