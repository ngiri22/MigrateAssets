package com.nttdata.lumileds.opentext.migration.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.lumileds.opentext.migration.config.MigrationConstants;
import com.nttdata.lumileds.opentext.migration.data.AssetInfo;

public class FileRenameUtil {

	Logger logger = LoggerFactory.getLogger(FileRenameUtil.class);

	public List<AssetInfo> processDBResult(ResultSet lmmFileNameAndLocation) {

		List<AssetInfo> assetInfoList = new ArrayList<AssetInfo>();

		String relativeDirPath;
		int lastIndexOfSlash;
		String[] fileParts;

		try {

			while (lmmFileNameAndLocation.next()) {

				AssetInfo assetInfo = new AssetInfo();

				assetInfo.setUoiID(lmmFileNameAndLocation.getString(1));

				logger.info("UOI ID: {}", assetInfo.getUoiID() );

				assetInfo.setFileName(lmmFileNameAndLocation.getString(2));

				logger.info("File Name: {}", assetInfo.getFileName() );

				assetInfo.setMasterObjID(lmmFileNameAndLocation.getString(3));

				logger.info("Object ID: {}", assetInfo.getMasterObjID()) ;

				assetInfo.setCurrentObjLocation(lmmFileNameAndLocation.getString(4));

				logger.info("Old Object Location: {}", assetInfo.getCurrentObjLocation());

				assetInfo.setMasterObjName(lmmFileNameAndLocation.getString(5));

				logger.info("Master Object Name: {}", assetInfo.getMasterObjName());

				fileParts = assetInfo.getFileName().split(MigrationConstants.DOT_FOR_ARRAY_SPLIT);

				//fileParts = assetInfo.getMasterObjName().split(MigrationConstants.DOT_FOR_ARRAY_SPLIT);

				lastIndexOfSlash = assetInfo.getCurrentObjLocation().
						lastIndexOf(MigrationConstants.BACK_SLASH);

				logger.debug("Last Index of Back Slash: {}", lastIndexOfSlash);

				relativeDirPath = assetInfo.getCurrentObjLocation().
						substring(0,lastIndexOfSlash);

				logger.debug("Relative Path of the Directory: {}", relativeDirPath);

				if (fileParts.length > 1) {

					assetInfo.setFixedObjLocation( 
							relativeDirPath + MigrationConstants.BACK_SLASH +
							fileParts[0] + MigrationConstants.UNDER_SCORE +
							//assetInfo.getFileName() + MigrationConstants.UNDER_SCORE +
							assetInfo.getMasterObjID() + MigrationConstants.DOT
							+ fileParts[1]
							);

				}
				else {

					assetInfo.setFixedObjLocation( 
							relativeDirPath + MigrationConstants.BACK_SLASH +
							fileParts[0] + MigrationConstants.UNDER_SCORE + 
							//assetInfo.getFileName() + MigrationConstants.UNDER_SCORE +
							assetInfo.getMasterObjID()
							);

				}


				logger.info("New Object Location: {}", assetInfo.getFixedObjLocation());			

				assetInfoList.add(assetInfo);

			}
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception while processing DB Results: {}",
					sqlEx);
		}

