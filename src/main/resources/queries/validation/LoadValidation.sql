SELECT
  v.validation_id AS id,
  v.description   AS description,
  v.version       AS version,
  v.commentary    AS commentary,

  v.severity_id   AS severityId,

  m.message_id    AS m_id,
  m.text          AS m_text,
  m.version       AS m_version,
  m.commentary    AS m_commentary

FROM validation v
  JOIN message m ON v.message_id = m.message_id
WHERE v.validation_id = :id