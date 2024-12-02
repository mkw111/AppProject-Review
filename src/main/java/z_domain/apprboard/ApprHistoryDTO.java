package z_domain.apprboard;

import java.sql.Date;

public class ApprHistoryDTO {
	
	private int no;
	private int seq;
	private Date regDate;
	private String approver;
	private String appStatus;
	
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	public int getSeq() {
		return seq;
	}
	
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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
	
}
