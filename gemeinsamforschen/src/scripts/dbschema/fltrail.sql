SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `fltrail` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `fltrail`;

CREATE TABLE IF NOT EXISTS `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userToken` varchar(120) DEFAULT NULL,
  `targetId` varchar(120) DEFAULT NULL,
  `targetCategory` varchar(30) NOT NULL,
  `title` varchar(120) DEFAULT NULL,
  `comment` varchar(400) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `answeredquiz` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `assessmentmechanismselected` (
  `projectId` varchar(100) NOT NULL,
  `amSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `categoriesselected` (
  `projectId` varchar(100) NOT NULL,
  `categorySelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `contributionrating` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `dossier` int(11) NOT NULL,
  `eJournal` int(11) NOT NULL,
  `research` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `fullsubmissions` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user` varchar(120) NOT NULL,
  `text` mediumtext NOT NULL,
  `projectId` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `grades` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groupfindingmechanismselected` (
  `projectId` varchar(100) NOT NULL,
  `gfmSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` varchar(400) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groupuser` (
  `userEmail` varchar(400) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `phasesselected` (
  `projectId` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `projects` (
  `id` varchar(100) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(400) NOT NULL,
  `adminPassword` varchar(400) NOT NULL,
  `token` varchar(400) NOT NULL,
  `phase` varchar(400) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `projectuser` (
  `projectId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  UNIQUE KEY `projectId_2` (`projectId`,`userId`),
  KEY `projectId` (`projectId`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `quiz` (
  `author` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `mcType` varchar(400) NOT NULL,
  `answer` varchar(400) NOT NULL,
  `correct` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `submissionpartbodyelements` (
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL,
  `startCharacter` int(11) NOT NULL,
  `endCharacter` int(11) NOT NULL,
  PRIMARY KEY (`fullSubmissionId`,`category`,`startCharacter`,`endCharacter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `submissionparts` (
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` varchar(120) NOT NULL,
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL,
  PRIMARY KEY (`fullSubmissionId`,`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tags` (
  `projectId` varchar(100) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tasks` (
  `userId` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `taskUrl` varchar(400) NOT NULL,
  `speakingName` varchar(400) DEFAULT NULL,
  `technicalName` varchar(400) DEFAULT NULL,
  `linkedMode` varchar(400) DEFAULT NULL,
  `groupTask` tinyint(4) DEFAULT NULL,
  `importance` varchar(100) DEFAULT NULL,
  `progress` varchar(100) DEFAULT NULL,
  `phase` varchar(100) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `due` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `users` (
  `name` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(800) NOT NULL,
  `rocketChatId` varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL,
  `isStudent` tinyint(1) DEFAULT '1',
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `workrating` (
  `projectId` varchar(400) NOT NULL,
  `studentId` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
