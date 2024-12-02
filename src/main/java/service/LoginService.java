package service;

import dao.LoginDao;
import dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.apprboard.ApprProxyDTO;
import dto.common.EmpDTO;

@Service
@Transactional
public class LoginService {
	
	private LoginDao loginDao;
	
	@Autowired
	public LoginService(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	
	public boolean isIdExist(LoginDTO loginDTO) {
		int isIdExist = loginDao.getTotalId(loginDTO);
		return isIdExist > 0 ;
	}


	public boolean isPasswoardCorrect(LoginDTO loginDTO) {
		int isPasswordCorrect = loginDao.checkPassword(loginDTO);
		return isPasswordCorrect > 0;
	}


	public EmpDTO getLoginedEmp(String id) {
		EmpDTO LoginedEmp = loginDao.findById(id);
		return LoginedEmp;
	}


	public ApprProxyDTO getProxyInfo(String id) {
		ApprProxyDTO proxyInfo = loginDao.getProxyInfo(id);
		return proxyInfo;
	}


	public int updateProxyInfo(String id) {
		int updatedProxy = loginDao.updateProxyInfo(id);
		return updatedProxy;
	}
}
