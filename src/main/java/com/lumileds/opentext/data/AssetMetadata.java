package com.lumileds.opentext.data;

import java.util.HashMap;
import java.util.List;
import org.dom4j.Element;

public class AssetMetadata {
	
	private String id;
	private String version;
	private String isMaster;
	private String isLatestVersion;
	private String fullPath;
	private String fileName;
	private HashMap<String, String> nameLabelPathMap;
	private List<Element> nameFieldList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return the isMaster
	 */
	public String getIsMaster() {
		return isMaster;
	}
	/**
	 * @param isMaster the isMaster to set
	 */
	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}
	/**
	 * @return the isLatestVersion
	 */
	public String getIsLatestVersion() {
		return isLatestVersion;
	}
	/**
	 * @param isLatestVersion the isLatestVersion to set
	 */
	public void setIsLatestVersion(String isLatestVersion) {
		this.isLatestVersion = isLatestVersion;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public HashMap<String, String> getNameLabelPathMap() {
		return nameLabelPathMap;
	}
	public void setNameLabelPathMap(HashMap<String, String> nameLabelPathMap) {
		this.nameLabelPathMap = nameLabelPathMap;
	}
	public List<Element> getNameFieldList() {
		return nameFieldList;
	}
	public void setNameFieldList(List<Element> nameFieldList) {
		this.nameFieldList = nameFieldList;
	}

}
