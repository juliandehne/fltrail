SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `fltrail` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `fltrail`;

CREATE TABLE `projects` (
  `id` varchar(400) NOT NULL,
  `password` varchar(400) NOT NULL,
  `activ` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(400) NOT NULL,
  `adminpassword` varchar(400) NOT NULL,
  `token` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tags` (
  `projectId` varchar(400) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `name` varchar(400) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(400) NOT NULL,
  `token` varchar(800) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE projectuser
        (
          projectId varchar(400) NOT NULL,
          userId varchar(400) NOT NULL
);  ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table users
  add isStudent tinyint(1) default '1' null;

CREATE TABLE researchReport (
   `title` varchar(400) NOT NULL,
   `method` varchar(10000) NOT NULL,
   `research` varchar(10000) NOT NULL,
   `researchResult` varchar(10000) NOT NULL,
   `evaluation` varchar(10000) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

//ToDo
CREATE TABLE timeplan(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE researchQuestion(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE bibliography(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE learningGoals(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE researchReport (
   `title` varchar(400) NOT NULL,
   `method` varchar(10000) NOT NULL,
   `research` varchar(10000) NOT NULL,
   `researchResult` varchar(10000) NOT NULL,
   `evaluation` varchar(10000) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

//ToDo
CREATE TABLE timeplan(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE researchQuestion(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE bibliography(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE learningGoals(
    `reportID` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
