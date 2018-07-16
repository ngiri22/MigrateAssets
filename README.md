##Migrate Assets Utility
This code reads the xml files from the xml folder and gets the needed elements.

Inserts the records into the database.

Moves the processed and errored out files to a different folder

#How to Run
The main class is MigrateAssets.java
It needs to input files in the classpath
- log4j.properties
- config.properties

Configure the different properties in the above two files.
file.input.batch.count param tells how many assets should be processed.

Call the jar file with following command.
java -jar MigrateAssets.jar (Make sure the two config files are in the classpath

Check the Db for records inserted and then the processed folder.