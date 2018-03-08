package com.teaShop.bean;

public class SysUserPage {

	private int userId;
	private String userName;
	private int pageId;
	private int prePageId;
	private String pageName;
	private String url;
	private String levelCode;
	private String icon;
	private int flag = 0;
	private String desciption;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
	public int getPrePageId() {
		return prePageId;
	}

	public void setPrePageId(int prePageId) {
		this.prePageId = prePageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public String toString() {
		return "用户名:" + this.userName + "  菜单名:" + this.pageName + " url：" + this.url;
	}
}
