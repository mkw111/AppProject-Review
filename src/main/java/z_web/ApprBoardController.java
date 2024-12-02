package z_web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import z_domain.apprboard.ApprBoardDTO;
import z_domain.apprboard.ApprBoardService;
import z_domain.apprboard.ApprHistoryDTO;
import z_domain.apprboard.ApprProxyDTO;
import z_domain.common.EmpDTO;

@Controller
public class ApprBoardController {

	private final ApprBoardService as;

	@Autowired
	public ApprBoardController(ApprBoardService as) {
		this.as = as;
	}

	@RequestMapping(value = "/apprBoardCreate", method = RequestMethod.GET)
	public String createForm(Model model) {
		// 게시글 번호
		int nextNo = as.getNextNo();
		model.addAttribute("no", nextNo);
		
		return "apprBoard/apprBoard";
	}

	@RequestMapping(value = "/apprBoards", method = { RequestMethod.GET, RequestMethod.POST })
	public String apprBoards(
			@RequestParam(required = false) Map<String, Object> searchData, 
			HttpServletRequest request,
			Model model
	) {
		String id = (String) request.getSession().getAttribute("id");
		EmpDTO loginedEmp = as.getLoginedEmp(id);
		ApprProxyDTO proxyInfo = (ApprProxyDTO) request.getSession().getAttribute("proxyInfo");
		
		// 검색데이터에 정보 추가
		if(proxyInfo != null) { // 대리결재 권한 있을시
			searchData.put("id", proxyInfo.getAuthorizer());
			searchData.put("name", loginedEmp.getName());
			searchData.put("position",proxyInfo.getAuthPosition());
			
		} else { // 대리결재 권한 없을시
			searchData.put("id", id);
			searchData.put("name", loginedEmp.getName());
			searchData.put("position", loginedEmp.getPosition());
		}
		
		List<Map<String, Object>> apprBoards = as.getApprBoards(searchData);
		
		
		// 총 게시글 수
		int postTotal = apprBoards.isEmpty() ? 0 : apprBoards.size();

		model.addAttribute("apprBoards", apprBoards);
		model.addAttribute("searchData", searchData);
		model.addAttribute("postTotal", postTotal);

		return "apprBoard/apprBoards";
	}

	// 비동기 검색
	@RequestMapping("/appStatusSearch")
	public String appStatusSearch(
			@RequestBody Map<String, Object> searchData, 
			HttpServletRequest request,
			Model model
	) {
		String id = (String) request.getSession().getAttribute("id");
		EmpDTO loginedEmp = as.getLoginedEmp(id);
		ApprProxyDTO proxyInfo = (ApprProxyDTO) request.getSession().getAttribute("proxyInfo");
		
		// 검색데이터에 정보 추가
		if (proxyInfo != null) { // 대리결재 권한 있을시
			searchData.put("id", proxyInfo.getAuthorizer());
			searchData.put("name", loginedEmp.getName());
			searchData.put("position", proxyInfo.getAuthPosition());

		} else { // 대리결재 권한 없을시
			searchData.put("id", id);
			searchData.put("name", loginedEmp.getName());
			searchData.put("position", loginedEmp.getPosition());
		}

		List<Map<String, Object>> apprBoards = as.getApprBoards(searchData);
		
		
		// 총 게시글 수
		int postTotal = apprBoards.isEmpty() ? 0 : apprBoards.size();

		model.addAttribute("apprBoards", apprBoards);
		model.addAttribute("searchData", searchData);
		model.addAttribute("postTotal", postTotal);

		return "apprBoard/sepApprBoards";
	}

	@RequestMapping("/apprBoards/{seq}")
	public String apprBoard(@PathVariable("seq") int seq, 
			@RequestParam("name") String name,
			@RequestParam("appContent") String appContent, 
			@RequestParam("apprPosition") String apprPosition,
			Model model
	) {
		ApprBoardDTO apprBoard = as.getApprBoard(seq);
		List<ApprHistoryDTO> apprHistory = as.getApprHistory(seq);

		model.addAttribute("no", seq);
		model.addAttribute("name", name);
		model.addAttribute("appContent", appContent);
		model.addAttribute("apprPosition", apprPosition);
		model.addAttribute("apprBoard", apprBoard);
		model.addAttribute("apprHistory", apprHistory);

		return "apprBoard/apprBoard";
	}

	// 글쓰기 임시저장
	@RequestMapping(value = "/tempSave", method = RequestMethod.POST)
	@ResponseBody
	public String tempSave(@RequestBody Map<String, Object> tempData) {
		as.saveTemp(tempData);
		return "success";
	}

	// 글쓰기 결재
	@RequestMapping(value = "/apprSave", method = RequestMethod.POST)
	@ResponseBody
	public String apprSave(@RequestBody Map<String, Map<String, Object>> combinedData) {
		as.apprSave(combinedData);
		return "success";
	}

	// 글쓰기 반려
	@RequestMapping(value = "/apprReject", method = RequestMethod.POST)
	@ResponseBody
	public String apprReject(@RequestBody Map<String, Map<String, Object>> combinedData) {
		as.apprReject(combinedData);
		return "success";
	}
	
	// 대리결재
	@RequestMapping(value = "/apprProxy", method = RequestMethod.GET)
	public String apprProxy(@RequestParam("rank") Integer rank, Model model) {
		List<EmpDTO> proxApprovers = as.getProxyApprover(rank);
		model.addAttribute("proxApprovers", proxApprovers); 
		return "apprBoard/apprProxy";
	}
	
	// 대리결재 승인 
	@RequestMapping(value = "/apprProxy", method = RequestMethod.POST)
	@ResponseBody
	public String grantProxyAppr(
			@RequestParam Map<String, String> proxyForm,
			Model model,
			HttpServletRequest request
	) {
		as.grantProxyAppr(proxyForm);
		return "success";
	}
	
	
}
