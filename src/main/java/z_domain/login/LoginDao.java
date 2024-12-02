package z_domain.login;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import z_domain.apprboard.ApprProxyDTO;
import z_domain.common.EmpDTO;

@Repository
public class LoginDao {
	
	private final SqlSession session;
		
	@Autowired
	public LoginDao(SqlSession session) {
		this.session = session;
	}


	public int getTotalId(LoginDTO loginDTO) {
		int result = 0;
		
		try {
			result = session.selectOne("getTotalId", loginDTO);
		} catch (Exception e) {
			System.out.println("LoginDao e.getMessage() -> " + e.getMessage());
		}
		
		return result;
	}


	public int checkPassword(LoginDTO loginDTO) {
		int result = 0;
		
		try {
			result = session.selectOne("checkPassword", loginDTO);
		} catch (Exception e) {
			System.out.println("LoginDao e.getMessage() -> " + e.getMessage());
		}

		return result;
	}


	public EmpDTO findById(String id) {
		EmpDTO loginedEmp = null;
		
		try {
			loginedEmp = session.selectOne("LoginMapper.findById", id);
		} catch (Exception e) {
			System.out.println("LoginDao e.getMessage() -> " + e.getMessage());
		}
		
		return loginedEmp;
		
	}


	public ApprProxyDTO getProxyInfo(String id) {
		ApprProxyDTO proxyInfo = null;

		try {
			proxyInfo = session.selectOne("getProxyInfo", id);
		} catch (Exception e) {
			System.out.println("LoginDao e.getMessage() -> " + e.getMessage());
		}

		return proxyInfo;
	}


	public int updateProxyInfo(String id) {
		int updatedProxy = 0;
		
		try {
			updatedProxy = session.update("updateProxyInfo", id);
		} catch (Exception e) {
			System.out.println("LoginDao e.getMessage() -> " + e.getMessage());
		}
		
		return updatedProxy;
	}
}
