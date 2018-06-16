CREATE DATABASE IF NOT EXISTS `fltrail` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `fltrail`;

CREATE TABLE if not exists `projects` (
  `id` varchar(400) NOT NULL,
  `password` varchar(400) NOT NULL,
  `activ` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(400) NOT NULL,
  `adminpassword` varchar(400) NOT NULL,
  `token` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists `groups` (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectId` varchar(400) NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists groupuser
        (
          userEmail varchar(400) NOT NULL,
          groupId int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `tags` (
  `projectId` varchar(400) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `users` (
  `name`                varchar(400) NOT NULL,
  `password`            varchar(200) NOT NULL,
  `email`               varchar(400) NOT NULL,
  `token`               varchar(800) NOT NULL,
  `rocketChatId`        varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists projectuser
        (
          projectId varchar(400) NOT NULL,
          userId varchar(400) NOT NULL
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table users
  add isStudent tinyint(1) default '1' null;


