SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `fltrail_gf` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `fltrail_gf`;

create table evaluationsus
(
    project    varchar(100) not null,
    user       varchar(200) not null,
    questionId varchar(100) not null,
    rating     int          not null,
    docent     tinyint(1)   not null
);

create table journals
(
    id          varchar(100) not null
        primary key,
    userEmail   varchar(100) not null,
    projectName varchar(200) not null,
    text        text         not null,
    visibility  varchar(100) not null,
    category    varchar(100) not null,
    open        tinyint      not null
);

create table learninggoalstore
(
    text varchar(250) not null,
    constraint learningGoalStore_text_uindex
        unique (text)
)
    comment 'saves predefined learning goals';

alter table learninggoalstore
    add primary key (text);

create table mappedtasks
(
    id            int auto_increment
        primary key,
    subjectEmail  varchar(200) null,
    groupObjectId int          null,
    objectEmail   varchar(200) null,
    taskname      varchar(200) null,
    projectName   varchar(200) null
)
    comment 'This table holds the task mapping i.e. which user should give feedback to which groups products';

create table originalgroups
(
    userEmail               varchar(200) not null,
    projectName             varchar(200) not null,
    groupId                 int          not null,
    groupFormationMechanism varchar(50)  not null
);

create table peerassessmentworkanswer
(
    propertieId    int          null,
    answerIndex    int          null,
    selectedAnswer varchar(255) null,
    userEmail      varchar(255) not null
);

create table peerassessmentworkproperties
(
    id          int auto_increment,
    scaleSize   int          null,
    question    varchar(500) not null,
    question_en varchar(500) not null,
    subvariable varchar(100) null,
    polarity    tinyint      null,
    constraint id
        unique (id)
);

alter table peerassessmentworkproperties
    add primary key (id);

create table profilequestionoptions
(
    id                int auto_increment
        primary key,
    profileQuestionId int          null,
    name              varchar(255) null
)
    comment 'the options for a profile question for thegroup finding algorithm';

create table profilequestions
(
    id          int auto_increment
        primary key,
    scaleSize   int          null,
    question    varchar(500) not null,
    question_en varchar(500) not null,
    subvariable varchar(100) null,
    polarity    tinyint(1)   null
)
    comment 'stores the questions needed for group finding';

create table profilequestionrelations
(
    firstQuestionId  int          null,
    secondQuestionId int          null,
    relation         varchar(200) null,
    constraint profilequestionrelations_profilequestions2_id_fk
        foreign key (secondQuestionId) references profilequestions (id),
    constraint profilequestionrelations_profilequestions_id_fk
        foreign key (firstQuestionId) references profilequestions (id)
)
    comment 'This indicates if a profile question leads to homogenity in groups';

create table profilevariables
(
    variable              varchar(100) null,
    subvariable           varchar(100) null,
    subvariabledefinition varchar(300) null,
    variabledefinition    varchar(300) null,
    context               varchar(300) null,
    variableweight        float        null,
    subvariableweight     float        null,
    homogeneity           tinyint(1)   null
);

create table reflectionquestionsstore
(
    id           varchar(200) not null
        primary key,
    question     varchar(250) not null,
    learningGoal varchar(250) not null
)
    comment 'holds all predefined reflection questions.';

create index reflectionquestionsstore_learninggoalstore_text_fk
    on reflectionquestionsstore (learningGoal);

create table submissionpartbodyelements
(
    fullSubmissionId varchar(120) not null,
    category         varchar(30)  not null,
    startCharacter   int          not null,
    endCharacter     int          not null,
    primary key (fullSubmissionId, category, startCharacter, endCharacter)
)
    comment 'holds the parts of a dossier that are annoated with category';

create table submissionparts
(
    timestamp        timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    groupId          int                                 not null,
    fullSubmissionId varchar(120)                        not null,
    category         varchar(30)                         not null,
    primary key (fullSubmissionId, category)
)
    comment 'no idea what that it does but it is important';

create table surveyitemsselected
(
    projectname       varchar(100) null,
    profilequestionid int          null,
    constraint surveyitemsselected_profilequestions_id_fk
        foreign key (profilequestionid) references profilequestions (id)
);

create table tasklock
(
    id        int auto_increment,
    taskName  varchar(100)                        not null,
    groupId   int                                 not null,
    owner     varchar(255)                        not null,
    timeStamp timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint id
        unique (id)
);

create index tasklock_taskName_groupId_index
    on tasklock (taskName, groupId);

create table users
(
    id                 int auto_increment
        primary key,
    name               varchar(100)         not null,
    password           varchar(200)         not null,
    email              varchar(255)         not null,
    rocketChatUserName varchar(400)         null,
    isStudent          tinyint(1) default 1 null,
    discordid          varchar(100)         null,
    constraint email
        unique (email)
)
    comment 'Just lists the users';

