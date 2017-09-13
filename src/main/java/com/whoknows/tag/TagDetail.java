package com.whoknows.tag;

public class TagDetail {

	private String picture;
	private String tagName;
	private Long tagID;
	private Integer followCount;
	private Boolean currentFollowed;
	private Long totalTopic;//tag有多少topic

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Boolean getCurrentFollowed() {
		return currentFollowed;
	}

	public void setCurrentFollowed(Boolean currentFollowed) {
		this.currentFollowed = currentFollowed;
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getTotalTopic() {
		return totalTopic;
	}

	public void setTotalTopic(Long totalTopic) {
		this.totalTopic = totalTopic;
	}
}
