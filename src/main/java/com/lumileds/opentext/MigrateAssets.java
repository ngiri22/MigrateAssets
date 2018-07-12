package com.lumileds.opentext;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumileds.opentext.config.MigrationConstants;
import com.lumileds.opentext.data.AssetMetadata;
import com.lumileds.opentext.util.FileList;
import com.lumileds.opentext.util.SQLExecutor;
import com.lumileds.opentext.util.XMLElements;

public class MigrateAssets {

	public static void main(String[] args) throws DocumentException {

		Logger logger = LoggerFactory.getLogger(MigrateAssets.class);	

		FileList filelist = new FileList();

		File[] files = filelist.getFiles(args[0]);

		logger.info("********************START******************");
		logger.info("File to be Processed: {} ", files[1].getName());

		XMLElements xmlElements = new XMLElements();
		
		AssetMetadata assetMetadata;
		
		SQLExecutor sqlexec = new SQLExecutor();
		

		for (int i=0; i < 5; i = i+1) {

			assetMetadata = xmlElements.getElements(files[i]);
				
			sqlexec.insertMetadata(assetMetadata);

			List<Element> elemList = assetMetadata.getNameFieldList();
			//fileDoc.getElements(files[3]);

			for (Iterator<Element> elemIterator = elemList.iterator(); elemIterator.hasNext(); ) {

				Element fieldElement = elemIterator.next();

				logger.debug("{} : {} ", 
						fieldElement.attributeValue(MigrationConstants.XML_NAME_ATTRIBUTE)
						, fieldElement.getStringValue());

			}

			logger.info("********************END******************");
			
		}

	}	

}
