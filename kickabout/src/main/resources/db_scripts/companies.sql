INSERT INTO `companies` (`id`, `name`, `account_id`, `company_details_id`) VALUES
	(1, 'Bury College', 2, NULL),
	(2, 'Bury Council', 2, NULL),
	(3, 'Play Football', 2, NULL),
	(4, 'Power League', 2, NULL),
	(5, 'Manchester City Council', 2, NULL),
	(6, 'Salford Council', 2, NULL),
	(7, 'Salford University', 2, NULL);
	
INSERT INTO `pitch_locations` (`id`, `address_line_1`, `address_line_2`, `city`, `county`, `email`, `post_code`, `telephone`, `company_id`) VALUES
	(1, 'Bury College', 'Market Street', 'Bury', 'Greater Manchester', 'bury@college.com', 'BL9 0DB', '0161 270 4280', 1),
	(2, 'Bury Council', 'Bury Street', 'Bury', 'Greater Manchester', 'bury@council.com', 'BL9 2UY', '0161 234 5698', 2),
	(3, 'Play Football', 'Manchester Street', 'Manchester', 'Greater Manchester', 'play@playfootball.com', 'M45 7ND', '0161 773 1890', 3),
	(4, 'Power League', 'Salford Road', 'Manchester', 'Greater Manchester', 'powerleague@football.com', 'M45 4LA', '0161 766 0834', 4),
	(5, 'Castlebrook High School', 'Parr Lane', 'Bury', 'Greater Manchester', 'castlebrookhigh@sports.com', 'BL9 8LP', '0161 270 4532', 2),
	(6, 'Goshen Sports Centre', 'Tennyson Avenue', 'Bury', 'Greater Manchester', 'goshen@sportscentre.com', 'BL9 9RG', '0161 265 5213', 2),
	(7, 'Philips High School', 'Higher Lane', 'Manchester', 'Greater Manchester', 'philipshigh@sports.com', 'M45 7PH', '0161 773 9034', 2),
	(8, 'Manchester Academy', 'Moss Lane East', 'Manchester', 'Greater Manchester', 'manchesteracademy@leisure.com', 'M14 4PX', '0161 766 6682', 5),
	(9, 'Trinity Sports Centre', 'Cambridge Street', 'Manchester', 'Greater Manchester', 'trinitysports@centre.com', 'M15 6HP', '0161 723 1116', 5),
	(10, 'Salford University', 'Cromwell Road', 'Salford', 'Greater Manchester', 'salford@university.com', 'M6 6LB', '0161 724 5218', 7),
	(11, 'Lower Broughton Playing Fields', 'Lower Broughton Road', 'Salford', 'Greater Manchester', 'broughton@playingfields.com', 'M7 2HR', '0161 777 8721', 6);