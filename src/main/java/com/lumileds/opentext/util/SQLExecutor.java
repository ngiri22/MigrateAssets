package com.lumileds.opentext.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Element;

import com.lumileds.opentext.config.MigrationConstants;
import com.lumileds.opentext.data.AssetMetadata;

public class SQLExecutor {

	public boolean insertMetadata(AssetMetadata assetMetadata) {

		Connection conn = null;

		try {
			conn = getConnection();
		}

		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
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
			System.out.println("Driver name: " + dm.getDriverName());
			System.out.println("Driver version: " + dm.getDriverVersion());
			System.out.println("Product name: " + dm.getDatabaseProductName());
			System.out.println("Product version: " + dm.getDatabaseProductVersion());

		}
		
		return conn;

	}
}