		return assetInfoList;

	}

	public List<AssetInfo> processScreenNameResult(ResultSet lmmScreenNameAndLocation) {

		List<AssetInfo> assetInfoList = new ArrayList<AssetInfo>();

		String relativeDirPath;
		int lastIndexOfSlash;
		String[] fileParts;

		try {

			while (lmmScreenNameAndLocation.next()) {

				AssetInfo assetInfo = new AssetInfo();

				assetInfo.setFileName(lmmScreenNameAndLocation.getString(1));

				logger.info("File Name: {}", assetInfo.getFileName() );

				assetInfo.setScreenObjID(lmmScreenNameAndLocation.getString(2));

				logger.info("Screen Object ID: {}", assetInfo.getScreenObjID()) ;

				assetInfo.setCurrentObjLocation(lmmScreenNameAndLocation.getString(3));

				logger.info("Old Object Location: {}", assetInfo.getCurrentObjLocation());

				fileParts = assetInfo.getFileName().split(MigrationConstants.DOT_FOR_ARRAY_SPLIT);
				
				assetInfo.setScreenObjName(fileParts[0] + "-S.JPG");
				
				logger.info("Corrected Screen Object Name: {}", assetInfo.getScreenObjName());

				//fileParts = assetInfo.getMasterObjName().split(MigrationConstants.DOT_FOR_ARRAY_SPLIT);

				lastIndexOfSlash = assetInfo.getCurrentObjLocation().
						lastIndexOf(MigrationConstants.BACK_SLASH);

				logger.debug("Last Index of Back Slash: {}", lastIndexOfSlash);

				relativeDirPath = assetInfo.getCurrentObjLocation().
						substring(0,lastIndexOfSlash);

				logger.debug("Relative Path of the Directory: {}", relativeDirPath);

				assetInfo.setFixedObjLocation( 
						relativeDirPath + MigrationConstants.BACK_SLASH +
						fileParts[0] + "-S" +
						MigrationConstants.UNDER_SCORE +
						//assetInfo.getFileName() + MigrationConstants.UNDER_SCORE +
						assetInfo.getScreenObjID() + MigrationConstants.DOT
						+ "JPG"
						);


				logger.info("New Object Location: {}", assetInfo.getFixedObjLocation());			

				assetInfoList.add(assetInfo);

			}
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception while processing DB Results: {}",
					sqlEx);
		}

		return assetInfoList;

	}
	
	public List<AssetInfo> processThumbnailNameResult(ResultSet lmmThumbnailNameAndLocation) {

		List<AssetInfo> assetInfoList = new ArrayList<AssetInfo>();

		String relativeDirPath;
		int lastIndexOfSlash;
		String[] fileParts;

		try {

			while (lmmThumbnailNameAndLocation.next()) {

				AssetInfo assetInfo = new AssetInfo();

				assetInfo.setFileName(lmmThumbnailNameAndLocation.getString(1));

				logger.info("File Name: {}", assetInfo.getFileName() );

				assetInfo.setThumbnailObjID(lmmThumbnailNameAndLocation.getString(2));

				logger.info("Thumbnail Object ID: {}", assetInfo.getThumbnailObjID()) ;

				assetInfo.setCurrentObjLocation(lmmThumbnailNameAndLocation.getString(3));

				logger.info("Old Object Location: {}", assetInfo.getCurrentObjLocation());

				fileParts = assetInfo.getFileName().split(MigrationConstants.DOT_FOR_ARRAY_SPLIT);
				
				assetInfo.setThumbnailObjName(fileParts[0] + "-S-T.JPG");
				
				logger.info("Corrected Thumbnail Object Name: {}", assetInfo.getThumbnailObjName());

				//fileParts = assetInfo.getMasterObjName().split(MigrationConstants.DOT_FOR_ARRAY_SPLIT);

				lastIndexOfSlash = assetInfo.getCurrentObjLocation().
						lastIndexOf(MigrationConstants.BACK_SLASH);

				logger.debug("Last Index of Back Slash: {}", lastIndexOfSlash);

				relativeDirPath = assetInfo.getCurrentObjLocation().
						substring(0,lastIndexOfSlash);

				logger.debug("Relative Path of the Directory: {}", relativeDirPath);

				assetInfo.setFixedObjLocation( 
						relativeDirPath + MigrationConstants.BACK_SLASH +
						fileParts[0] + "-S-T" +
						MigrationConstants.UNDER_SCORE +
						//assetInfo.getFileName() + MigrationConstants.UNDER_SCORE +
						assetInfo.getThumbnailObjID() + 
						MigrationConstants.DOT +
						"JPG"
						);


				logger.info("New Object Location: {}", assetInfo.getFixedObjLocation());			

				assetInfoList.add(assetInfo);

			}
		} catch (SQLException sqlEx) {
			logger.error("SQL Exception while processing DB Results: {}",
					sqlEx);
		}

		return assetInfoList;

	}

}
