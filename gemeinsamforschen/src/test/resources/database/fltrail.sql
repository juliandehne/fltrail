-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 10. Jun 2019 um 10:43
-- Server-Version: 10.1.32-MariaDB
-- PHP-Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `fltrail`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `annotations`
--

CREATE TABLE `annotations`
(
    `id`             varchar(120) NOT NULL,
    `timestamp`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `userEmail`      varchar(255)          DEFAULT NULL,
    `targetId`       varchar(120)          DEFAULT NULL,
    `targetCategory` varchar(30)  NOT NULL,
    `title`          varchar(120)          DEFAULT NULL,
    `comment`        varchar(400)          DEFAULT NULL,
    `startCharacter` int(11)               DEFAULT NULL,
    `endCharacter`   int(11)               DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Stores comments to a part of the dossier for a category such as RESEARCH';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `answeredquiz`
--

CREATE TABLE `answeredquiz`
(
    `projectName` varchar(200) NOT NULL,
    `userName`    varchar(100) NOT NULL,
    `question`    varchar(200) NOT NULL,
    `correct`     tinyint(4)   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='The answered quiz table holds the interpreted result of the quiz answer';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `assessmentmechanismselected`
--

CREATE TABLE `assessmentmechanismselected`
(
    `projectName` varchar(100) NOT NULL,
    `amSelected`  varchar(200) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Holds the peer assessement mechanism selected';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `categoriesselected`
--

CREATE TABLE `categoriesselected`
(
    `projectName`      varchar(100) NOT NULL,
    `categorySelected` varchar(200) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='NOT IMPLEMENTED';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `contributionfeedback`
--

CREATE TABLE `contributionfeedback`
(
    `id`                         varchar(120) CHARACTER SET utf8 NOT NULL,
    `fullsubmissionId`           varchar(120) CHARACTER SET utf8 DEFAULT NULL,
    `fullSubmissionPartCategory` varchar(120) CHARACTER SET utf8 DEFAULT NULL,
    `text`                       mediumtext CHARACTER SET utf8,
    `groupId`                    int(11)                         NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = uft8 COMMENT ='This table saves feedback for contributions';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `contributionrating`
--

CREATE TABLE `contributionrating`
(
    `projectName` varchar(200) NOT NULL,
    `userName`    varchar(100) DEFAULT NULL,
    `fromPeer`    varchar(100) DEFAULT NULL,
    `groupId`     int(11)      DEFAULT NULL,
    `fileRole`    varchar(100) NOT NULL,
    `fromTeacher` varchar(100) DEFAULT NULL,
    `rating`      int(11)      NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Holds the quantitative peer assessment regarding the uploads.';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fullsubmissions`
--

CREATE TABLE `fullsubmissions`
(
    `id`            varchar(120) NOT NULL,
    `version`       int(11)      NOT NULL,
    `timestamp`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `groupId`       int(11)               DEFAULT NULL,
    `text`          mediumtext   NOT NULL,
    `projectName`   varchar(200) NOT NULL,
    `feedbackGroup` int(11)               DEFAULT NULL,
    `finalized`     tinyint(1)            DEFAULT NULL,
    `fileRole`      varchar(200) NOT NULL,
    `userEmail`     varchar(255)          DEFAULT NULL,
    `visibility`    varchar(200) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='This holds the aggregated text of the dossier students should upload';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `grades`
--

CREATE TABLE `grades`
(
    `projectName` varchar(200) NOT NULL,
    `userEmail`   varchar(100) NOT NULL,
    `grade`       double       NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Shows the grades that are calculated for a given student';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groupfindingmechanismselected`
--

CREATE TABLE `groupfindingmechanismselected`
(
    `projectName` varchar(100) NOT NULL,
    `gfmSelected` varchar(200) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Groupfinding is done either automatically or manual';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups`
--

CREATE TABLE `groups`
(
    `id`          int(11)      NOT NULL,
    `projectName` varchar(200) NOT NULL,
    `chatRoomId`  varchar(400) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='the groups that are created';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groupuser`
--

CREATE TABLE `groupuser`
(
    `userEmail` varchar(255) NOT NULL,
    `groupId`   int(11)      NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='n x m table for group and user';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `journals`
--

CREATE TABLE `journals`
(
    `id`          varchar(100) NOT NULL,
    `userEmail`   varchar(100) NOT NULL,
    `projectName` varchar(200) NOT NULL,
    `text`        text         NOT NULL,
    `visibility`  varchar(100) NOT NULL,
    `category`    varchar(100) NOT NULL,
    `open`        tinyint(4)   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `largefilestorage`
--

CREATE TABLE `largefilestorage`
(
    `id`           int(11)      NOT NULL,
    `groupId`      int(11)      DEFAULT NULL,
    `userEmail`    varchar(255) DEFAULT NULL,
    `projectName`  varchar(100) NOT NULL,
    `filelocation` varchar(100) NOT NULL,
    `filerole`     varchar(100) NOT NULL,
    `filename`     varchar(100) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mappedtasks`
--

CREATE TABLE `mappedtasks`
(
    `id`            int(11) NOT NULL,
    `subjectEmail`  varchar(200) DEFAULT NULL,
    `groupObjectId` int(11)      DEFAULT NULL,
    `objectEmail`   varchar(200) DEFAULT NULL,
    `taskname`      varchar(200) DEFAULT NULL,
    `projectName`   varchar(200) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='This table holds the task mapping i.e. which user should give feedback to which groups products';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `originalgroups`
--

CREATE TABLE `originalgroups`
(
    `userEmail`               varchar(200) NOT NULL,
    `projectName`             varchar(200) NOT NULL,
    `groupId`                 int(11)      NOT NULL,
    `groupFormationMechanism` varchar(50)  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `peerassessmentworkanswer`
--

CREATE TABLE `peerassessmentworkanswer`
(
    `propertieId`    int(11)      DEFAULT NULL,
    `answerIndex`    int(11)      DEFAULT NULL,
    `selectedAnswer` varchar(255) DEFAULT NULL,
    `userEmail`      varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `peerassessmentworkproperties`
--

CREATE TABLE `peerassessmentworkproperties`
(
    `id`          int(11)      NOT NULL,
    `scaleSize`   int(11)      DEFAULT NULL,
    `question`    varchar(500) NOT NULL,
    `question_en` varchar(500) NOT NULL,
    `subvariable` varchar(100) DEFAULT NULL,
    `polarity`    tinyint(4)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `phasesselected`
--

CREATE TABLE `phasesselected`
(
    `projectName`   varchar(100) NOT NULL,
    `phaseSelected` varchar(200) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='the phase that is selected out of Phase enum';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `profilequestionanswer`
--

CREATE TABLE `profilequestionanswer`
(
    `profileQuestionId` int(11)      DEFAULT NULL,
    `answerIndex`       int(11)      DEFAULT NULL,
    `selectedAnswer`    varchar(255) DEFAULT NULL,
    `userEmail`         varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='the answer to a profile question needed for group finding algorithm';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `profilequestionoptions`
--

CREATE TABLE `profilequestionoptions`
(
    `id`                int(11) NOT NULL,
    `profileQuestionId` int(11)      DEFAULT NULL,
    `name`              varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='the options for a profile question for thegroup finding algorithm';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `profilequestionrelations`
--

CREATE TABLE `profilequestionrelations`
(
    `firstQuestionId`  int(11)      DEFAULT NULL,
    `secondQuestionId` int(11)      DEFAULT NULL,
    `relation`         varchar(200) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='This indicates if a profile question leads to homogenity in groups';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `profilequestions`
--

CREATE TABLE `profilequestions`
(
    `id`          int(11)      NOT NULL,
    `scaleSize`   int(11)      DEFAULT NULL,
    `question`    varchar(500) NOT NULL,
    `question_en` varchar(500) NOT NULL,
    `subvariable` varchar(100) DEFAULT NULL,
    `polarity`    tinyint(1)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='stores the questions needed for group finding';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `profilevariables`
--

CREATE TABLE `profilevariables`
(
    `variable`              varchar(100) DEFAULT NULL,
    `subvariable`           varchar(100) DEFAULT NULL,
    `subvariabledefinition` varchar(300) DEFAULT NULL,
    `variabledefinition`    varchar(300) DEFAULT NULL,
    `context`               varchar(300) DEFAULT NULL,
    `variableweight`        float        DEFAULT NULL,
    `subvariableweight`     float        DEFAULT NULL,
    `homogeneity`           tinyint(1)   DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projects`
--

CREATE TABLE `projects`
(
    `name`        varchar(100) NOT NULL,
    `password`    varchar(400) NOT NULL,
    `active`      tinyint(1)   NOT NULL,
    `timecreated` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `author`      varchar(100) NOT NULL,
    `description` varchar(600)          DEFAULT NULL,
    `phase`       varchar(400) NOT NULL DEFAULT 'GroupFormation',
    `isSurvey`    tinyint(1)            DEFAULT NULL,
    `context`     varchar(100)          DEFAULT NULL,
    `groupSize`   int(11)      NOT NULL DEFAULT '0'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='just a list of all the projects';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projectuser`
--

CREATE TABLE `projectuser`
(
    `projectName` varchar(100) NOT NULL,
    `userEmail`   varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='n x m for projects and user';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `quiz`
--

CREATE TABLE `quiz`
(
    `author`      varchar(100) NOT NULL,
    `projectName` varchar(200) NOT NULL,
    `question`    varchar(200) NOT NULL,
    `mcType`      varchar(400) NOT NULL,
    `answer`      varchar(400) NOT NULL,
    `correct`     tinyint(1)   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='lists the quizzes for the app';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `reflectionquestions`
--

CREATE TABLE `reflectionquestions`
(
    `id`               varchar(400) CHARACTER SET utf8 NOT NULL,
    `learningGoalId`   varchar(400) CHARACTER SET utf8 NOT NULL,
    `question`         varchar(400) CHARACTER SET utf8 NOT NULL,
    `userEmail`        varchar(255) CHARACTER SET utf8 NOT NULL,
    `fullSubmissionId` varchar(120) CHARACTER SET utf8 DEFAULT NULL,
    `projectName`      varchar(100) CHARACTER SET utf8 NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='holds all reflection questions students have to answer or had answered';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `submissionpartbodyelements`
--

CREATE TABLE `submissionpartbodyelements`
(
    `fullSubmissionId` varchar(120) NOT NULL,
    `category`         varchar(30)  NOT NULL,
    `startCharacter`   int(11)      NOT NULL,
    `endCharacter`     int(11)      NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='holds the parts of a dossier that are annoated with category';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `submissionparts`
--

CREATE TABLE `submissionparts`
(
    `timestamp`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `groupId`          int(11)      NOT NULL,
    `fullSubmissionId` varchar(120) NOT NULL,
    `category`         varchar(30)  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='no idea what that it does but it is important';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `surveyitemsselected`
--

CREATE TABLE `surveyitemsselected`
(
    `projectname`       varchar(100) DEFAULT NULL,
    `profilequestionid` int(11)      DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tags`
--

CREATE TABLE `tags`
(
    `projectName` varchar(200) NOT NULL,
    `tag`         varchar(400) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='lists some tags for the project in order to make it more searchable and for groupfinding';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tasks`
--

CREATE TABLE `tasks`
(
    `userEmail`   varchar(255) NOT NULL,
    `projectName` varchar(200) NOT NULL,
    `taskName`    varchar(100)      DEFAULT NULL,
    `groupTask`   tinyint(4)        DEFAULT NULL,
    `importance`  varchar(100)      DEFAULT NULL,
    `progress`    varchar(100)      DEFAULT NULL,
    `phase`       varchar(100)      DEFAULT NULL,
    `created`     timestamp    NULL DEFAULT NULL,
    `due`         timestamp    NULL DEFAULT NULL,
    `taskMode2`   varchar(100)      DEFAULT NULL,
    `taskMode3`   varchar(100)      DEFAULT NULL,
    `taskMode`    varchar(100)      DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='The task table is important. It lists the actual state of the system associated to tasks';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users`
(
    `id`                 int(11)      NOT NULL,
    `name`               varchar(100) NOT NULL,
    `password`           varchar(200) NOT NULL,
    `email`              varchar(255) NOT NULL,
    `rocketChatUserName` varchar(400) DEFAULT NULL,
    `isStudent`          tinyint(1)   DEFAULT '1',
    `discordid`          varchar(100) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Just lists the users';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `workrating`
--

CREATE TABLE `workrating`
(
    `projectName` varchar(200) NOT NULL,
    `userEmail`   varchar(100) NOT NULL,
    `fromPeer`    varchar(100) NOT NULL,
    `rating`      int(11)      NOT NULL,
    `itemName`    varchar(100) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='@Axel plz comment';

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `annotations`
--
ALTER TABLE `annotations`
    ADD PRIMARY KEY (`id`),
    ADD KEY `annotations_fullsubmissions_id_fk` (`targetId`);

--
-- Indizes für die Tabelle `answeredquiz`
--
ALTER TABLE `answeredquiz`
    ADD KEY `answeredquiz_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `assessmentmechanismselected`
--
ALTER TABLE `assessmentmechanismselected`
    ADD KEY `assessmentmechanismselected_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `categoriesselected`
--
ALTER TABLE `categoriesselected`
    ADD KEY `categoriesselected_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `contributionfeedback`
--
ALTER TABLE `contributionfeedback`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `feedback_id_uindex` (`id`),
    ADD KEY `feedback_fullsubmissions_id_fk` (`fullsubmissionId`),
    ADD KEY `contributionfeedback_groups_id_fk` (`groupId`);

--
-- Indizes für die Tabelle `contributionrating`
--
ALTER TABLE `contributionrating`
    ADD UNIQUE KEY `contributionrating_projectName_userName_fromPeer_uindex` (`projectName`, `userName`, `fromPeer`);

--
-- Indizes für die Tabelle `fullsubmissions`
--
ALTER TABLE `fullsubmissions`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `fullsubmissions_id_uindex` (`id`),
    ADD KEY `fullsubmissions_projects_name_fk` (`projectName`),
    ADD KEY `fullsubmissions_version_fk` (`version`),
    ADD KEY `fullsubmissions_contribution_category_fk` (`fileRole`),
    ADD KEY `fullsubmissions_users_email_fk` (`userEmail`),
    ADD KEY `fullsubmissions_groups_id_fk` (`groupId`);

--
-- Indizes für die Tabelle `grades`
--
ALTER TABLE `grades`
    ADD UNIQUE KEY `grades_projectName_userEmail_uindex` (`projectName`, `userEmail`);

--
-- Indizes für die Tabelle `groupfindingmechanismselected`
--
ALTER TABLE `groupfindingmechanismselected`
    ADD KEY `groupfindingmechanismselected_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `groups`
--
ALTER TABLE `groups`
    ADD PRIMARY KEY (`id`),
    ADD KEY `groups_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `groupuser`
--
ALTER TABLE `groupuser`
    ADD KEY `userEmail` (`userEmail`),
    ADD KEY `groupId` (`groupId`);

--
-- Indizes für die Tabelle `journals`
--
ALTER TABLE `journals`
    ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `largefilestorage`
--
ALTER TABLE `largefilestorage`
    ADD PRIMARY KEY (`id`),
    ADD KEY `largefilestorage_projects_name_fk` (`projectName`),
    ADD KEY `largefilestorage_users_email_fk` (`userEmail`);

--
-- Indizes für die Tabelle `mappedtasks`
--
ALTER TABLE `mappedtasks`
    ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `peerassessmentworkproperties`
--
ALTER TABLE `peerassessmentworkproperties`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `id` (`id`);

--
-- Indizes für die Tabelle `phasesselected`
--
ALTER TABLE `phasesselected`
    ADD KEY `phasesselected_projectName_index` (`projectName`);

--
-- Indizes für die Tabelle `profilequestionanswer`
--
ALTER TABLE `profilequestionanswer`
    ADD KEY `profilequestionanswer_profilequestions_id_fk` (`profileQuestionId`),
    ADD KEY `profilequestionanswer_user_email_fk` (`userEmail`);

--
-- Indizes für die Tabelle `profilequestionoptions`
--
ALTER TABLE `profilequestionoptions`
    ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `profilequestionrelations`
--
ALTER TABLE `profilequestionrelations`
    ADD KEY `profilequestionrelations_profilequestions_id_fk` (`firstQuestionId`),
    ADD KEY `profilequestionrelations_profilequestions2_id_fk` (`secondQuestionId`);

--
-- Indizes für die Tabelle `profilequestions`
--
ALTER TABLE `profilequestions`
    ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `projects`
--
ALTER TABLE `projects`
    ADD UNIQUE KEY `name` (`name`),
    ADD KEY `author` (`author`);

--
-- Indizes für die Tabelle `projectuser`
--
ALTER TABLE `projectuser`
    ADD KEY `userEmail` (`userEmail`),
    ADD KEY `projectName` (`projectName`);

--
-- Indizes für die Tabelle `quiz`
--
ALTER TABLE `quiz`
    ADD KEY `quiz_question_projectName_author_index` (`question`, `projectName`, `author`),
    ADD KEY `quiz_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `reflectionquestions`
--
ALTER TABLE `reflectionquestions`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `reflexionquestions_fullSubmissionId_uindex` (`fullSubmissionId`),
    ADD KEY `reflexionquestions_users_email_fk` (`userEmail`),
    ADD KEY `reflexionquestions_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `submissionpartbodyelements`
--
ALTER TABLE `submissionpartbodyelements`
    ADD PRIMARY KEY (`fullSubmissionId`, `category`, `startCharacter`, `endCharacter`);

--
-- Indizes für die Tabelle `submissionparts`
--
ALTER TABLE `submissionparts`
    ADD PRIMARY KEY (`fullSubmissionId`, `category`);

--
-- Indizes für die Tabelle `surveyitemsselected`
--
ALTER TABLE `surveyitemsselected`
    ADD KEY `surveyitemsselected_profilequestions_id_fk` (`profilequestionid`);

--
-- Indizes für die Tabelle `tags`
--
ALTER TABLE `tags`
    ADD KEY `tags_projectName_index` (`projectName`);

--
-- Indizes für die Tabelle `tasks`
--
ALTER TABLE `tasks`
    ADD UNIQUE KEY `tasks_userEmail_projectName_taskName_uindex` (`userEmail`, `projectName`, `taskName`, `groupTask`),
    ADD KEY `tasks_projects_name_fk` (`projectName`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `email` (`email`);

--
-- Indizes für die Tabelle `workrating`
--
ALTER TABLE `workrating`
    ADD UNIQUE KEY `workrating_projectName_userEmail_fromPeer_uindex` (`projectName`, `userEmail`, `fromPeer`, `itemName`) USING BTREE;

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `groups`
--
ALTER TABLE `groups`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `largefilestorage`
--
ALTER TABLE `largefilestorage`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `mappedtasks`
--
ALTER TABLE `mappedtasks`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `peerassessmentworkproperties`
--
ALTER TABLE `peerassessmentworkproperties`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `profilequestionoptions`
--
ALTER TABLE `profilequestionoptions`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `profilequestions`
--
ALTER TABLE `profilequestions`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `annotations`
--
ALTER TABLE `annotations`
    ADD CONSTRAINT `annotations_fullsubmissions_id_fk` FOREIGN KEY (`targetId`) REFERENCES `fullsubmissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `answeredquiz`
--
ALTER TABLE `answeredquiz`
    ADD CONSTRAINT `answeredquiz_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `assessmentmechanismselected`
--
ALTER TABLE `assessmentmechanismselected`
    ADD CONSTRAINT `assessmentmechanismselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `categoriesselected`
--
ALTER TABLE `categoriesselected`
    ADD CONSTRAINT `categoriesselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `contributionfeedback`
--
ALTER TABLE `contributionfeedback`
    ADD CONSTRAINT `contributionfeedback_groups_id_fk` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `feedback_fullsubmissions_id_fk` FOREIGN KEY (`fullsubmissionId`) REFERENCES `fullsubmissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `contributionrating`
--
ALTER TABLE `contributionrating`
    ADD CONSTRAINT `contributionrating_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `fullsubmissions`
--
ALTER TABLE `fullsubmissions`
    ADD CONSTRAINT `fullsubmissions_groups_id_fk` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `fullsubmissions_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `fullsubmissions_users_email_fk` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `grades`
--
ALTER TABLE `grades`
    ADD CONSTRAINT `grades_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `groupfindingmechanismselected`
--
ALTER TABLE `groupfindingmechanismselected`
    ADD CONSTRAINT `groupfindingmechanismselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `groups`
--
ALTER TABLE `groups`
    ADD CONSTRAINT `groups_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `groupuser`
--
ALTER TABLE `groupuser`
    ADD CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE,
    ADD CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`) ON DELETE CASCADE;

--
-- Constraints der Tabelle `largefilestorage`
--
ALTER TABLE `largefilestorage`
    ADD CONSTRAINT `largefilestorage_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `largefilestorage_users_email_fk` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `phasesselected`
--
ALTER TABLE `phasesselected`
    ADD CONSTRAINT `phasesselected_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `profilequestionanswer`
--
ALTER TABLE `profilequestionanswer`
    ADD CONSTRAINT `profilequestionanswer_profilequestions_id_fk` FOREIGN KEY (`profileQuestionId`) REFERENCES `profilequestions` (`id`),
    ADD CONSTRAINT `profilequestionanswer_user_email_fk` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`);

--
-- Constraints der Tabelle `profilequestionrelations`
--
ALTER TABLE `profilequestionrelations`
    ADD CONSTRAINT `profilequestionrelations_profilequestions2_id_fk` FOREIGN KEY (`secondQuestionId`) REFERENCES `profilequestions` (`id`),
    ADD CONSTRAINT `profilequestionrelations_profilequestions_id_fk` FOREIGN KEY (`firstQuestionId`) REFERENCES `profilequestions` (`id`);

--
-- Constraints der Tabelle `projects`
--
ALTER TABLE `projects`
    ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`author`) REFERENCES `users` (`email`);

--
-- Constraints der Tabelle `projectuser`
--
ALTER TABLE `projectuser`
    ADD CONSTRAINT `projectuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE,
    ADD CONSTRAINT `projectuser_ibfk_2` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE;

--
-- Constraints der Tabelle `quiz`
--
ALTER TABLE `quiz`
    ADD CONSTRAINT `quiz_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `reflectionquestions`
--
ALTER TABLE `reflectionquestions`
    ADD CONSTRAINT `reflexionquestions_fullsubmissions_id_fk` FOREIGN KEY (`fullSubmissionId`) REFERENCES `fullsubmissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `reflexionquestions_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `reflexionquestions_users_email_fk` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `surveyitemsselected`
--
ALTER TABLE `surveyitemsselected`
    ADD CONSTRAINT `surveyitemsselected_profilequestions_id_fk` FOREIGN KEY (`profilequestionid`) REFERENCES `profilequestions` (`id`);

--
-- Constraints der Tabelle `tags`
--
ALTER TABLE `tags`
    ADD CONSTRAINT `tags_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `tasks`
--
ALTER TABLE `tasks`
    ADD CONSTRAINT `tasks_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `workrating`
--
ALTER TABLE `workrating`
    ADD CONSTRAINT `workrating_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
