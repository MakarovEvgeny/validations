select * from (
SELECT
  v.validation_id                    AS id,
  m.message_id                       AS messageId,
  m.text                             AS messageText,
  v.description                      AS description,
  v.version                          AS version,
  v.commentary                       AS commentary,
  s.severity_id                      AS severityId,
  s.name                             AS severityName,
  string_agg(DISTINCT en.name, ', ') AS entityNames,
  string_agg(DISTINCT op.name, ', ') AS operationNames
FROM validation v
  JOIN validation_entity_operation veo on v.validation_id = veo.validation_id
  JOIN operation op on veo.operation_id = op.operation_id
  JOIN entity en on veo.entity_id = en.entity_id
  JOIN message m on v.message_id = m.message_id
  JOIN severity s on v.severity_id = s.severity_id
GROUP BY v.validation_id, m.message_id, s.severity_id
ORDER BY v.validation_id
) wrapper