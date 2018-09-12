CREATE DATABASE IF NOT EXISTS `fltrail`

  DEFAULT CHARACTER SET utf8

  COLLATE utf8_general_ci;

USE `fltrail`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` int(11) DEFAULT NULL,
  `targetId` int(11) DEFAULT NULL,
  `body` varchar(280) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `assessments` (
  `adressat` tinyint(1) NOT NULL,
  `deadline` date NOT NULL,
  `erstellerId` varchar(400) NOT NULL,
  `empfaengerId` varchar(400) NOT NULL,
  `projektId` varchar(100) NOT NULL,
  `bewertung` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groupuser` (
  `userEmail` varchar(400) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projects` (
  `id` varchar(100) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(400) NOT NULL,
  `adminPassword` varchar(400) NOT NULL,
  `token` varchar(400) NOT NULL,
  `phase` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projectuser` (
  `projectId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `quiz` (
  `author` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `mcType` varchar(400) NOT NULL,
  `answer` varchar(400) NOT NULL,
  `correct` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tags` (
  `projectId` varchar(100) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tasks` (
  `userId` varchar(400) NOT NULL,
  `projectId` varchar(400) NOT NULL,
  `taskUrl` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `name` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(800) NOT NULL,
  `rocketChatId` varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL,
  `isStudent` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `annotations`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `projects`
  ADD UNIQUE KEY `id` (`id`);

ALTER TABLE `projectuser`
  ADD UNIQUE KEY `projectId_2` (`projectId`,`userId`),
  ADD KEY `projectId` (`projectId`,`userId`);

ALTER TABLE `users`
  ADD UNIQUE KEY `email` (`email`);


ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
