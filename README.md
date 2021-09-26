# ORMT7-p1
Created by: Noah Gaston,

## Description:
  This ORM uses CRUD methods created in java to interact with a database without the hassle of SQL or connection management.
## Tech Stack:
  * PostgreSQL - version 42.2.18  
  * Java - version 8.0  
  * Apache commons - version 2.1.1  
  * log4j - version 1.2.17
## Features:
  * No need for database specific language, just good old fashioned java.
  * Simple Annotations that are easy to use.
  * A simple API that can be used as a dependency.
  * Logging of everything that happens.
  * Ability to filter tables by multiple requirements.
## To Do:
  * Create transactions so that databases can be roled back, preventing users from having to manually undo the changes they made.
  * Ability to create files with custom tables based on input parameters.
  * Come up with a better name than ORMT7-p1.
  * Object cacheing.
  * Connection pooling.
## Getting Started:
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210823-Enterprise/ORMT7-p1.git
  cd ORMT7-p1/ORMLite
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.team7</groupId>
    <artifactId>ORMLite</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>

```
Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
  url=path to database
  username=username of database
  password=password of database  
  ```
## Usage
  ### Annotating Classes:
     All classes which represent objects in database must be annotated.
   - #### @Column(name = "column_name")  
      - Indicates that the Annotated field is a column in the table with the name 'column_name'  
   - #### @Id(name = "column_name") 
      - Indicates that the annotated field is the primary key for the table.
   - #### @Entity(table_name = "name of the table")
      - Indicates that the annotated class contains data for a database interaction.
   - #### @JoinColumn(name = "column_name")
      - Indicates that the annotated class contains data for a column that joins two tables.
  ### User API:
   - #### public Map<Integer, Map<String, String>> readFromDb(Class<?> obj, Connection conn, Map<String, String> cont)
      - Reads the database from obj using conn and returns a Map containing all of the values found with those parameters.
   - #### public Map<String, String> getEntry(Class<?> obj, Connection conn, String id)
      - Reads a database for a row with primary key id and returns that row in a map.
   - #### public Map<Integer, Map<String, String>> getAll(Class<?> obj, Connection conn)
      - Reads a database from obj and returns a map that maps each row with another map containing each row's column contents.

