package z_domain.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import z_domain.apprboard.ApprProxyDTO;
import z_domain.common.EmpDTO;

@Service
@Transactional
public class LoginService {
	
	private LoginDao ld;
	
	@Autowired
	public LoginService(LoginDao ld) {
		this.ld = ld;
	}

	
	public boolean isIdExist(LoginDTO loginDTO) {
		int isIdExist = ld.getTotalId(loginDTO);
		return isIdExist > 0 ;
	}


	public boolean isPasswoardCorrect(LoginDTO loginDTO) {
		int isPasswordCorrect = ld.checkPassword(loginDTO);
		return isPasswordCorrect > 0;
	}


	public EmpDTO getLoginedEmp(String id) {
		EmpDTO LoginedEmp = ld.findById(id);
		return LoginedEmp;
	}


	public ApprProxyDTO getProxyInfo(String id) {
		ApprProxyDTO proxyInfo = ld.getProxyInfo(id);
		return proxyInfo;
	}


	public int updateProxyInfo(String id) {
		int updatedProxy = ld.updateProxyInfo(id);
		return updatedProxy;
	}
	
	
}
