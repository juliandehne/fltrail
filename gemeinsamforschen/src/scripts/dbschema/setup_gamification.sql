-- This script initializes tables to be used for gamification

CREATE DATABASE IF NOT EXISTS `fltrail`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;
USE `fltrail`;

---- events
-- events can be
--  * writing a comment
--  * taking a quiz
--  * creating a quiz
--  * adding a part of the journal
--  * completing the journal
--  * doing peer assessment (actively and passively)
CREATE TABLE if not exists events
(
  eventId     varchar(400) NOT NULL,
  eventType   varchar(400) NOT NULL,
  studentId   varchar(400) NOT NULL,
  projectId   varchar(400) NOT NULL,
  title       varchar(400) NOT NULL,
  description varchar(400) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

---- tasks
-- a task is something a student is obliged to do, such as
--  * uploading parts of the journal
--  * completing the journal
--  * giving at least one feedback to another student
--  * absolving peer assessment

CREATE TABLE if not exists tasks
(
  taskId     varchar(400) NOT NULL,
  taskType   varchar(400) NOT NULL,
  projectId   varchar(400) NOT NULL,
  title       varchar(400) NOT NULL,
  description varchar(400) NOT NULL,
  state       varchar(400) NOT NULL -- should be "open" or "closed"
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--- achievements
CREATE TABLE if not exists achievements
(
  authorEmail varchar(400) NOT NULL,
  projectId varchar(400) NOT NULL,
  question varchar(400) NOT NULL,
  mcType varchar(400) NOT NULL,
  answer varchar(400) NOT NULL,
  correct tinyint(1) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