create table profilequestionanswer
(
    profileQuestionId int          null,
    answerIndex       int          null,
    selectedAnswer    varchar(255) null,
    userEmail         varchar(255) not null,
    constraint profilequestionanswer_profilequestions_id_fk
        foreign key (profileQuestionId) references profilequestions (id),
    constraint profilequestionanswer_user_email_fk
        foreign key (userEmail) references users (email)
)
    comment 'the answer to a profile question needed for group finding algorithm';

create table projects
(
    name        varchar(100)                           not null,
    password    varchar(400)                           not null,
    active      tinyint(1)                             not null,
    timecreated timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    author      varchar(100)                           not null,
    description varchar(600)                           null,
    phase       varchar(400) default 'GroupFormation'  not null,
    isSurvey    tinyint(1)                             null,
    context     varchar(100)                           null,
    groupSize   int          default 0                 not null,
    constraint name
        unique (name),
    constraint projects_ibfk_1
        foreign key (author) references users (email)
)
    comment 'just a list of all the projects';

create table answeredquiz
(
    projectName varchar(200) not null,
    userName    varchar(100) not null,
    question    varchar(200) not null,
    correct     tinyint      not null,
    constraint answeredquiz_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'The answered quiz table holds the interpreted result of the quiz answer';

create table assessmentmechanismselected
(
    projectName varchar(100) not null,
    amSelected  varchar(200) not null,
    constraint assessmentmechanismselected_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'Holds the peer assessement mechanism selected';

create table categoriesselected
(
    projectName      varchar(100) not null,
    categorySelected varchar(200) not null,
    constraint categoriesselected_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'NOT IMPLEMENTED';

create table contributionrating
(
    projectName varchar(200) not null,
    userName    varchar(100) null,
    fromPeer    varchar(100) null,
    groupId     int          null,
    fileRole    varchar(100) not null,
    fromTeacher varchar(100) null,
    rating      int          not null,
    constraint contributionrating_projectName_userName_fromPeer_uindex
        unique (projectName, userName, fromPeer),
    constraint contributionrating_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'Holds the quantitative peer assessment regarding the uploads.';

create table grades
(
    projectName varchar(200) not null,
    userEmail   varchar(100) not null,
    grade       double       not null,
    constraint grades_projectName_userEmail_uindex
        unique (projectName, userEmail),
    constraint grades_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'Shows the grades that are calculated for a given student';

create table groupfindingmechanismselected
(
    projectName varchar(100) not null,
    gfmSelected varchar(200) not null,
    constraint groupfindingmechanismselected_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'Groupfinding is done either automatically or manual';

create table `groups`
(
    id          int auto_increment
        primary key,
    projectName varchar(200) not null,
    chatRoomId  varchar(400) null,
    name        varchar(255) null,
    constraint groups_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'the groups that are created';

create table fullsubmissions
(
    id            varchar(120)                        not null,
    version       int                                 not null,
    timestamp     timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    groupId       int                                 null,
    header        varchar(100)                        null,
    text          mediumtext                          not null,
    projectName   varchar(200)                        not null,
    feedbackGroup int                                 null,
    finalized     tinyint(1)                          null,
    fileRole      varchar(200)                        not null,
    userEmail     varchar(255)                        null,
    visibility    varchar(200)                        not null,
    constraint fullsubmissions_id_uindex
        unique (id),
    constraint fullsubmissions_groups_id_fk
        foreign key (groupId) references `groups` (id)
            on update cascade on delete cascade,
    constraint fullsubmissions_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade,
    constraint fullsubmissions_users_email_fk
        foreign key (userEmail) references users (email)
            on update cascade on delete cascade
)
    comment 'This holds the aggregated text of the dossier students should upload';

create index fullsubmissions_contribution_category_fk
    on fullsubmissions (fileRole);

create index fullsubmissions_version_fk
    on fullsubmissions (version);

alter table fullsubmissions
    add primary key (id);

create table annotations
(
    id             varchar(120)                        not null
        primary key,
    timestamp      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    userEmail      varchar(255)                        null,
    targetId       varchar(120)                        null,
    targetCategory varchar(30)                         not null,
    title          varchar(120)                        null,
    comment        varchar(400)                        null,
    startCharacter int                                 null,
    endCharacter   int                                 null,
    constraint annotations_fullsubmissions_id_fk
        foreign key (targetId) references fullsubmissions (id)
            on update cascade on delete cascade
)
    comment 'Stores comments to a part of the dossier for a category such as RESEARCH';

create table contributionfeedback
(
    id                         varchar(120)                        not null,
    fullsubmissionId           varchar(120)                        null,
    fullSubmissionPartCategory varchar(120)                        null,
    text                       mediumtext                          null,
    userEmail                  varchar(255)                        not null,
    timestamp                  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    groupId                    int                                 null,
    constraint feedback_id_uindex
        unique (id),
    constraint contributionfeedback_groups_id_fk
        foreign key (groupId) references `groups` (id)
            on update cascade on delete cascade,
    constraint feedback_fullsubmissions_id_fk
        foreign key (fullsubmissionId) references fullsubmissions (id)
            on update cascade on delete cascade
)
    comment 'This table saves feedback for contributions';

alter table contributionfeedback
    add primary key (id);

create table groupuser
(
    userEmail varchar(255) not null,
    groupId   int          not null,
    constraint groupuser_ibfk_1
        foreign key (userEmail) references users (email)
            on delete cascade,
    constraint groupuser_ibfk_2
        foreign key (groupId) references `groups` (id)
            on delete cascade
)
    comment 'n x m table for group and user';

create index groupId
    on groupuser (groupId);

create index userEmail
    on groupuser (userEmail);

create table largefilestorage
(
    id           int auto_increment
        primary key,
    groupId      int          null,
    userEmail    varchar(255) null,
    projectName  varchar(100) not null,
    filelocation varchar(100) not null,
    filerole     varchar(100) not null,
    filename     varchar(100) not null,
    constraint largefilestorage_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade,
    constraint largefilestorage_users_email_fk
        foreign key (userEmail) references users (email)
            on update cascade on delete cascade
);

create table phasesselected
(
    projectName   varchar(100) not null,
    phaseSelected varchar(200) not null,
    constraint phasesselected_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'the phase that is selected out of Phase enum';

create index phasesselected_projectName_index
    on phasesselected (projectName);

create index author
    on projects (author);

create table projectuser
(
    projectName varchar(100) not null,
    userEmail   varchar(255) not null,
    constraint projectuser_ibfk_1
        foreign key (userEmail) references users (email)
            on delete cascade,
    constraint projectuser_ibfk_2
        foreign key (projectName) references projects (name)
            on delete cascade
)
    comment 'n x m for projects and user';

create index projectName
    on projectuser (projectName);

create index userEmail
    on projectuser (userEmail);

create table quiz
(
    author      varchar(100) not null,
    projectName varchar(200) not null,
    question    varchar(200) not null,
    mcType      varchar(400) not null,
    answer      varchar(400) not null,
    correct     tinyint(1)   not null,
    constraint quiz_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'lists the quizzes for the app';

create index quiz_question_projectName_author_index
    on quiz (question, projectName, author);

create table selectedlearninggoals
(
    id          varchar(200) not null
        primary key,
    text        varchar(400) not null,
    projectName varchar(255) not null,
    constraint learninggoals_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'holds all learning goals';

create table selectedreflectionquestions
(
    id             varchar(200) not null,
    learningGoalId varchar(200) not null,
    question       text         not null,
    constraint selected_reflection_questions_id_uindex
        unique (id),
    constraint selectedreflectionquestions_learninggoals_id_fk
        foreign key (learningGoalId) references selectedlearninggoals (id)
            on update cascade on delete cascade
)
    comment 'Saves all selected reflection questions by docent';

alter table selectedreflectionquestions
    add primary key (id);

create table reflectionquestionanswers
(
    id                           varchar(100) not null
        primary key,
    selectedReflectionQuestionId varchar(200) not null,
    fullSubmissionId             varchar(120) null,
    constraint reflexionquestions_fullSubmissionId_uindex
        unique (fullSubmissionId),
    constraint reflectionquestions_selectedreflectionquestions_id_fk
        foreign key (selectedReflectionQuestionId) references selectedreflectionquestions (id)
)
    comment 'holds all reflection questions students have to answer or had answered';

create table tags
(
    projectName varchar(200) not null,
    tag         varchar(400) not null,
    constraint tags_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'lists some tags for the project in order to make it more searchable and for groupfinding';

create index tags_projectName_index
    on tags (projectName);

create table tasks
(
    userEmail   varchar(255) not null,
    projectName varchar(200) not null,
    taskName    varchar(100) null,
    groupTask   bigint       null,
    importance  varchar(100) null,
    progress    varchar(100) null,
    phase       varchar(100) null,
    created     bigint       not null,
    due         timestamp    null,
    taskMode2   varchar(100) null,
    taskMode3   varchar(100) null,
    taskMode    varchar(100) null,
    constraint tasks_userEmail_projectName_taskName_uindex
        unique (userEmail, projectName, taskName, groupTask),
    constraint tasks_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'The task table is important. It lists the actual state of the system associated to tasks';

create table workrating
(
    projectName varchar(200) not null,
    userEmail   varchar(100) not null,
    fromPeer    varchar(100) not null,
    rating      int          not null,
    itemName    varchar(100) not null,
    constraint workrating_projectName_userEmail_fromPeer_uindex
        unique (projectName, userEmail, fromPeer, itemName),
    constraint workrating_projects_name_fk
        foreign key (projectName) references projects (name)
            on update cascade on delete cascade
)
    comment 'Peers rate one another in different dimensions defined in "itemName". Its a part of assessment.';



ALTER TABLE `reflectionquestionanswers` ADD CONSTRAINT `fullsubmissionIdKey` FOREIGN KEY (`fullSubmissionId`) REFERENCES `fullsubmissions`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;