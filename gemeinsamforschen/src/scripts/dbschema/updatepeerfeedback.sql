UPDATE
  peerfeedback
SET
  reciever = (
    SELECT
      token
    FROM
      users
    WHERE
      name = "kathi"
  ) WHERE reciever = "kathi"