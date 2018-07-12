package com.lumileds.opentext.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumileds.opentext.config.MigrationConstants;
import com.lumileds.opentext.data.AssetMetadata;

public class SQLExecutor {
	private final Logger logger = LoggerFactory.getLogger(SQLExecutor.class);

	public boolean insertMetadata(AssetMetadata assetMetadata) {

		Connection conn = null;

		try {
			
			insertDB(assetMetadata,getConnection());

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

	private void insertDB (AssetMetadata assetMetadata, Connection conn) throws SQLException {

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
		
		assetMasterInsertStatement.setString(1, assetMetadata.getId());
		assetMasterInsertStatement.setString(2, assetMetadata.getVersion());
		assetMasterInsertStatement.setString(3, assetMetadata.getIsMaster());
		assetMasterInsertStatement.setString(4, assetMetadata.getIsLatestVersion());
		assetMasterInsertStatement.setString(5, assetMetadata.getFullPath());
		assetMasterInsertStatement.setString(6, assetMetadata.getFileName());
		
		assetMasterInsertStatement.executeUpdate();	
		
		
	}
}
