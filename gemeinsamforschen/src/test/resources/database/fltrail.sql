SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `fltrail`;
USE fltrail;
CREATE TABLE if not exists `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userEmail` varchar(120) DEFAULT NULL,
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
  `userEmail` VARCHAR(120) NOT NULL,
  `text` MEDIUMTEXT NOT NULL,
  `projectName` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists `submissionparts` (
  `timestamp` TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userEmail` VARCHAR(120) NOT NULL,
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

Create Table if not exists `answeredquiz` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create Table if not exists `contributionrating` (
  `groupId` int(11) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `dossier` int(11) NOT NULL,
  `research` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create Table if not exists `grades` (
  `projectName` varchar(400) NOT NULL,
  `userEmail` varchar(400) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create Table if not exists `groups` (
  `id` int(11) NOT NULL,
  `projectName` varchar(400) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
Create Table if not exists `groupuser` (
  `userName` varchar(400) NOT NULL,
  `projectName` varchar(400) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
Create Table if not exists `phasesselected` (
  `projectName` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create Table if not exists `profilepicture` (
  `userName` varchar(200) NOT NULL,
  `image` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create Table if not exists `projects` (
  `id` varchar(400) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `authorEmail` varchar(400) NOT NULL,
  `adminPassword` varchar(400) NOT NULL,
  `token` varchar(400) NOT NULL,
  `phase` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Create Table if not exists `projectuser` (
  `projectName` varchar(400) NOT NULL,
  `useremail` varchar(400) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Create Table if not exists `quiz` (
  `userName` varchar(400) NOT NULL,
  `projectName` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `mcType` varchar(400) NOT NULL,
  `answer` varchar(400) NOT NULL,
  `correct` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
Create Table if not exists `tags` (
  `projectName` varchar(400) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
Create Table if not exists `tasks` (
  `userEmail` varchar(400) NOT NULL,
  `projectName` varchar(400) NOT NULL,
  `taskUrl` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Create Table if not exists `users` (
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

Create Table if not exists `workrating` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `projectdescription` (
 `id` varchar(400) NOT NULL,
 `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 `author` varchar(400) NOT NULL,
 `projectName` varchar(400) NOT NULL,
 `text` text,
 `open` tinyint(1) DEFAULT NULL
)

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


ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `projectuser`
  ADD KEY `projectName` (`projectName`(255),`userEmail`(255));

ALTER TABLE `users`
  ADD UNIQUE KEY `email` (`email`);


ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT
COMMIT;