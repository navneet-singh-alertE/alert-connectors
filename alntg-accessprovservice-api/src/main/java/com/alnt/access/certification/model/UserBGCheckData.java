package com.alnt.access.certification.model;

import java.util.Map;

public class UserBGCheckData {
	
	String userId;
	String bgCheckStatus;
	Map<String,Object> bgCheckInfo;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBgCheckStatus() {
		return bgCheckStatus;
	}
	public void setBgCheckStatus(String bgCheckStatus) {
		this.bgCheckStatus = bgCheckStatus;
	}
	public Map<String, Object> getBgCheckInfo() {
		return bgCheckInfo;
	}
	public void setBgCheckInfo(Map<String, Object> bgCheckInfo) {
		this.bgCheckInfo = bgCheckInfo;
	}

}
