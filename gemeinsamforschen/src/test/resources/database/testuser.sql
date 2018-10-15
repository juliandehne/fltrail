INSERT IGNORE INTO users (name, password, email, rocketChatId, rocketChatAuthToken, isStudent) values ("Julian Dehne",
"egal", "vodkas@yolo.com", "egal", "egal", 1);

INSERT IGNORE INTO users (name, password, email, rocketChatId, rocketChatAuthToken, isStudent) values ("Axel Wiepke",
"egal", "vodkass@yolo.com", "egal", "egal", 1);

INSERT IGNORE INTO users (name, password, email, rocketChatId, rocketChatAuthToken, isStudent) values ("Herr Lehrer",
"egal", "vodka@yolo.com", "egal", "egal", 0);


INSERT IGNORE INTO projects (name, password, active, timecreated, author, adminPassword, phase) VALUES ("test1", "", 1,
current_timestamp, "vodka@yolo.com", "", "GroupFormation" );

INSERT IGNORE INTO projects (name, password, active, timecreated, author, adminPassword, phase) VALUES ("test2", "", 1,
current_timestamp, "vodka@yolo.com", "", "GroupFormation" );

insert IGNORE into projectuser (projectName, userEmail) VALUES ("test1", "vodkas@yolo.com");
insert IGNORE into projectuser (projectName, userEmail) VALUES ("test1", "vodkass@yolo.com");

insert IGNORE into projectuser (projectName, userEmail) VALUES ("test2", "vodkas@yolo.com");
insert IGNORE into projectuser (projectName, userEmail) VALUES ("test2", "vodkass@yolo.com");