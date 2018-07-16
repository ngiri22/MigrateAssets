##Migrate Assets Utility

This code reads the xml files from the xml folder and gets the needed elements.
Inserts the records into the database.
Moves the processed and errored out files to a different folder

#How to Run

It needs two input files in the classpath
- log4j.properties
- config.properties

Configure the properties in the above two files.

Call the jar file with following command.
- java -jar MigrateAssets.jar (Make sure the two config files are present in the current directory or in the classpath)

Check the database for records inserted and then the processed folder.

Logs are generated into file migration.log under the directory called logs/ (this is configurable using log4j)
