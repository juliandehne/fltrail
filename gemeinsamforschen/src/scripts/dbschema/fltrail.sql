CREATE DATABASE IF NOT EXISTS `fltrail`

  DEFAULT CHARACTER SET utf8

  COLLATE utf8_general_ci;

USE `fltrail`;

CREATE TABLE if not exists `projects` (

  `id`            varchar(400) NOT NULL,

  `password`      varchar(400) NOT NULL,

  `active`        tinyint(1) NOT NULL,

  `timecreated`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

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

  `projectId` varchar(400) NOT NULL,

  `tag`       varchar(400) NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists `users` (

  `name`                varchar(400) NOT NULL,

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

  projectId varchar(400) NOT NULL,

  userId    varchar(400) NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;CREATE TABLE if not exists `annotations` (

  `id` varchar(120) NOT NULL,

  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  `userId` int(11) DEFAULT NULL,

  `targetId` int(11) DEFAULT NULL,

  `body` varchar(280) DEFAULT NULL,

  `startCharacter` int(11) DEFAULT NULL,

  `endCharacter` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;alter table users

  add isStudent tinyint(1) default '1' null;

CREATE TABLE if not exists quiz

(

  author varchar(400) NOT NULL,

  projectId varchar(400) NOT NULL,

  question varchar(400) NOT NULL,

  mcType varchar(400) NOT NULL,

  answer varchar(400) NOT NULL,

  correct tinyint(1) NOT NULL

)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists tasks

(

  userId varchar(400) NOT NULL,

  projectId varchar(400) NOT NULL,

  taskUrl varchar (400) NOT NULL
)

  ENGINE = InnoDB

  DEFAULT CHARSET = utf8;