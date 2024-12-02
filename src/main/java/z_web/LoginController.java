package z_web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import z_domain.apprboard.ApprProxyDTO;
import z_domain.common.EmpDTO;
import z_domain.login.LoginDTO;
import z_domain.login.LoginService;

@Controller
public class LoginController {
		
	private final LoginService ls;
	
	@Autowired
	public LoginController(LoginService ls) {
		this.ls = ls;
	}
	
	@RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
	public String loginForm(
			@RequestParam(required=false) String id,
			@RequestParam(required=false) String password,
			Model model
	) {
		model.addAttribute("id", id);
		model.addAttribute("password", password);
		
		return "login/loginForm";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@ModelAttribute("loginDTO") LoginDTO loginDTO,
			EmpDTO empDTO,
			HttpServletRequest request 
	) {
		// login 실패시
		boolean isIdExist = ls.isIdExist(loginDTO);
		if(!isIdExist) {
			int totalId = 0;
			return "redirect:/login?id=" + totalId;
		}
		
		boolean isPasswordCorrect = ls.isPasswoardCorrect(loginDTO);
		if(!isPasswordCorrect) {
			int checkedPassword = 0;
			return "redirect:login?password=" + checkedPassword;
		}
				
		// login 성공시 && session 저장
		String id = loginDTO.getId();
		String password = loginDTO.getPassword();
		EmpDTO loginedEmp = ls.getLoginedEmp(id);
				
		request.getSession().setAttribute("id", id);
		request.getSession().setAttribute("password", password);
		request.getSession().setAttribute("name", loginedEmp.getName());
		request.getSession().setAttribute("rank", loginedEmp.getRank());
						
		// 대리결재 유효성 검사 및 대리결재 권한 있을시 position 변경
		int updatedProxy = ls.updateProxyInfo(id);
		ApprProxyDTO proxyInfo = ls.getProxyInfo(id);
		
		if(proxyInfo != null) {
			request.getSession().setAttribute("proxyInfo", proxyInfo);
			request.getSession().setAttribute("position", proxyInfo.getAuthPosition());
			request.getSession().setAttribute("exPosition", loginedEmp.getPosition());
			
		} else {
			request.getSession().setAttribute("position", loginedEmp.getPosition());
			
		}
				
		return "redirect:/apprBoards";
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		 
		 if (session != null) {
			 session.invalidate();
		 }
		 
		 return "redirect:/login";
	}
}
