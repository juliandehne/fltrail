SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `fltrail`;
USE fltrail;
CREATE TABLE if not exists `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userToken` varchar(120) DEFAULT NULL,
  `targetId` varchar(120) DEFAULT NULL,
  `targetCategory` VARCHAR(30) NOT NULL,
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
  `startCharacter` int(11) NOT NULL,
  `endCharacter` int(11) NOT NULL,
  PRIMARY KEY (`fullSubmissionId`, `category`, `startCharacter`, `endCharacter`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `answeredquiz` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `contributionrating` (
  `groupId` int(11) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `dossier` int(11) NOT NULL,
  `research` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `grades` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `groupuser` (
  `studentId` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `phasesselected` (
  `projectId` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `profilepicture` (
  `studentId` varchar(200) NOT NULL,
  `image` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `projects` (
  `id` varchar(400) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `authorEmail` varchar(400) NOT NULL,
  `adminPassword` varchar(400) NOT NULL,
  `token` varchar(400) NOT NULL,
  `phase` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projectuser` (
  `projectId` varchar(400) NOT NULL,
  `userId` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `quiz` (
  `studentId` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `mcType` varchar(400) NOT NULL,
  `answer` varchar(400) NOT NULL,
  `correct` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `tags` (
  `projectId` varchar(400) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `tasks` (
  `userId` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `taskUrl` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `name` varchar(400) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(800) NOT NULL,
  `rocketChatId` varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL,
  `isStudent` tinyint(1) DEFAULT '1'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `workrating` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `projectuser`
  ADD KEY `projectId` (`projectId`(255),`userId`(255));

ALTER TABLE `users`
  ADD UNIQUE KEY `email` (`email`);


ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;