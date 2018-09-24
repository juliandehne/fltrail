/*
  Make sure all tables are created with fltrail.sql
  Functions with empty grouptable only (because of autoincrement id of group)
 */

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent1', 'egal', 'test1@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent2', 'egal', 'test2@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent3', 'egal', 'test3@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent4', 'egal', 'test4@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent5', 'egal', 'test5@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent6', 'egal', 'test6@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent7', 'egal', 'test7@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatUserId`, `rocketChatAuthToken`) VALUES
  ('teststudent8', 'egal', 'test8@uni.de', 'test1','1','abc');

INSERT INTO  `projects` (`id`, `password`,`active`,`timecreated`,`author`,`adminpassword`,`token`,`phase`) VALUES
  ('1','123',1,current_timestamp,'Julian','123','abc','CourseCreation');

INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test1@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test2@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test3@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test4@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test5@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test6@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test7@uni.de');
INSERT INTO `projectuser` (`projectId`,`userId`) VALUES ('1','test8@uni.de');

INSERT INTO groups (projectId, chatRoomId) VALUES ('1','1');
INSERT INTO groups (projectId, chatRoomId) VALUES ('1','2');
INSERT INTO groups (projectId, chatRoomId) VALUES ('1','3');
INSERT INTO groups (projectId, chatRoomId) VALUES ('1','4');

INSERT INTO groupuser (userEmail, groupId) VALUES ('test1@uni.de','1');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test2@uni.de','2');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test3@uni.de','3');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test4@uni.de','4');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test5@uni.de','1');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test6@uni.de','2');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test7@uni.de','3');
INSERT INTO groupuser (userEmail, groupId) VALUES ('test8@uni.de','4');