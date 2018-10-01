

CREATE TABLE IF NOT EXISTS `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userEmail` varchar(120) DEFAULT NULL,
  `targetId` varchar(120) DEFAULT NULL,
  `targetCategory` varchar(30) NOT NULL,
  `title` varchar(120) DEFAULT NULL,
  `comment` varchar(400) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `answeredquiz` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `question` varchar(400) NOT NULL,
  `correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `assessmentmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `amSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `categoriesselected` (
  `projectName` varchar(100) NOT NULL,
  `categorySelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `contributionrating` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
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
  `projectName` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `grades` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groupfindingmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `gfmSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectName` varchar(400) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groupuser` (
  `userEmail` varchar(255) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `phasesselected` (
  `projectName` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `projects` (
  `name` varchar(100) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` mediumtext not null,
  `author` varchar(100) NOT NULL,
  `adminPassword` varchar(400) NOT NULL,
  `phase` varchar(400) NOT NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `projectuser` (
  `projectName` varchar(100) NOT NULL,
  `userEmail` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `quiz` (
  `author` varchar(400) NOT NULL,
  `projectName` varchar(400) NOT NULL,
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
  `userEmail` varchar(120) NOT NULL,
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL,
  PRIMARY KEY (`fullSubmissionId`,`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tags` (
  `projectName` varchar(100) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tasks` (
  `userEmail` varchar(400) NOT NULL,
  `projectName` varchar(400) NOT NULL,
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
  `rocketChatId` varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL,
  `isStudent` tinyint(1) DEFAULT '1',
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `workrating` (
  `projectName` varchar(400) NOT NULL,
  `userName` varchar(400) NOT NULL,
  `fromPeer` varchar(400) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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



Alter Table projectuser add  FOREIGN KEY (`userEmail`) REFERENCES users(`email`);
Alter Table projectuser add  FOREIGN KEY (`projectName`) REFERENCES projects(`name`);
ALTER TABLE groupuser add FOREIGN KEY (`userEmail`) REFERENCES users(`email`);
ALTER TABLE groupuser add FOREIGN KEY (`groupId`) REFERENCES groups(`id`);
ALTER TABLE projects add foreign key (`author`) REFERENCES users(`email`);