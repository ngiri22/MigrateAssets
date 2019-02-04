package com.nttdata.lumileds.opentext.migration;

/*****
 * Main class for Migrate Assets.
 * - Gets the metadata xml files.
 * - Gets the elements from the xml file.
 * - Reads the metadata and inserts metadata into the database.
 */

import java.io.File;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.lumileds.opentext.migration.config.MigrationConstants;
import com.nttdata.lumileds.opentext.migration.data.AssetMetadata;
import com.nttdata.lumileds.opentext.migration.util.FilesUtility;
import com.nttdata.lumileds.opentext.migration.util.SQLExecutor;
import com.nttdata.lumileds.opentext.migration.util.XMLElements;

public class MigrateAssets {

	public static void main(String[] args) throws DocumentException {

		Logger logger = LoggerFactory.getLogger(MigrateAssets.class);	

		logger.info("********************START******************");

		FilesUtility filesUtility = new FilesUtility();


		//Get the files to be processed.
		File[] files = filesUtility.getFilesToProcess();

		XMLElements xmlElements = new XMLElements();

		AssetMetadata assetMetadata;

		SQLExecutor sqlexec = new SQLExecutor();


		for (int i=0; i < files.length; i = i+1) {

			// Process only max batch size for every run.

			if ( i < Integer.valueOf(MigrationConstants.FILE_INPUT_BATCH_COUNT) ) {

				logger.info("File being processed: {}", files[i].getName());

				try {

					//Get the xml elements as a POJO object.
					assetMetadata = xmlElements.getElements(files[i]);

					//Insert the pojo into the database.
					if(sqlexec.insertMetadata(assetMetadata )) {

						//Move the files to processed directory, if metadata inserted successfully..
						filesUtility.move(files[i], MigrationConstants.FILE_PROCESSED_LOCATION);
					}
				}
				catch (Exception ex) {

					logger.error("Null pointer exception {}: ", ex);

				}
			}
		}
		
		SQLExecutor.closeConnection();

		logger.info("********************END******************");

	}	

}
