package com.lumileds.opentext.util;

import java.io.File;
import java.io.FilenameFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumileds.opentext.config.MigrationConstants;

public class FilesUtility {
	
	private final Logger logger = LoggerFactory.getLogger(FilesUtility.class);

	public File[] getFilesToProcess() {

		return xmlFiles(MigrationConstants.FILE_INPUT_LOCATION);
		
	}

	private File[] xmlFiles(String directory) {

		File directoryName = new File(directory);

		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File directoryName, String fileNamePattern) {

				if( fileNamePattern.lastIndexOf('.') > 0 ) {

					//get last index for '.' char
					int lastIndex = fileNamePattern.lastIndexOf('.');

					//get extension
					String str = fileNamePattern.substring(lastIndex);

					// match path name extension
					if(str.equals(".xml")) {
						return true;
					}
					
				}

				return false;
			};

		};

		File[] xmlFileList = directoryName.listFiles(fileNameFilter);

		return xmlFileList;

	}

	public void move(File file, String dirToMove) {
		
		logger.info(" Processing completed, file is moved to {}",
				dirToMove + "\\" + file.getName());
		
		file.renameTo(new File (
				dirToMove + "\\" + file.getName()));
		
	}

}
