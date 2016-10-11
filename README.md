[![Build Status](https://travis-ci.org/dannil/HttpDownloader.svg?branch=dev)](https://travis-ci.org/dannil/HttpDownloader)
[![Codacy Grade Badge](https://api.codacy.com/project/badge/grade/00c9801017e64464ad853a618143ff98)](https://www.codacy.com/app/dannil/HttpDownloader)
[![Codacy Coverage Badge](https://api.codacy.com/project/badge/coverage/00c9801017e64464ad853a618143ff98)](https://www.codacy.com/app/dannil/HttpDownloader)

# HttpDownloader

A server application for automating downloads.

## Install

Because this application is in very early development, no generic installer exists. The minimum required steps to try out the application is listed below.

1. Clone the repository from the master branch if you'd like the stable version (or if you're brave and want the bleeding edge, clone the dev branch). 
2. Configure your own database with the attached SQL-statements present inside the resources-folder.
3. Connect the application to the database by adding the file persistence.xml inside the folder META-INF (preferably, but you could place it anywhere you want) 
and configure this file with your credentials. For more info, see [JBoss - The persistence.xml file](https://docs.jboss.org/jbossas/docs/Server_Configuration_Guide/4/html/ch01s02s01.html)