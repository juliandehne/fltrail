CREATE TABLE `annotations` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userEmail` varchar(255) DEFAULT NULL,
  `targetId` varchar(120) DEFAULT NULL,
  `targetCategory` varchar(30) NOT NULL,
  `title` varchar(120) DEFAULT NULL,
  `comment` varchar(400) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `answeredquiz` (
  `projectName` varchar(200) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `question` varchar(200) NOT NULL,
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
  `projectName` varchar(200) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `fromPeer` varchar(100) NOT NULL,
  `dossier` int(11) NOT NULL,
  `eJournal` int(11) NOT NULL,
  `research` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `fullsubmissions` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user` varchar(100) NOT NULL,
  `text` mediumtext NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `feedbackUser` varchar(255) DEFAULT NULL,
  `finalized` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `grades` (
  `projectName` varchar(200) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groupfindingmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `gfmSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `chatRoomId` varchar(400) NOT NULL,
  `name`        varchar(255) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `groupuser` (
  `userEmail` varchar(255) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `journals` (
  `id` varchar(100) NOT NULL,
  `userEmail` varchar(100) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `text` text NOT NULL,
  `visibility` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `open` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `phasesselected` (
  `projectName` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projects` (
  `name` varchar(100) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(100) NOT NULL,
  `adminPassword` varchar(400),
  `phase` varchar(400) NOT NULL DEFAULT 'GroupFormation',
  `description` varchar(400)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `projectuser` (
  `projectName` varchar(100) NOT NULL,
  `userEmail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `quiz` (
  `author` varchar(100) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `question` varchar(200) NOT NULL,
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
  `userEmail` varchar(255) NOT NULL,
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tags` (
  `projectName` varchar(200) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tasks` (
  `userEmail` varchar(255) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `taskName` varchar(100) DEFAULT NULL,
  `groupTask` tinyint(4) DEFAULT NULL,
  `importance` varchar(100) DEFAULT NULL,
  `progress` varchar(100) DEFAULT NULL,
  `phase` varchar(100) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `due` timestamp NULL DEFAULT NULL,
  `taskMode2` varchar(100) DEFAULT NULL,
  `taskMode3` varchar(100) DEFAULT NULL,
  `taskMode` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `name`                          varchar(100) NOT NULL,
  `password`                      varchar(200) NOT NULL,
  `email`                         varchar(255) NOT NULL,
  `rocketChatUserName`            varchar(400),
  `isStudent`                     tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `workrating` (
  `projectName` varchar(200) NOT NULL,
  `userEmail` varchar(100) NOT NULL,
  `fromPeer` varchar(100) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE submissionuser
(
    submissionId varchar(400),
    userEmail varchar(255)
);

ALTER TABLE `annotations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `annotations_fullsubmissions_id_fk` (`targetId`);

ALTER TABLE `answeredquiz`
  ADD KEY `answeredquiz_projects_name_fk` (`projectName`);

ALTER TABLE `assessmentmechanismselected`
  ADD KEY `assessmentmechanismselected_projects_name_fk` (`projectName`);

ALTER TABLE `categoriesselected`
  ADD KEY `categoriesselected_projects_name_fk` (`projectName`);

ALTER TABLE `contributionrating`
  ADD UNIQUE KEY `contributionrating_projectName_userName_fromPeer_uindex` (`projectName`,`userName`,`fromPeer`);

ALTER TABLE `fullsubmissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fullsubmissions_projects_name_fk` (`projectName`);

ALTER TABLE `grades`
  ADD UNIQUE KEY `grades_projectName_userName_uindex` (`projectName`,`userName`);

ALTER TABLE `groupfindingmechanismselected`
  ADD KEY `groupfindingmechanismselected_projects_name_fk` (`projectName`);

ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`),
  ADD KEY `groups_projects_name_fk` (`projectName`);

ALTER TABLE `groupuser`
  ADD KEY `userEmail` (`userEmail`),
  ADD KEY `groupId` (`groupId`);

ALTER TABLE `journals`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `phasesselected`
  ADD KEY `phasesselected_projectName_index` (`projectName`);

ALTER TABLE `projects`
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `author` (`author`);

ALTER TABLE `projectuser`
  ADD KEY `userEmail` (`userEmail`),
  ADD KEY `projectName` (`projectName`);

ALTER TABLE `quiz`
  ADD KEY `quiz_question_projectName_author_index` (`question`,`projectName`,`author`),
  ADD KEY `quiz_projects_name_fk` (`projectName`);

ALTER TABLE `submissionpartbodyelements`
  ADD PRIMARY KEY (`fullSubmissionId`,`category`,`startCharacter`,`endCharacter`);

ALTER TABLE `submissionparts`
  ADD PRIMARY KEY (`fullSubmissionId`,`category`);

ALTER TABLE `tags`
  ADD KEY `tags_projectName_index` (`projectName`);

ALTER TABLE `tasks`
  ADD UNIQUE KEY `tasks_userEmail_projectName_taskName_uindex` (`userEmail`,`projectName`,`taskName`),
  ADD KEY `tasks_projects_name_fk` (`projectName`);

ALTER TABLE `users`
  ADD UNIQUE KEY `email` (`email`);

ALTER TABLE `workrating`
  ADD UNIQUE KEY `workrating_projectName_userEmail_fromPeer_uindex` (`projectName`,`userEmail`,`fromPeer`);


ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `annotations`
  ADD CONSTRAINT `annotations_fullsubmissions_id_fk` FOREIGN KEY (`targetId`) REFERENCES `fullsubmissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `answeredquiz`
  ADD CONSTRAINT `answeredquiz_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assessmentmechanismselected`
  ADD CONSTRAINT `assessmentmechanismselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `categoriesselected`
  ADD CONSTRAINT `categoriesselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `contributionrating`
  ADD CONSTRAINT `contributionrating_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `fullsubmissions`
  ADD CONSTRAINT `fullsubmissions_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `grades`
  ADD CONSTRAINT `grades_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `groupfindingmechanismselected`
  ADD CONSTRAINT `groupfindingmechanismselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `groups`
  ADD CONSTRAINT `groups_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `groupuser`
  ADD CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE,
  ADD CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`) ON DELETE CASCADE;

ALTER TABLE `phasesselected`
  ADD CONSTRAINT `phasesselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `projects`
  ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`author`) REFERENCES `users` (`email`);

ALTER TABLE `projectuser`
  ADD CONSTRAINT `projectuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE,
  ADD CONSTRAINT `projectuser_ibfk_2` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE;

ALTER TABLE `quiz`
  ADD CONSTRAINT `quiz_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `workrating`
  ADD CONSTRAINT `workrating_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

CREATE UNIQUE INDEX fullsubmissions_user_projectName_uindex ON fullsubmissions (user, projectName);

CREATE TABLE profilequestions
(
  id int PRIMARY KEY AUTO_INCREMENT,
  scaleSize int,
  question varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE profilequestionoptions
(
  id int PRIMARY KEY AUTO_INCREMENT,
  profileQuestionId int,
  name varchar (255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE profilequestionanswer
(
    profileQuestionId int,
    answerIndex int,
    selectedAnswer varchar(255) not null,
    userEmail varchar(255) not null,
    CONSTRAINT profilequestionanswer_profilequestions_id_fk FOREIGN KEY (profileQuestionId)
    REFERENCES profilequestions(id),
    CONSTRAINT profilequestionanswer_user_email_fk FOREIGN KEY (userEmail)
    REFERENCES users(email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE profilequestionrelations
(
    firstQuestionId int,
    secondQuestionId int,
    relation varchar(200),
    CONSTRAINT profilequestionrelations_profilequestions_id_fk FOREIGN KEY (firstQuestionId) REFERENCES profilequestions (id),
    CONSTRAINT profilequestionrelations_profilequestions2_id_fk FOREIGN KEY (secondQuestionId) REFERENCES profilequestions (id)
);

ALTER TABLE profilequestionrelations COMMENT = 'This indicates if a profile question leads to homogenity in groups';
ALTER TABLE annotations COMMENT = 'Stores comments to a part of the dossier for a category such as RESEARCH';
ALTER TABLE answeredquiz COMMENT = 'The answered quiz table holds the interpreted result of the quiz answer';
ALTER TABLE assessmentmechanismselected COMMENT = 'Holds the peer assessement mechanism selected';
ALTER TABLE categoriesselected COMMENT = 'NOT IMPLEMENTED';
ALTER TABLE contributionrating COMMENT = 'TODO @Axel plz comment';
ALTER TABLE fullsubmissions COMMENT = 'This holds the aggregated text of the dossier students should upload';
ALTER TABLE grades COMMENT = 'Shows the grades that are calculated for a given student';
ALTER TABLE groupfindingmechanismselected COMMENT = 'Groupfinding is done either automatically or manual';
ALTER TABLE groups COMMENT = 'the groups that are created';
ALTER TABLE groupuser COMMENT = 'n x m table for group and user';
ALTER TABLE phasesselected COMMENT = 'the phase that is selected out of Phase enum';
ALTER TABLE profilequestionanswer COMMENT = 'the answer to a profile question needed for group finding algorithm';
ALTER TABLE profilequestionoptions COMMENT = 'the options for a profile question for thegroup finding algorithm';
ALTER TABLE profilequestions COMMENT = 'stores the questions needed for group finding';
ALTER TABLE projects COMMENT = 'just a list of all the projects';
ALTER TABLE projectuser COMMENT = 'n x m for projects and user';
ALTER TABLE quiz COMMENT = 'lists the quizzes for the app';
ALTER TABLE submissionpartbodyelements COMMENT = 'holds the parts of a dossier that are annoated with category';
ALTER TABLE submissionparts COMMENT = 'no idea what that it does but it is important';
ALTER TABLE submissionuser COMMENT = 'no idea if that is needed. seems not be used';
ALTER TABLE tags COMMENT = 'lists some tags for the project in order to make it more searchable and for groupfinding';
ALTER TABLE tasks COMMENT = 'The task table is important. It lists the actual state of the system associated to tasks';
ALTER TABLE users COMMENT = 'Just lists the users';
ALTER TABLE workrating COMMENT = '@Axel plz comment';








