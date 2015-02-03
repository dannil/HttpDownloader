[![Build Status](https://travis-ci.org/dannil/HttpDownloader.svg?branch=dev)](https://travis-ci.org/dannil/HttpDownloader)
[![Coverage Status](https://coveralls.io/repos/dannil/HttpDownloader/badge.svg)](https://coveralls.io/r/dannil/HttpDownloader)
[![Dependency Status](https://www.versioneye.com/user/projects/546cceb681010618c7000571/badge.svg?style=flat)](https://www.versioneye.com/user/projects/546cceb681010618c7000571)

# HttpDownloader

A server application for automating downloads.

## Install

Because this application is in very early development, no generic installer exists. The minimum required steps to try out the application is listed below.

1. Clone the repository from the master branch if you'd like the stable version (or if you're brave and want the bleeding edge, clone the dev branch). 
2. Configure your own database with the attached SQL-statements present inside the resources-folder.
3. Connect the application to the database by adding the file persistence.xml inside the folder META-INF (preferably, but you could place it anywhere you want) 
and configure this file with your credentials. For more info, see [JBoss - The persistence.xml file](https://docs.jboss.org/jbossas/docs/Server_Configuration_Guide/4/html/ch01s02s01.html)