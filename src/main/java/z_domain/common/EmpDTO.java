package z_domain.common;

public class EmpDTO {
	
	private String id;
    private String password;
    private String name;
    private String position;
    private Integer rank;
    private String isProxy;
    private String proxPosition;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}

	public String getProxPosition() {
		return proxPosition;
	}

	public void setProxPosition(String proxPosition) {
		this.proxPosition = proxPosition;
	}
	    
}
