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
  `userEmail` varchar(255) DEFAULT NULL,
  `targetId` varchar(120) DEFAULT NULL,
  `targetCategory` varchar(30) NOT NULL,
  `title` varchar(120) DEFAULT NULL,
  `comment` varchar(400) DEFAULT NULL,
  `startCharacter` int(11) DEFAULT NULL,
  `endCharacter` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores comments to a part of the dossier for a category such as RESEARCH';

CREATE TABLE `answeredquiz` (
  `projectName` varchar(200) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `question` varchar(200) NOT NULL,
  `correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The answered quiz table holds the interpreted result of the quiz answer';

CREATE TABLE `assessmentmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `amSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Holds the peer assessement mechanism selected';

CREATE TABLE `categoriesselected` (
  `projectName` varchar(100) NOT NULL,
  `categorySelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='NOT IMPLEMENTED';

CREATE TABLE `contributionrating` (
  `projectName` varchar(200) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `fromPeer` varchar(100) NOT NULL,
  `dossier` int(11) NOT NULL,
  `eJournal` int(11) NOT NULL,
  `research` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='TODO @Axel plz comment';

CREATE TABLE `fullsubmissions` (
  `id` varchar(120) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user` varchar(100) NOT NULL,
  `text` mediumtext NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `feedbackUser` varchar(255) DEFAULT NULL,
  `finalized` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This holds the aggregated text of the dossier students should upload';

CREATE TABLE `grades` (
  `projectName` varchar(200) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `grade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Shows the grades that are calculated for a given student';

CREATE TABLE `groupfindingmechanismselected` (
  `projectName` varchar(100) NOT NULL,
  `gfmSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Groupfinding is done either automatically or manual';

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `chatRoomId` varchar(400) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='the groups that are created';

CREATE TABLE `groupuser` (
  `userEmail` varchar(255) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='n x m table for group and user';

CREATE TABLE `journals` (
  `id` varchar(100) NOT NULL,
  `userEmail` varchar(100) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `text` text NOT NULL,
  `visibility` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `open` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `peerassessmentworkanswer` (
  `propertieId` int(11) DEFAULT NULL,
  `answerIndex` int(11) DEFAULT NULL,
  `selectedAnswer` varchar(255) DEFAULT NULL,
  `userEmail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `peerassessmentworkproperties` (
  `id` int(11) NOT NULL,
  `scaleSize` int(11) DEFAULT NULL,
  `question` varchar(500) NOT NULL,
  `question_en` varchar(500) NOT NULL,
  `subvariable` varchar(100) DEFAULT NULL,
  `polarity` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `peerassessmentworkproperties` (`id`, `scaleSize`, `question`, `question_en`, `subvariable`, `polarity`) VALUES
                                                                                                                            (1, 5, 'Wie schätzt du die Mitarbeit des Gruppenmitglieds ein?', 'How do you assess the collaboration flow for your group member?', 'collabflow', 0),
                                                                                                                            (2, 5, 'Wie gut hat das gegenseitige Verständnis über eure Interaktion hinweg funktioniert?', 'How do you assess the sustained mutual understanding flow of your group member?', 'understanding', 0),
                                                                                                                            (3, 5, 'Wie schätzt du den Wissensaustausch mit deinem Gruppenmitglied ein?', 'How do you asses the knowledge exchange and giving explanations of your group member?', 'knowledgeexchange', 0),
                                                                                                                            (4, 5, 'Wie bewertest du die Argumentationen deines Gruppenmitglieds?', 'How do you assess the argumentation of your group member?', 'Argumentation', 0),
                                                                                                                            (5, 5, 'Wie beurteilst du das Zeitmanagement bzw. die Strukturierung von Problemen deines Gruppenmitglieds?', 'How do you assess the \"Structuring the problem solving process/time management\" of your group member?', 'timeManagement', 0),
                                                                                                                            (6, 5, 'Wie gut war die Kooperation deines Gruppenmitglieds durchschnittlich?', 'How do you assess the cooperative orientation of your group member?', 'cooperation', 0);

CREATE TABLE `phasesselected` (
  `projectName` varchar(100) NOT NULL,
  `phaseSelected` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='the phase that is selected out of Phase enum';

CREATE TABLE `profilequestionanswer` (
  `profileQuestionId` int(11) DEFAULT NULL,
  `answerIndex` int(11) DEFAULT NULL,
  `selectedAnswer` varchar(255) DEFAULT NULL,
  `userEmail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='the answer to a profile question needed for group finding algorithm';

CREATE TABLE `profilequestionoptions` (
  `id` int(11) NOT NULL,
  `profileQuestionId` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='the options for a profile question for thegroup finding algorithm';

CREATE TABLE `profilequestionrelations` (
  `firstQuestionId` int(11) DEFAULT NULL,
  `secondQuestionId` int(11) DEFAULT NULL,
  `relation` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This indicates if a profile question leads to homogenity in groups';

CREATE TABLE `profilequestions` (
  `id` int(11) NOT NULL,
  `scaleSize` int(11) DEFAULT NULL,
  `question` varchar(500) NOT NULL,
  `question_en` varchar(500) NOT NULL,
  `subvariable` varchar(100) DEFAULT NULL,
  `polarity` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='stores the questions needed for group finding';

INSERT INTO `profilequestions` (`id`, `scaleSize`, `question`, `question_en`, `subvariable`, `polarity`) VALUES
                                                                                                                (1, 5, 'Ich fühle mich wohl, so wie ich bin ', 'I like me the way I am ', 'Neurotizismus', 0),
                                                                                                                (2, 5, 'Ich habe häufig Stimmungsschwankungen', 'My mood changes a lot', 'Neurotizismus', 1),
                                                                                                                (3, 5, 'Ich mag mich selbst nicht', 'I don\'t like myself', 'Neurotizismus', 1),
                                                                                                                (4, 5, 'Ich gerate leicht in Panik', 'I panique a lot', 'Neurotizismus', 1),
                                                                                                                (5, 5, 'Ich bin oft völlig am Ende', 'I am totally finished a lot', 'Neurotizismus', 1),
                                                                                                                (6, 5, 'Ich fühle mich selten niedergeschlagen ', 'I feel rarely down ', 'Neurotizismus', 0),
                                                                                                                (7, 5, 'Ich bin zufrieden mit mir selbst ', 'I am content with myself ', 'Neurotizismus', 0),
                                                                                                                (8, 5, 'Ich fühle mich oft niedergeschlagen', 'I feel down a lot', 'Neurotizismus', 1),
                                                                                                                (9, 5, 'Ich gewinne leicht Freunde', 'I make friends easily', 'Extraversion', 1),
                                                                                                                (10, 5, 'Ich halte mich im Hintergrund', 'I always keep in the back ', 'Extraversion', 0),
                                                                                                                (11, 5, 'Ich weiß, wie ich Menschen für mich einnehmen kann', 'I know, how to charm people', 'Extraversion', 1),
                                                                                                                (12, 5, 'Ich mag es nicht, Aufmerksamkeit auf mich zu ziehen', 'I don\'t like to draw attention ', 'Extraversion', 0),
                                                                                                                (13, 5, 'Ich rede nicht viel', 'I don\'t talk too much ', 'Extraversion', 0),
                                                                                                                (14, 5, 'Ich fühle mich wohl, wenn ich unter Menschen bin', 'I feel well, when I am with people', 'Extraversion', 1),
                                                                                                                (15, 5, 'Ich bin der Stimmungsmacher auf Partys', 'I am the centre of attention at parties', 'Extraversion', 1),
                                                                                                                (16, 5, 'Ich bin nicht gesprächig', 'I am not very talkativ ', 'Extraversion', 0),
                                                                                                                (17, 5, 'Ich fühle mich wohl, so wie ich bin ', 'I like me the way I am ', 'Neurotizismus', 1),
                                                                                                                (18, 5, 'Ich habe häufig Stimmungsschwankungen', 'My mood changes a lot', 'Neurotizismus', 1),
                                                                                                                (19, 5, 'Ich mag mich selbst nicht', 'I don\'t like myself', 'Neurotizismus', 1),
                                                                                                                (20, 5, 'Ich gerate leicht in Panik', 'I panique a lot', 'Neurotizismus', 1),
                                                                                                                (21, 5, 'Ich bin oft völlig am Ende', 'I am totally finished a lot', 'Neurotizismus', 1),
                                                                                                                (22, 5, 'Ich fühle mich selten niedergeschlagen ', 'I feel rarely down ', 'Neurotizismus', 1),
                                                                                                                (23, 5, 'Ich bin zufrieden mit mir selbst ', 'I am content with myself ', 'Neurotizismus', 1),
                                                                                                                (24, 5, 'Ich fühle mich oft niedergeschlagen', 'I feel down a lot', 'Neurotizismus', 1),
                                                                                                                (25, 5, 'Ich gewinne leicht Freunde', 'I make friends easily', 'Extraversion', 1),
                                                                                                                (26, 5, 'Ich halte mich im Hintergrund', 'I always keep in the back ', 'Extraversion', 1),
                                                                                                                (27, 5, 'Ich weiß, wie ich Menschen für mich einnehmen kann', 'I know, how to charm people', 'Extraversion', 1),
                                                                                                                (28, 5, 'Ich mag es nicht, Aufmerksamkeit auf mich zu ziehen', 'I don\'t like to draw attention ', 'Extraversion', 1),
                                                                                                                (29, 5, 'Ich rede nicht viel', 'I don\'t talk too much ', 'Extraversion', 1),
                                                                                                                (30, 5, 'Ich fühle mich wohl, wenn ich unter Menschen bin', 'I feel well, when I am with people', 'Extraversion', 1),
                                                                                                                (31, 5, 'Ich bin der Stimmungsmacher auf Partys', 'I am the centre of attention at parties', 'Extraversion', 1),
                                                                                                                (32, 5, 'Ich bin nicht gesprächig', 'I am not very talkativ ', 'Extraversion', 1),
                                                                                                                (33, 5, 'Ich fühle mich wohl, so wie ich bin ', 'I like me the way I am ', 'Neurotizismus', 1),
                                                                                                                (34, 5, 'Ich habe häufig Stimmungsschwankungen', 'My mood changes a lot', 'Neurotizismus', 1),
                                                                                                                (35, 5, 'Ich mag mich selbst nicht', 'I don\'t like myself', 'Neurotizismus', 1),
                                                                                                                (36, 5, 'Ich gerate leicht in Panik', 'I panique a lot', 'Neurotizismus', 1),
                                                                                                                (37, 5, 'Ich bin oft völlig am Ende', 'I am totally finished a lot', 'Neurotizismus', 1),
                                                                                                                (38, 5, 'Ich fühle mich selten niedergeschlagen ', 'I feel rarely down ', 'Neurotizismus', 1),
                                                                                                                (39, 5, 'Ich bin zufrieden mit mir selbst ', 'I am content with myself ', 'Neurotizismus', 1),
                                                                                                                (40, 5, 'Ich fühle mich oft niedergeschlagen', 'I feel down a lot', 'Neurotizismus', 1),
                                                                                                                (41, 5, 'Ich gewinne leicht Freunde', 'I make friends easily', 'Extraversion', 1),
                                                                                                                (42, 5, 'Ich halte mich im Hintergrund', 'I always keep in the back ', 'Extraversion', 1),
                                                                                                                (43, 5, 'Ich weiß, wie ich Menschen für mich einnehmen kann', 'I know, how to charm people', 'Extraversion', 1),
                                                                                                                (44, 5, 'Ich mag es nicht, Aufmerksamkeit auf mich zu ziehen', 'I don\'t like to draw attention ', 'Extraversion', 1),
                                                                                                                (45, 5, 'Ich rede nicht viel', 'I don\'t talk too much ', 'Extraversion', 1),
                                                                                                                (46, 5, 'Ich fühle mich wohl, wenn ich unter Menschen bin', 'I feel well, when I am with people', 'Extraversion', 1),
                                                                                                                (47, 5, 'Ich bin der Stimmungsmacher auf Partys', 'I am the centre of attention at parties', 'Extraversion', 1),
                                                                                                                (48, 5, 'Ich bin nicht gesprächig', 'I am not very talkativ ', 'Extraversion', 1),
                                                                                                                (49, 5, 'Ich fühle mich wohl, so wie ich bin ', 'I like me the way I am ', 'Neurotizismus', 1),
                                                                                                                (50, 5, 'Ich habe häufig Stimmungsschwankungen', 'My mood changes a lot', 'Neurotizismus', 1),
                                                                                                                (51, 5, 'Ich mag mich selbst nicht', 'I don\'t like myself', 'Neurotizismus', 1),
                                                                                                                (52, 5, 'Ich gerate leicht in Panik', 'I panique a lot', 'Neurotizismus', 1),
                                                                                                                (53, 5, 'Ich bin oft völlig am Ende', 'I am totally finished a lot', 'Neurotizismus', 1),
                                                                                                                (54, 5, 'Ich fühle mich selten niedergeschlagen ', 'I feel rarely down ', 'Neurotizismus', 1),
                                                                                                                (55, 5, 'Ich bin zufrieden mit mir selbst ', 'I am content with myself ', 'Neurotizismus', 1),
                                                                                                                (56, 5, 'Ich fühle mich oft niedergeschlagen', 'I feel down a lot', 'Neurotizismus', 1),
                                                                                                                (57, 5, 'Ich gewinne leicht Freunde', 'I make friends easily', 'Extraversion', 1),
                                                                                                                (58, 5, 'Ich halte mich im Hintergrund', 'I always keep in the back ', 'Extraversion', 1),
                                                                                                                (59, 5, 'Ich weiß, wie ich Menschen für mich einnehmen kann', 'I know, how to charm people', 'Extraversion', 1),
                                                                                                                (60, 5, 'Ich mag es nicht, Aufmerksamkeit auf mich zu ziehen', 'I don\'t like to draw attention ', 'Extraversion', 1),
                                                                                                                (61, 5, 'Ich rede nicht viel', 'I don\'t talk too much ', 'Extraversion', 1),
                                                                                                                (62, 5, 'Ich fühle mich wohl, wenn ich unter Menschen bin', 'I feel well, when I am with people', 'Extraversion', 1),
                                                                                                                (63, 5, 'Ich bin der Stimmungsmacher auf Partys', 'I am the centre of attention at parties', 'Extraversion', 1),
                                                                                                                (64, 5, 'Ich bin nicht gesprächig', 'I am not very talkativ ', 'Extraversion', 1);

CREATE TABLE `profilevariables` (
  `variable` varchar(100) DEFAULT NULL,
  `subvariable` varchar(100) DEFAULT NULL,
  `subvariabledefinition` varchar(300) DEFAULT NULL,
  `variabledefinition` varchar(300) DEFAULT NULL,
  `context` varchar(300) DEFAULT NULL,
  `variableweight` float DEFAULT NULL,
  `subvariableweight` float DEFAULT NULL,
  `homogeneity` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `profilevariables` (`variable`, `subvariable`, `subvariabledefinition`, `variabledefinition`, `context`, `variableweight`, `subvariableweight`, `homogeneity`) VALUES
                                                                                                                                                                                  ('Persönlichkeit', 'Neurotizismus', NULL, 'egal', 'allgemein', 1, 1, 1),
                                                                                                                                                                                  ('Persönlichkeit', 'Extraversion', NULL, 'egal', 'allgemein', 1, 1, 0);

CREATE TABLE `projects` (
  `name` varchar(100) NOT NULL,
  `password` varchar(400) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `timecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(100) NOT NULL,
  `description` varchar(600) DEFAULT NULL,
  `phase` varchar(400) NOT NULL DEFAULT 'GroupFormation',
  `isSurvey` tinyint(1) DEFAULT NULL,
  `context` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='just a list of all the projects';

CREATE TABLE `projectuser` (
  `projectName` varchar(100) NOT NULL,
  `userEmail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='n x m for projects and user';

CREATE TABLE `quiz` (
  `author` varchar(100) NOT NULL,
  `projectName` varchar(200) NOT NULL,
  `question` varchar(200) NOT NULL,
  `mcType` varchar(400) NOT NULL,
  `answer` varchar(400) NOT NULL,
  `correct` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='lists the quizzes for the app';

CREATE TABLE `submissionpartbodyelements` (
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL,
  `startCharacter` int(11) NOT NULL,
  `endCharacter` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='holds the parts of a dossier that are annoated with category';

CREATE TABLE `submissionparts` (
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userEmail` varchar(255) NOT NULL,
  `fullSubmissionId` varchar(120) NOT NULL,
  `category` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='no idea what that it does but it is important';

CREATE TABLE `submissionuser` (
  `submissionId` varchar(400) DEFAULT NULL,
  `userEmail` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='no idea if that is needed. seems not be used';

CREATE TABLE `surveyitemsselected` (
  `projectname` varchar(100) DEFAULT NULL,
  `profilequestionid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tags` (
  `projectName` varchar(200) NOT NULL,
  `tag` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='lists some tags for the project in order to make it more searchable and for groupfinding';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The task table is important. It lists the actual state of the system associated to tasks';

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(255) NOT NULL,
  `rocketChatUserName` varchar(400) DEFAULT NULL,
  `isStudent` tinyint(1) DEFAULT '1',
  `discordid` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Just lists the users';

INSERT INTO `users` (`id`, `name`, `password`, `email`, `rocketChatUserName`, `isStudent`, `discordid`) VALUES
                                                                                                               (1, 'Julian Dehne', 'egal', 'julian.dehne@uni-potsdam.de', 'fltrailadmin', 0, NULL);

CREATE TABLE `workrating` (
  `projectName` varchar(200) NOT NULL,
  `userEmail` varchar(100) NOT NULL,
  `fromPeer` varchar(100) NOT NULL,
  `responsibility` int(11) NOT NULL,
  `partOfWork` int(11) NOT NULL,
  `cooperation` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `autonomous` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='@Axel plz comment';


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
  ADD UNIQUE KEY `fullsubmissions_user_projectName_uindex` (`user`,`projectName`),
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

ALTER TABLE `peerassessmentworkproperties`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

ALTER TABLE `phasesselected`
  ADD KEY `phasesselected_projectName_index` (`projectName`);

ALTER TABLE `profilequestionanswer`
  ADD KEY `profilequestionanswer_profilequestions_id_fk` (`profileQuestionId`),
  ADD KEY `profilequestionanswer_user_email_fk` (`userEmail`);

ALTER TABLE `profilequestionoptions`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `profilequestionrelations`
  ADD KEY `profilequestionrelations_profilequestions_id_fk` (`firstQuestionId`),
  ADD KEY `profilequestionrelations_profilequestions2_id_fk` (`secondQuestionId`);

ALTER TABLE `profilequestions`
  ADD PRIMARY KEY (`id`);

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

ALTER TABLE `surveyitemsselected`
  ADD KEY `surveyitemsselected_profilequestions_id_fk` (`profilequestionid`);

ALTER TABLE `tags`
  ADD KEY `tags_projectName_index` (`projectName`);

ALTER TABLE `tasks`
  ADD UNIQUE KEY `tasks_userEmail_projectName_taskName_uindex` (`userEmail`,`projectName`,`taskName`),
  ADD KEY `tasks_projects_name_fk` (`projectName`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

ALTER TABLE `workrating`
  ADD UNIQUE KEY `workrating_projectName_userEmail_fromPeer_uindex` (`projectName`,`userEmail`,`fromPeer`);


ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

ALTER TABLE `peerassessmentworkproperties`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `profilequestionoptions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `profilequestions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;


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

ALTER TABLE `profilequestionanswer`
  ADD CONSTRAINT `profilequestionanswer_profilequestions_id_fk` FOREIGN KEY (`profileQuestionId`) REFERENCES `profilequestions` (`id`),
  ADD CONSTRAINT `profilequestionanswer_user_email_fk` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`);

ALTER TABLE `profilequestionrelations`
  ADD CONSTRAINT `profilequestionrelations_profilequestions2_id_fk` FOREIGN KEY (`secondQuestionId`) REFERENCES `profilequestions` (`id`),
  ADD CONSTRAINT `profilequestionrelations_profilequestions_id_fk` FOREIGN KEY (`firstQuestionId`) REFERENCES `profilequestions` (`id`);

ALTER TABLE `projects`
  ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`author`) REFERENCES `users` (`email`);

ALTER TABLE `projectuser`
  ADD CONSTRAINT `projectuser_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE,
  ADD CONSTRAINT `projectuser_ibfk_2` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE;

ALTER TABLE `quiz`
  ADD CONSTRAINT `quiz_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `surveyitemsselected`
  ADD CONSTRAINT `surveyitemsselected_profilequestions_id_fk` FOREIGN KEY (`profilequestionid`) REFERENCES `profilequestions` (`id`);

ALTER TABLE `tags`
  ADD CONSTRAINT `tags_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `workrating`
  ADD CONSTRAINT `workrating_projects_name_fk` FOREIGN KEY (`projectName`) REFERENCES `projects` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
