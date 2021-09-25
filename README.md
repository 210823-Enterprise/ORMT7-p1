# ORMT7-p1
Project 1 repo for team 7

## Description:
  This ORM uses CRUD methods created in java to interact with a database without the hassle of SQL or connection management.
## Tech Stack:
  * PostgreSQL - version 42.2.18  
  * Java - version 8.0  
  * Apache commons - version 2.1.1  
  * JUnit - version 4.13.1
  * log4j - version 1.2.17
## Features:
  * No need for database specific language, just good old fashioned java.
  * Simple Annotations that are easy to use.
  * A simple API that can be used as a dependency.
  * Logging of everything that happens.
  * Ability to filter tables by multiple requirements.
## To Do:
  * Create transactions so that databases can be roled back, preventing users from having to manually undo the changes they made.
  * Format printing so that it looks pretty.
  * Ability to create files with custom tables based on input parameters.
  * Minimize calls to database by removing possible repeats.
## Getting Started:
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210517-Enterprise/*your-repo*_p1.git
  cd *your-repo*_p1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>*your-repo*_p1</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>

```
