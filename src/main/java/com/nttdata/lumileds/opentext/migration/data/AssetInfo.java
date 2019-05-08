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
	private String fileName;
	private String currentObjLocation;
	private String fixedObjLocation;
	
	
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
	

}
