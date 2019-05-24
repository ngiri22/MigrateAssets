package com.nttdata.lumileds.opentext.migration.util;

/****
 * Class for SQL related operations
 * Gets the pojo class and inserts the records
 *  into the database into each of the tables
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.lumileds.opentext.migration.config.MigrationConstants;
import com.nttdata.lumileds.opentext.migration.data.AssetInfo;
import com.nttdata.lumileds.opentext.migration.data.AssetMetadata;

public class SQLExecutor {

	private static final Logger logger = LoggerFactory.getLogger(SQLExecutor.class);

	private static Connection conn = null;

	public SQLExecutor() {

		String dbURL = MigrationConstants.DB_URL;
		String driver = MigrationConstants.DB_DRIVER;
		String user = MigrationConstants.DB_USER;
		String pass = MigrationConstants.DB_PASSWORD;

		try {
			Class.forName(driver).newInstance();

			conn = DriverManager.getConnection(dbURL, user, pass);
			if (null != conn) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				logger.debug("Driver name : {}", dm.getDriverName());
				logger.debug("Driver version : {}", dm.getDriverVersion());
				logger.debug("Product name : {}", dm.getDatabaseProductName());
				logger.debug("Product version : {}", dm.getDatabaseProductVersion());
			}

		}
		catch (Exception ex) {
			logger.error("Exception: {} ", ex);
		}

	}

	public boolean insertMetadata(AssetMetadata assetMetadata) {

		try {

			insertAssetMaster(assetMetadata,conn);

			HashMap<String,String> namePathLabelPathMap = assetMetadata.getNameLabelPathMap();

			insertAssetTaxonomy(namePathLabelPathMap, assetMetadata.getId(),conn);

			List<Element> nameFieldElementList = assetMetadata.getNameFieldList();

			insertAssetMetadata(nameFieldElementList, assetMetadata.getId(),conn);

			return true;

		}

		catch (Exception ex) {

			logger.error("Exception while inserting into database: {} ", ex );

		} 
		return false;
	}

	private void insertAssetMetadata(List<Element> nameFieldElementList,
			String id, Connection conn) throws SQLException {

		String assetMetadataInsert = "INSERT INTO LUMILEDS_MIGRATION_PAL_ASSET_METADATA "
				+ "(ID, NAME, FIELD) VALUES "
				+ "(?,?,?)";

		PreparedStatement assetMetadataInsertStatement = 
				conn.prepareStatement(assetMetadataInsert);

		for (Iterator<Element> elemIterator = nameFieldElementList.iterator(); elemIterator.hasNext(); ) {

			Element fieldElement = elemIterator.next();

			logger.debug("{} : {} ", 
					fieldElement.attributeValue(MigrationConstants.XML_NAME_ATTRIBUTE)
					, fieldElement.getStringValue());

			assetMetadataInsertStatement.setString(1, id);
			assetMetadataInsertStatement.setString(2, fieldElement.attributeValue(MigrationConstants.XML_NAME_ATTRIBUTE));
			assetMetadataInsertStatement.setString(3,  fieldElement.getStringValue());
			assetMetadataInsertStatement.executeUpdate();
			assetMetadataInsertStatement.clearParameters();			

		}

	}

	private void insertAssetTaxonomy(HashMap<String, String> namePathLabelPathMap,
			String id, Connection conn) throws SQLException {

		String assetTaxonomyInsert = "INSERT INTO LUMILEDS_MIGRATION_PAL_ASSET_TAXONOMY "
				+ "(ID, NAMEPATH, LABELPATH) VALUES "
				+ "(?,?,?)";

		PreparedStatement assetTaxonomyInsertStatement = 
				conn.prepareStatement(assetTaxonomyInsert);

		for (Map.Entry<String, String> entry : namePathLabelPathMap.entrySet()) {

			logger.debug("NamePath: {}", entry.getKey());
			logger.debug("LabelPath: {}", entry.getValue());

			assetTaxonomyInsertStatement.setString(1, id);
			assetTaxonomyInsertStatement.setString(2, entry.getKey());
			assetTaxonomyInsertStatement.setString(3, entry.getValue());
			assetTaxonomyInsertStatement.executeUpdate();
			assetTaxonomyInsertStatement.clearParameters();

		}

	}


	private void insertAssetMaster (AssetMetadata assetMetadata, Connection conn) throws SQLException {

		String assetMasterInsert = "INSERT INTO LUMILEDS_MIGRATION_PAL_ASSET_MASTER "
				+ "(ID, VERSION, ISMASTER, ISLATESTVERSION,"
				+ "FULLPATH,FILENAME) VALUES"
				+ "(?,?,?,?,?,?)";

		PreparedStatement assetMasterInsertStatement = 
				conn.prepareStatement(assetMasterInsert);

		logger.debug("Version: {} ", assetMetadata.getVersion());
		logger.debug("isMaster: {} ", assetMetadata.getIsMaster());
		logger.debug("isLatestVersion: {} ", assetMetadata.getIsLatestVersion());
		logger.debug("fullPath: {} ", assetMetadata.getFullPath());
		logger.debug("fileName: {} ", assetMetadata.getFileName());

		assetMasterInsertStatement.setString(1, assetMetadata.getId());
		assetMasterInsertStatement.setString(2, assetMetadata.getVersion());
		assetMasterInsertStatement.setString(3, assetMetadata.getIsMaster());
		assetMasterInsertStatement.setString(4, assetMetadata.getIsLatestVersion());
		assetMasterInsertStatement.setString(5, assetMetadata.getFullPath());
		assetMasterInsertStatement.setString(6, assetMetadata.getFileName());

		assetMasterInsertStatement.executeUpdate();		

	}

	public ResultSet getFileNameAndLocation() {

		String fileNameAndLocation = 
				"select top " + MigrationConstants.RENAME_BATCH_COUNT 
				+ " a.uoi_id,a.name,b.master_obj_id,b.master_obj_name_loc, "
				+ " b.master_obj_name "
				+ " from uois a, uois b where " 
				+ " a.uoi_id=b.uoi_id and "
				+ " a.name<>b.master_obj_name";
		PreparedStatement fileNameAndLocationStatement;
		try {

			fileNameAndLocationStatement =
					conn.prepareStatement(fileNameAndLocation);

			return fileNameAndLocationStatement.executeQuery();
		}
		catch (SQLException sqlEx) {
			logger.error("SQLException while fetching object details: {} ", sqlEx);
		}				

		return null;
	}

	public ResultSet getScreenNameAndLocation() {

		String screenNameAndLocation = 
				"select top " + MigrationConstants.RENAME_BATCH_COUNT + 
				" a.name, b.object_id, b.OBJECT_NAME_LOCATION " + 
				" from " + 
				" uois a , object_stacks b where " + 
				" a.SCREEN_RES_OBJ_ID = b.object_id and " + 
				" substring (a.name,0,5) <> substring(b.object_name,0,5) and " + 
				" b.OBJECT_NAME like '%-S.JPG' and " + 
				" b.content_kind='PREVIEW' and " + 
				" a.name like '%.%'";

		PreparedStatement screenNameAndLocationStatement;
		try {

			screenNameAndLocationStatement =
					conn.prepareStatement(screenNameAndLocation);

			return screenNameAndLocationStatement.executeQuery();
		}
		catch (SQLException sqlEx) {
			logger.error("SQLException while fetching screen name details: {} ", sqlEx);
		}				

		return null;
	}
	
	public void updateScreenObjNameLocation(AssetInfo assetInfo) {

		String updateObjStacksNameLocation = "UPDATE OBJECT_STACKS SET "
				+ " OBJECT_NAME = ? , "
				+ " OBJECT_NAME_LOCATION = ? "
				+ " WHERE OBJECT_ID = ? ";

		try {

			PreparedStatement updateObjStacksStatement = 
					conn.prepareStatement(updateObjStacksNameLocation);

			updateObjStacksStatement.setString(1, assetInfo.getScreenObjName());
			updateObjStacksStatement.setString(2,  assetInfo.getFixedObjLocation());
			updateObjStacksStatement.setString(3,  assetInfo.getScreenObjID());

			updateObjStacksStatement.executeUpdate();

			updateObjStacksStatement.close();


		} catch (SQLException sqlEx) {
			logger.error("SQLException while updating object details: {} ", sqlEx);
		}

	}

	public void updateObjNameLocation(AssetInfo assetInfo) {

		String updateUOISObjNameLocation = "UPDATE UOIS SET "
				+ " MASTER_OBJ_NAME = ? , "
				+ " MASTER_OBJ_NAME_LOC = ? "
				+ " WHERE MASTER_OBJ_ID = ? AND "
				+ " UOI_ID = ? ";

		String updateObjStacksNameLocation = "UPDATE OBJECT_STACKS SET "
				+ " OBJECT_NAME = ? , "
				+ " OBJECT_NAME_LOCATION = ? "
				+ " WHERE OBJECT_ID = ? ";

		try {

			PreparedStatement updateUOISStatement = 
					conn.prepareStatement(updateUOISObjNameLocation);

			updateUOISStatement.setString(1, assetInfo.getFileName());
			updateUOISStatement.setString(2,  assetInfo.getFixedObjLocation());
			updateUOISStatement.setString(3,  assetInfo.getMasterObjID());
			updateUOISStatement.setString(4, assetInfo.getUoiID());

			updateUOISStatement.executeUpdate();

			updateUOISStatement.close();


			PreparedStatement updateObjStacksStatement = 
					conn.prepareStatement(updateObjStacksNameLocation);

			updateObjStacksStatement.setString(1, assetInfo.getFileName());
			updateObjStacksStatement.setString(2,  assetInfo.getFixedObjLocation());
			updateObjStacksStatement.setString(3,  assetInfo.getMasterObjID());

			updateObjStacksStatement.executeUpdate();

			updateObjStacksStatement.close();


		} catch (SQLException sqlEx) {
			logger.error("SQLException while updating object details: {} ", sqlEx);
		}

	}

	public static void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException sqlEx) {
			logger.error("SQLException while closing the connection: {} ", sqlEx);
		}
	}

}
