package z_domain.apprboard;

import java.sql.Date;

import org.springframework.stereotype.Component;


public class ApprBoardDTO {
	
	private Integer seq;
	private String id;
	private String subject;
	private String content;
	private Date regDate;
	private Date AppDate;
	private String approver;
	private String appStatus;
	
	// 테이블 조인용
	private String name;
	private String appContent;
	private String position;
		
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public Date getAppDate() {
		return AppDate;
	}
	
	public void setAppDate(Date appDate) {
		AppDate = appDate;
	}
	
	public String getApprover() {
		return approver;
	}
	
	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	public String getAppStatus() {
		return appStatus;
	}
	
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppContent() {
		return appContent;
	}

	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
}