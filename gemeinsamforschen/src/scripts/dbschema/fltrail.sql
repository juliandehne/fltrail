CREATE DATABASE IF NOT EXISTS `fltrail`

  DEFAULT CHARACTER SET utf8

  COLLATE utf8_general_ci;

USE `fltrail`;

CREATE TABLE if not exists `projects` (

  `id`            varchar(100) NOT NULL,

  `password`      varchar(400) NOT NULL,

  `active`        tinyint(1)   NOT NULL,

  `timecreated`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,

  `author`        varchar(400) NOT NULL,

  `adminPassword` varchar(400) NOT NULL,

  `token`         varchar(400) NOT NULL,

  `phase`         varchar(400) NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `groups` (

  `id`         int          NOT NULL AUTO_INCREMENT,

  `projectId`  varchar(400) NOT NULL,

  `chatRoomId` varchar(400) NOT NULL,

  PRIMARY KEY (id)

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists groupuser

(

  userEmail varchar(400) NOT NULL,

  groupId   int          NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `tags` (

  `projectId` varchar(100) NOT NULL,

  `tag`       varchar(400) NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `users` (

  `name`                varchar(100) NOT NULL,

  `password`            varchar(200) NOT NULL,

  `email`               varchar(255) NOT NULL,

  `token`               varchar(800) NOT NULL,

  `rocketChatId`        varchar(400) NOT NULL,

  `rocketChatAuthToken` varchar(800) NOT NULL,

  UNIQUE (email)

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists projectuser

(

  projectId varchar(100) NOT NULL,

  userId    varchar(100) NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userToken` varchar(120) DEFAULT NULL,
  `targetId` int(11) DEFAULT NULL,
  `title` varchar(120) DEFAULT NULL,
  `comment` varchar(400) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `fullsubmissions` (
  `id` VARCHAR(120) NOT NULL,
  `timestamp` TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user` VARCHAR(120) NOT NULL,
  `text` MEDIUMTEXT NOT NULL,
  `projectId` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `submissionparts` (
  `timestamp` TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userId` VARCHAR(120) NOT NULL,
  `fullSubmissionId` VARCHAR(120) NOT NULL,
  `category` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`fullSubmissionId`, `category`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `submissionpartbodyelements` (
  `fullSubmissionId` VARCHAR(120) NOT NULL,
  `category` VARCHAR(30) NOT NULL,
  `text` MEDIUMTEXT NOT NULL,
  `startCharacter` int(11) NOT NULL,
  `endCharacter` int(11) NOT NULL,
  PRIMARY KEY (`fullSubmissionId`, `category`, `startCharacter`, `endCharacter`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

alter table users

  add isStudent tinyint(1) default '1' null;

CREATE TABLE if not exists quiz

(

  author    varchar(400) NOT NULL,

  projectId varchar(400) NOT NULL,

  question  varchar(400) NOT NULL,

  mcType    varchar(400) NOT NULL,

  answer    varchar(400) NOT NULL,

  correct   tinyint(1)   NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;
CREATE TABLE if not exists grades

(

  projectId varchar(400) NOT NULL,

  studentId varchar(400) NOT NULL,

  grade double NOT NULL
)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists tasks

(

  userId    varchar(400) NOT NULL,

  projectId varchar(400) NOT NULL,

  taskUrl   varchar(400) NOT NULL
)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;


CREATE TABLE if not exists phasesSelected (
 `projectId`    varchar(100) NOT NULL,
  phaseSelected varchar(200) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists categoriesSelected (
  `projectId`    varchar(100) NOT NULL,
   categorySelected varchar(200) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists groupfindingMechanismSelected (
  `projectId`    varchar(100) NOT NULL,
   gfmSelected   varchar(200) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists assessmentMechanismSelected (
  `projectId`    varchar(100) NOT NULL,
   amSelected    varchar(200) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `projectuser`
  ADD INDEX (`projectId`, `userId`);
ALTER TABLE `projectuser`
  ADD UNIQUE (`projectId`, `userId`);
ALTER TABLE `projects`
  ADD UNIQUE (`id`);