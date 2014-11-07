CREATE TABLE Users ( UserID BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
					 Email VARCHAR(64) NOT NULL,
					 Password VARCHAR(320) NOT NULL,
					 Firstname VARCHAR(32) NOT NULL,
					 Lastname VARCHAR(32) NOT NULL
				   );
				   
CREATE TABLE Downloads ( DownloadID BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
						 Title VARCHAR(32) NOT NULL,
						 StartDate DATE,
						 EndDate DATE,
						 Data BLOB NOT NULL
					   );

CREATE TABLE UsersDownloads ( UserID BIGINT(11) NOT NULL,
                              DownloadID BIGINT(11) NOT NULL,
                              PRIMARY KEY (UserID, DownloadID),
                              FOREIGN KEY (UserID) REFERENCES Users(UserID),
                              FOREIGN KEY (DownloadID) REFERENCES Downloads(DownloadID)
                            );