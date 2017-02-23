package javamodel;

import java.util.Date;

public class Comment {

	private int commentId;
	private int homeId=-1;
	private String content;
	private String userIP;
	private Date commentDate;
	
	
	
	public Comment(int homeId, String content, String userIP) {
		super();
		this.homeId = homeId;
		this.content = content;
		this.userIP = userIP;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getHomeId() {
		return homeId;
	}
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	
	
}
