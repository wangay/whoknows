package com.whoknows.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reply {

	private Long id;
	private Long user_id;
	private Long topic_id;
	private String content;
	private String action;
	private Timestamp create_time;
	private Timestamp update_time;
	private Long reply_id;
	private Long rank;
	private String createTime;//字符串形式，前段直接显示

	public String getCreateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		createTime = df.format(getCreate_time());
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public Long getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(Long topic_id) {
		this.topic_id = topic_id;
	}

	public Long getReply_id() {
		return reply_id;
	}

	public void setReply_id(Long reply_id) {
		this.reply_id = reply_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
}
