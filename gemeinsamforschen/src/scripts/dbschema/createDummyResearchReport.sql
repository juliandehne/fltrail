drop TABLE `researchReport`;

CREATE TABLE researchReport (
   `id` varchar(400) NOT NULL,
   `authorEmail` varchar(400) NOT NULL,
   `title` varchar(400) NOT NULL,
   `method` varchar(10000) NOT NULL,
   `research` varchar(10000) NOT NULL,
   `researchResult` varchar(10000) NOT NULL,
   `evaluation` varchar(10000) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO researchReport (`id`, `authorEmail`, `title`, `method`, `research`, `researchResult`, `evaluation`) VALUES
  ('id1', 'author1', 'title1', 'method1', 'research1', 'researchResult1', 'evaluation1');

INSERT INTO researchReport (`id`, `authorEmail`, `title`, `method`, `research`, `researchResult`, `evaluation`) VALUES
  ('id2', 'author2', 'title2', 'method2', 'research2', 'researchResult2', 'evaluation2');

INSERT INTO researchReport (`id`, `authorEmail`, `title`, `method`, `research`, `researchResult`, `evaluation`) VALUES
  ('id3', 'author3', 'title3', 'method3', 'research3', 'researchResult3', 'evaluation3');

INSERT INTO researchReport (`id`, `authorEmail`, `title`, `method`, `research`, `researchResult`, `evaluation`) VALUES
  ('id4', 'author4', 'title4', 'method4', 'research4', 'researchResult4', 'evaluation4');

INSERT INTO researchReport (`id`, `authorEmail`, `title`, `method`, `research`, `researchResult`, `evaluation`) VALUES
  ('id5', 'author5', 'title5', 'method5', 'research5', 'researchResult5', 'evaluation5');