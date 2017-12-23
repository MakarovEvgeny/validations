SELECT
  username AS username,
  r.name   AS role,
  password AS password
FROM users u
  JOIN role r ON u.role_id = r.role_id
WHERE username = :username