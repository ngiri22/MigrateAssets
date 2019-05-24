package com.nttdata.lumileds.opentext.migration;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.lumileds.opentext.migration.data.AssetInfo;
import com.nttdata.lumileds.opentext.migration.util.FileRenameUtil;
import com.nttdata.lumileds.opentext.migration.util.FilesUtility;
import com.nttdata.lumileds.opentext.migration.util.SQLExecutor;

public class LMMScreenRename {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(LMMScreenRename.class);
		
		logger.info("* * * STARTED File Renaming Process * * *");

		SQLExecutor sqlExecutor = new SQLExecutor();

		FilesUtility filesUtil = new FilesUtility();

		FileRenameUtil fileRenUtil = new FileRenameUtil();

		List<AssetInfo> assetInfoList = new ArrayList<AssetInfo>();

		ResultSet lmmScreenNameAndLocation = sqlExecutor.getScreenNameAndLocation();

		if (null != lmmScreenNameAndLocation) {
			assetInfoList = 
					fileRenUtil
					.processScreenNameResult(lmmScreenNameAndLocation);

			for (AssetInfo assetInfo : assetInfoList) {

				//Update Database
				sqlExecutor.updateScreenObjNameLocation(assetInfo);

				//Update File location in repository
				filesUtil.renameFiles(assetInfo);
			}

		}
		
		logger.info("* * * COMPLETED File Renaming Process * * *");
	}	

}
