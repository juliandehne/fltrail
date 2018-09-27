USE `fltrail`;

CREATE TABLE if not exists `journals` (
  `id`         varchar(400) NOT NULL,
  `timestamp`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  `userName`     varchar(400) NOT NULL,
  `projectName`    varchar(400) NOT NULL,
  `text`       text,
  `visibility` varchar(50),
  `category`   varchar(50),
  `open`       TINYINT(1)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `projectDescription` (
  `id`         varchar(400) NOT NULL,
  `timestamp`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  `userName`     varchar(400) NOT NULL,
  `projectName`    varchar(400) NOT NULL,
  `text`       text,
  `open`       TINYINT(1)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `links` (
  `id`         varchar(400) NOT NULL,
  `projecdesription`     varchar(400) NOT NULL,
  `name`       varchar(50) NOT NULL,
  `link`       varchar(50) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
