CREATE TABLE if not exists researchReport (
   `id` varchar(400) NOT NULL,
   `author` varchar(400) NOT NULL,
   `title` varchar(400) NOT NULL,
   `method` varchar(10000) NOT NULL,
   `research` varchar(10000) NOT NULL,
   `question` varchar(10000) NOT NULL,
   `concept` varchar(10000) NOT NULL,
   `done` varchar(10000) NOT NULL,
   `evaluation` varchar(10000) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists biblography (
   `id` varchar(400) NOT NULL,
   `source` varchar(400) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;