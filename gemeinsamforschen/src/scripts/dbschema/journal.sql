USE `fltrail`;

CREATE TABLE if not exists `journals` (
  `id`         varchar(400) NOT NULL,
  `timestamp`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  `author`     varchar(400) NOT NULL,
  `project`    varchar(400) NOT NULL,
  `text`       text,
  `visibility` varchar(50),
  `category`   varchar(50),
  `open`       TINYINT(1)


)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
