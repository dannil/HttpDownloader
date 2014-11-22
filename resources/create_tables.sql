CREATE TABLE Users ( UserID BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
					 Email VARCHAR(64) NOT NULL,
					 Password VARCHAR(320) NOT NULL,
					 Firstname VARCHAR(32) NOT NULL,
					 Lastname VARCHAR(32) NOT NULL
				   );
				   
CREATE TABLE Downloads ( DownloadID BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
						 UserID BIGINT(11) NOT NULL,
						 Title VARCHAR(32) NOT NULL,
						 URL VARCHAR(512) NOT NULL,
						 StartDate DATETIME,
						 EndDate DATETIME,
						 FOREIGN KEY (UserID) REFERENCES Users(UserID)
						 	ON UPDATE CASCADE
						 	ON DELETE CASCADE
					   );