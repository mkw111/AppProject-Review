package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.apprboard.ApprProxyDTO;
import dto.common.EmpDTO;
import dto.LoginDTO;
import service.LoginService;

@Controller
public class LoginController {

	//알아보기 힘든 변수명은 지양, 알아보기 쉽게 변경
	private final LoginService loginService;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
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

	/**
	 * 비즈니스 로직은 최대한 서비스 단으로 옮겨서 가리는 방향으로
	 * 컨트롤러의 기능은 보내주는 중계의 기능이라 단순 파라미터 검증 또는 간단한 로직들만 들어가야함
	 * 나머지 복잡한 비즈니스 로직은 최대한 서비스로 이동
	 * @param loginDTO
	 * @param empDTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@ModelAttribute("loginDTO") LoginDTO loginDTO,
			HttpServletRequest request
	) {

		// login 실패시
		boolean isIdExist = loginService.isIdExist(loginDTO);
		if(!isIdExist) {
			int totalId = 0;
			return "redirect:/login?id=" + totalId;
		}

		boolean isPasswordCorrect = loginService.isPasswoardCorrect(loginDTO);
		if(!isPasswordCorrect) {
			int checkedPassword = 0;
			return "redirect:login?password=" + checkedPassword;
		}

		///////////////////////////////////여기서 부터 //////////////////////////////
		// login 성공시 && session 저장
		String id = loginDTO.getId();
		String password = loginDTO.getPassword();
		EmpDTO loginedEmp = loginService.getLoginedEmp(id);

		request.getSession().setAttribute("id", id);
		request.getSession().setAttribute("password", password);
		request.getSession().setAttribute("name", loginedEmp.getName());
		request.getSession().setAttribute("rank", loginedEmp.getRank());

		// 대리결재 유효성 검사 및 대리결재 권한 있을시 position 변경
		int updatedProxy = loginService.updateProxyInfo(id);
		ApprProxyDTO proxyInfo = loginService.getProxyInfo(id);

		if(proxyInfo != null) {
			request.getSession().setAttribute("proxyInfo", proxyInfo);
			request.getSession().setAttribute("position", proxyInfo.getAuthPosition());
			request.getSession().setAttribute("exPosition", loginedEmp.getPosition());

		} else {
			request.getSession().setAttribute("position", loginedEmp.getPosition());

		}
		///////////////////////////여기까지는 서비스로 빠져도 괜찮을거 같음//////////////
				
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
