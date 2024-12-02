package z_domain.apprboard;

import java.util.Date;

public class ApprProxyDTO {
	
	private String id;
	private String authorizer;
	private String authPosition;
	private String proxyApprover;
	private String proxyPosition;
	private Date proxyStartDate;
	private Date proxyEndDate;
	private String status;
	
	// 조회용
	private String name;
	
	
	public String getAuthorizer() {
		return authorizer;
	}
	
	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}
	
	public String getAuthPosition() {
		return authPosition;
	}
	
	public void setAuthPosition(String authPosition) {
		this.authPosition = authPosition;
	}
	
	public String getProxyApprover() {
		return proxyApprover;
	}
	
	public void setProxyApprover(String proxyApprover) {
		this.proxyApprover = proxyApprover;
	}
	
	public String getProxyPosition() {
		return proxyPosition;
	}
	
	public void setProxyPosition(String proxyPosition) {
		this.proxyPosition = proxyPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getProxyStartDate() {
		return proxyStartDate;
	}

	public void setProxyStartDate(Date proxyStartDate) {
		this.proxyStartDate = proxyStartDate;
	}

	public Date getProxyEndDate() {
		return proxyEndDate;
	}

	public void setProxyEndDate(Date proxyEndDate) {
		this.proxyEndDate = proxyEndDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
