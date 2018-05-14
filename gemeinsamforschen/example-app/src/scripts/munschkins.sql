
use munschkins;

CREATE TABLE IF NOT EXISTS Munschkins (
  MunschkinId int,
  LastName varchar(255),
  FirstName varchar(255),
  BadThings varchar(255),
  Strength int
);

INSERT INTO `Munschkins` (MunschkinId, LastName, FirstName, BadThings, Strength) values(1, "Thor", "Theodor", "he
will eat your feet", 5);

