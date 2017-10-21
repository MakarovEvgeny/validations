UPDATE validation SET
  message_id = :messageId,
  description = :description,
  version = version + 1,
  commentary = :commentary
WHERE validation_id = :id AND version = :version