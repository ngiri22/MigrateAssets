package com.lumileds.opentext.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileList {

	public File[] getFiles(String folderPath) {

		return xmlFiles(folderPath);
		
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

}
