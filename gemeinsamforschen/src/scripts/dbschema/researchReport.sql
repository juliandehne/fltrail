CREATE TABLE `researchReport` (
  `reportID` varchar(400) NOT NULL,
  `author` varchar(100) NOT NULL,
  `title` varchar(400) NOT NULL,
  `method` text NOT NULL,
  `research` text NOT NULL,
  `question` text NOT NULL,
  `concept` text NOT NULL,
  `do` text NOT NULL,
  `evaluation` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `biblography` (
  `reportID` varchar(400) NOT NULL,
  `source` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
