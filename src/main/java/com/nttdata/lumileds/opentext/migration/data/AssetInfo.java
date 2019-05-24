/**
 * 
 */
package com.nttdata.lumileds.opentext.migration.data;

/**
 * Asset Info Details POJO class
 *
 */

public class AssetInfo {
	
	private String uoiID;
	private String masterObjID;
	private String screenObjID;
	private String fileName;
	private String currentObjLocation;
	private String fixedObjLocation;
	private String masterObjName;
	private String screenObjName;
	
	/**
	 * @return the masteObjName
	 */
	
	public String getMasterObjName() {
		return masterObjName;
	}
	
	/**
	 * @param masterObjName the masterObjName to set
	 */
	public void setMasterObjName(String masterObjName) {
		this.masterObjName = masterObjName;
	}
	
	/**
	 * @return the uoiID
	 */
	public String getUoiID() {
		return uoiID;
	}
	/**
	 * @param uoiID the uoiID to set
	 */
	public void setUoiID(String uoiID) {
		this.uoiID = uoiID;
	}
	
	/**
	 * @return the masterObjID
	 */
	public String getMasterObjID() {
		return masterObjID;
	}
	/**
	 * @param masterObjID the masterObjID to set
	 */
	public void setMasterObjID(String masterObjID) {
		this.masterObjID = masterObjID;
	}
	
	/**
	 * @return the screenObjID
	 */
	public String getScreenObjID() {
		return screenObjID;
	}

	/**
	 * @param screenObjID the screenObjID to set
	 */
	public void setScreenObjID(String screenObjID) {
		this.screenObjID = screenObjID;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the currentObjLocation
	 */
	public String getCurrentObjLocation() {
		return currentObjLocation;
	}
	/**
	 * @param currentObjLocation the currentObjLocation to set
	 */
	public void setCurrentObjLocation(String currentObjLocation) {
		this.currentObjLocation = currentObjLocation;
	}
	/**
	 * @return the fixedObjLocation
	 */
	public String getFixedObjLocation() {
		return fixedObjLocation;
	}
	/**
	 * @param fixedObjLocation the fixedObjLocation to set
	 */
	public void setFixedObjLocation(String fixedObjLocation) {
		this.fixedObjLocation = fixedObjLocation;
	}

	/**
	 * @return the screenObjName
	 */
	public String getScreenObjName() {
		return screenObjName;
	}

	/**
	 * @param screenObjName the screenObjName to set
	 */
	public void setScreenObjName(String screenObjName) {
		this.screenObjName = screenObjName;
	}
	

}
