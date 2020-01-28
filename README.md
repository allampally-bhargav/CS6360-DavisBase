# CS6360-DavisBase
DavisBase is a primary relational database system developed as part of Data Base Design course.


DavisBase supports only one database and one user which are set up by default. User can create multiple tables in the database. Supported commands are as follows.

•	SHOW TABLES;    
-	  Displays all the tables in the database.
•	CREATE TABLE table_name (<column_name datatype>);       
-	   Creates a new table in the database.
•	INSERT INTO table_name (column1, column2...) VALUES (value1, value2,..);         
-	 Inserts a new record into the table.
•	DELETE FROM TABLE table_name WHERE row_id = key_value;     
-	Deletes a record from the table whose rowid is <key_value>.
•	UPDATE table_name SET column_name = value WHERE condition; 
-	Modifies the records in the table.
•	CREATE INDEX ON table_name (column_name);                  
-	Creates an index for the specified column in the table.
•	SELECT * FROM table_name;                                  
-	Displays all records in the table.
•	SELECT * FROM table_name WHERE column_name operator value; 
-	Displays all the records of the table where the given condition is satisfied.
•	DROP TABLE table_name;                                   
-	  Removes table data and its schema.
•	VERSION;                                               
-	   Displays the program version.
•	HELP;                                                  
-	    Displays this help information.
•	EXIT;                                                
-	     Exits from DavisBase.

All the commands are case insensitive. For demonstration, load the folder DavisBase_Team_Black in Eclipse project workspace and run the file "DavisBase.java" as a Java application.
