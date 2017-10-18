UPDATE message SET
  text     = :text,
  version    = version + 1,
  commentary = :commentary
WHERE message_id = :id AND version = :version