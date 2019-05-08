#This code has two capabilities.

### 1. Capture Metadata from Asset xmls.

- This code reads the xml files from the xml folder and gets the needed elements.
- Inserts the records into the database.
- Moves the processed and errored out files to a different folder

### 2. Fix File renaming issue on download.
- This code gets the files which are having a different Name and Object Name from the DB.
- Fixes the object name and location in DB to match the name of the asset.
- Renames the file in repository to match the DB updates done.

#How to Run

It needs two input files in the classpath
- log4j.properties
- config.properties

Configure the properties in the above two files.


###Command to Capture Metadata from Asset xmls
Call the jar file with following command.
- java -cp .;MigrateAssets.jar com.nttdata.lumileds.opentext.migration.MigrateAssets (Make sure the two config files are present in the current directory or in the classpath)

Check the database for records inserted and then the processed folder.

Logs are generated into file migration.log under the directory called logs/ (this is configurable using log4j)

###Command to fix the file renaming issue
- java -cp .;MigrateAssets.jar com.nttdata.lumileds.opentext.migration.LMMFileRename

Check the logs for any errors.
Expectation is that both database and repository should be updated with proper filepath location and filename.