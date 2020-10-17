package com.questa.blogapi.model;

public class UserProgressLevel {

	private Integer level;
	private Long currentLevelTime;
	private Long TotalLevelTime;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getCurrentLevelTime() {
		return currentLevelTime;
	}
	public void setCurrentLevelTime(Long currentLevelTime) {
		this.currentLevelTime = currentLevelTime;
	}
	public Long getTotalLevelTime() {
		return TotalLevelTime;
	}
	public void setTotalLevelTime(Long totalLevelTime) {
		TotalLevelTime = totalLevelTime;
	}
}
