
drop TABLE `users`;

CREATE TABLE `users` (
  `name` varchar(400) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(400) NOT NULL,
  `token` varchar(800) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent1', 'egal', 'test1@uni.de', 'test1');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent1', 'egal', 'test1@uni.de', 'test1');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent2', 'egal', 'test2@uni.de', 'test2');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent3', 'egal', 'test3@uni.de', 'test3');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent4', 'egal', 'test4@uni.de', 'test4');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent5', 'egal', 'test5@uni.de', 'test5');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent6', 'egal', 'test6@uni.de', 'test6');

INSERT INTO `users` (`name`, `password`, `email`, `token`) VALUES
  ('teststudent7', 'egal', 'test7@uni.de', 'test7');
