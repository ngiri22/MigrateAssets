package com.lumileds.opentext.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumileds.opentext.config.MigrationConstants;
import com.lumileds.opentext.data.AssetMetadata;

public class SQLExecutor {
	
	private final Logger logger = LoggerFactory.getLogger(SQLExecutor.class);
	
	private Connection getConnection() throws SQLException {

		Connection conn = null;

		String dbURL = MigrationConstants.DB_URL;
		String user = MigrationConstants.DB_USER;
		String pass = MigrationConstants.DB_PASSWORD;

		conn = DriverManager.getConnection(dbURL, user, pass);
		if (conn != null) {
			DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
			logger.debug("Driver name : {}", dm.getDriverName());
			logger.debug("Driver version : {}", dm.getDriverVersion());
			logger.debug("Product name : {}", dm.getDatabaseProductName());
			logger.debug("Product version : {}", dm.getDatabaseProductVersion());
		}

		return conn;

	}

	public boolean insertMetadata(AssetMetadata assetMetadata) {

		Connection conn = null;

		try {
		
			conn = getConnection();
			
			insertAssetMaster(assetMetadata,conn);
			
			HashMap<String,String> namePathLabelPathMap = assetMetadata.getNameLabelPathMap();
			
			insertAssetTaxonomy(namePathLabelPathMap, assetMetadata.getId(),conn);
			
			
			List<Element> nameFieldElementList = assetMetadata.getNameFieldList();
			
			insertAssetMetadata(nameFieldElementList, assetMetadata.getId(),conn);

		}

		catch (SQLException ex) {
			
			ex.printStackTrace();
			logger.info("Exception: {} ", ex.getMessage());
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger.info("Exception: {} ", ex.getMessage());
			}
		}

		return false;
	}

	private void insertAssetMetadata(List<Element> nameFieldElementList,
			String id, Connection conn) throws SQLException {
		
		String assetMetadataInsert = "INSERT INTO ASSET_METADATA "
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
		
		String assetTaxonomyInsert = "INSERT INTO ASSET_TAXONOMY "
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

		String assetMasterInsert = "INSERT INTO ASSET_MASTER "
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
}
