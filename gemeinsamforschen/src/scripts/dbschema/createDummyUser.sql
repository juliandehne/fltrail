
drop TABLE `users`;

CREATE TABLE if not exists `users` (
  `name`                varchar(100) NOT NULL,
  `password`            varchar(200) NOT NULL,
  `email`               varchar(255) NOT NULL,
  `token`               varchar(800) NOT NULL,
  `rocketChatId`        varchar(400) NOT NULL,
  `rocketChatAuthToken` varchar(800) NOT NULL,
  UNIQUE (email)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;


INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatId`, `rocketChatAuthToken`) VALUES
  ('teststudent1', 'egal', 'test1@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatId`, `rocketChatAuthToken`) VALUES
  ('teststudent1', 'egal', 'test2@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatId`, `rocketChatAuthToken`) VALUES
  ('teststudent1', 'egal', 'test3@uni.de', 'test1','1','abc');

INSERT INTO `users` (`name`, `password`, `email`, `token`, `rocketChatId`, `rocketChatAuthToken`) VALUES
  ('teststudent1', 'egal', 'test4@uni.de', 'test1','1','abc');