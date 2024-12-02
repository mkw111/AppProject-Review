package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.apprboard.ApprBoardDTO;
import service.ApprBoardService;
import dto.apprboard.ApprHistoryDTO;
import dto.apprboard.ApprProxyDTO;
import dto.common.EmpDTO;

@Controller
public class ApprBoardController {

	private final ApprBoardService apprBoardService;

	public ApprBoardController(ApprBoardService apprBoardService) {
		this.apprBoardService = apprBoardService;
	}

	@RequestMapping(value = "/apprBoardCreate", method = RequestMethod.GET)
	public String createForm(Model model) {
		// 게시글 번호
		int nextNo = apprBoardService.getNextNo();
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
		EmpDTO loginedEmp = apprBoardService.getLoginedEmp(id);
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
		
		List<Map<String, Object>> apprBoards = apprBoardService.getApprBoards(searchData);
		
		
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
		EmpDTO loginedEmp = apprBoardService.getLoginedEmp(id);
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

		List<Map<String, Object>> apprBoards = apprBoardService.getApprBoards(searchData);
		
		
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
		ApprBoardDTO apprBoard = apprBoardService.getApprBoard(seq);
		List<ApprHistoryDTO> apprHistory = apprBoardService.getApprHistory(seq);

		model.addAttribute("no", seq);
		model.addAttribute("name", name);
		model.addAttribute("appContent", appContent);
		model.addAttribute("apprPosition", apprPosition);
		model.addAttribute("apprBoard", apprBoard);
		model.addAttribute("apprHistory", apprHistory);

		return "apprBoard/apprBoard";
	}

	/**
	 * 무조건 성공을 리턴하고 있음 : 성공 실패 여부를 리턴해줘야함
	 * responseEntity를 사용하면 상태코드까지 리턴해줄수 있음 HttpStatus.CREATED = 201 생성성공 등등
	 * badRequest = 500 의 경우엔 AJAX호출시 failed나 error쪽으로 리턴이 오게됨 success로 오지 않음
	 * ResponseEntity를 사용하면 ResponseBody는 생략해도됨
	 * @param tempData
	 * @return
	 */
	// 글쓰기 임시저장
	@RequestMapping(value = "/tempSave", method = RequestMethod.POST)
	public ResponseEntity<Integer> tempSave(@RequestBody Map<String, Object> tempData) {
		int savedCnt = apprBoardService.saveTemp(tempData);
		//return "success";
		if(savedCnt > 0) {
			return new ResponseEntity(savedCnt, HttpStatus.CREATED);
		}else{
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	// 글쓰기 결재
	@RequestMapping(value = "/apprSave", method = RequestMethod.POST)
	@ResponseBody
	public String apprSave(@RequestBody Map<String, Map<String, Object>> combinedData) {
		apprBoardService.apprSave(combinedData);
		return "success";
	}

	// 글쓰기 반려
	@RequestMapping(value = "/apprReject", method = RequestMethod.POST)
	@ResponseBody
	public String apprReject(@RequestBody Map<String, Map<String, Object>> combinedData) {
		apprBoardService.apprReject(combinedData);
		return "success";
	}
	
	// 대리결재
	@RequestMapping(value = "/apprProxy", method = RequestMethod.GET)
	public String apprProxy(@RequestParam("rank") Integer rank, Model model) {
		List<EmpDTO> proxApprovers = apprBoardService.getProxyApprover(rank);
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
		apprBoardService.grantProxyAppr(proxyForm);
		return "success";
	}
	
	
}
