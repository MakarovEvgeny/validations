INSERT INTO validation_entity_operation_h (validation_version_id, entity_version_id, operation_version_id)
  VALUES (
    :validationVersionId,
    (SELECT entity_version_id FROM entity_h WHERE entity_id = :entityId ORDER BY DATE DESC LIMIT 1),
    (SELECT operation_version_id FROM operation_h WHERE operation_id = :operationId ORDER BY DATE DESC LIMIT 1)
  )