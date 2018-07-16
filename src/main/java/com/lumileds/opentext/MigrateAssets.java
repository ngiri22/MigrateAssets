package com.lumileds.opentext;

import java.io.File;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumileds.opentext.config.MigrationConstants;
import com.lumileds.opentext.data.AssetMetadata;
import com.lumileds.opentext.util.FilesUtility;
import com.lumileds.opentext.util.SQLExecutor;
import com.lumileds.opentext.util.XMLElements;

public class MigrateAssets {

	public static void main(String[] args) throws DocumentException {

		Logger logger = LoggerFactory.getLogger(MigrateAssets.class);	

		logger.info("********************START******************");

		FilesUtility filesUtility = new FilesUtility();

		File[] files = filesUtility.getFilesToProcess();

		XMLElements xmlElements = new XMLElements();

		AssetMetadata assetMetadata;

		SQLExecutor sqlexec = new SQLExecutor();


		for (int i=0; i < files.length; i = i+1) {

			if ( i < Integer.valueOf(MigrationConstants.FILE_INPUT_BATCH_COUNT) ) {

				logger.info("File being processed: {}", files[i].getName());

				try {

					assetMetadata = xmlElements.getElements(files[i]);

					sqlexec.insertMetadata(assetMetadata);

					filesUtility.move(files[i], MigrationConstants.FILE_PROCESSED_LOCATION);
				}
				catch (NullPointerException nullEx) {

					logger.error("Null pointer exception {}: ", nullEx);

				}
			}
		}

		logger.info("********************END******************");

	}	

}
