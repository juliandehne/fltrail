SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

DROP DATABASE `fltrail`;
CREATE DATABASE IF NOT EXISTS `fltrail` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;

USE `fltrail`;

CREATE TABLE `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userEmail` varchar(120) DEFAULT NULL,
  `targetId` varchar(120) DEFAULT NULL,
  `targetCategory` varchar(30) NOT NULL,
  `title` varchar(120) DEFAULT NULL,
  `comment` varchar(400) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `answeredquiz` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `assessmentmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `amSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `categoriesselected` (
  `projectName` varchar(100) NOT NULL,
  `categorySelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `contributionrating` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `dossier` int(11) NOT NULL,
  `eJournal` int(11) NOT NULL,
  `research` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `fullsubmissions` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user` varchar(120) NOT NULL,
  `text` mediumtext NOT NULL,
  `projectName` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `grades` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groupfindingmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `gfmSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `projectName` varchar(400) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groupuser` (
  `userEmail` varchar(255) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `phasesselected` (
  `projectName` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projects` (
  `name` varchar(100) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` mediumtext NOT NULL,
  `author` varchar(100) NOT NULL,
  `adminPassword` varchar(400) NOT NULL,
  `phase` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projectuser` (
  `projectName` varchar(100) NOT NULL,
  `userEmail` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `quiz` (
  `author` varchar(400) NOT NULL,
  `projectName` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `mcType` varchar(400) NOT NULL,
  `answer` varchar(400) NOT NULL,
  `correct` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `submissionpartbodyelements` (
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL,
  `startCharacter` int(11) NOT NULL,
  `endCharacter` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `submissionparts` (
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userEmail` varchar(120) NOT NULL,
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tags` (
  `projectName` varchar(100) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tasks` (
  `userEmail` varchar(100) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `taskName` varchar(100) DEFAULT NULL,
  `groupTask` tinyint(4) DEFAULT NULL,
  `importance` varchar(100) DEFAULT NULL,
  `progress` varchar(100) DEFAULT NULL,
  `phase` varchar(100) DEFAULT NULL,
  `created` mediumtext,
  `due` mediumtext,
  `taskMode2` varchar(100) DEFAULT NULL,
  `taskMode3` varchar(100) DEFAULT NULL,
  `taskMode` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `name` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(255) NOT NULL,
  `rocketChatId` varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL,
  `isStudent` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `workrating` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `annotations`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `fullsubmissions`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `groupuser`
  ADD KEY `userEmail` (`userEmail`),
  ADD KEY `groupId` (`groupId`);

ALTER TABLE `projects`
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `author` (`author`);

ALTER TABLE `projectuser`
  ADD KEY `userEmail` (`userEmail`),
  ADD KEY `projectName` (`projectName`);

ALTER TABLE `submissionpartbodyelements`
  ADD PRIMARY KEY (`fullSubmissionId`,`category`,`startCharacter`,`endCharacter`);

ALTER TABLE `submissionparts`
  ADD PRIMARY KEY (`fullSubmissionId`,`category`);

ALTER TABLE `users`
  ADD UNIQUE KEY `email` (`email`);


ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;


ALTER TABLE `groupuser`
  ADD CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`),
  ADD CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`);

ALTER TABLE `projects`
  ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`author`) REFERENCES `users` (`email`);

ALTER TABLE `projectuser`
  ADD CONSTRAINT `projectuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`),
  ADD CONSTRAINT `projectuser_ibfk_2` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`);
COMMIT;

CREATE UNIQUE INDEX fullsubmissions_user_projectName_uindex ON fullsubmissions (user, projectName);

CREATE UNIQUE INDEX tasks_userEmail_projectName_taskName_uindex ON tasks (userEmail, projectName, taskName);


