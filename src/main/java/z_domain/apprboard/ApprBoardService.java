package z_domain.apprboard;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import z_domain.common.EmpDTO;

@Service
@Transactional
public class ApprBoardService {

	private final ApprBoardDao ad;

	@Autowired
	public ApprBoardService(ApprBoardDao ad) {
		this.ad = ad;
	}

	public int getNextNo() {
		int nextNo = ad.getNextNo();
		return nextNo;
	}

	public EmpDTO getLoginedEmp(String id) {
		EmpDTO loginedEmp = ad.findById(id);
		return loginedEmp;
	}

	public List<Map<String, Object>> getApprBoards(Map<String, Object> searchData) {
		List<Map<String, Object>> apprBoards = ad.findByAll(searchData);
		return apprBoards;
	}

	public ApprBoardDTO getApprBoard(int seq) {
		ApprBoardDTO apprBoard = ad.findBySeq(seq);
		return apprBoard;
	}

	public List<ApprHistoryDTO> getApprHistory(int seq) {
		List<ApprHistoryDTO> ApprHistory = ad.getApprHistory(seq);
		return ApprHistory;
	}

	public void saveTemp(Map<String, Object> tempData) {
		String seq = (String) tempData.get("seq");

		int tempSaved = 0;
		if (seq == null || seq.isEmpty()) {
			tempSaved = ad.insertTempSave(tempData);
		} else {
			tempSaved = ad.updateTempSave(tempData);
		}

	}

	public void apprSave(Map<String, Map<String, Object>> combinedData) {

		// 결재 데이터
		Map<String, Object> apprData = combinedData.get("approval");

		int apprSaved = 0;
		String seq = (String) apprData.get("seq");
		if (seq == null || seq.isEmpty()) {
			apprSaved = ad.insertApprSave(apprData);
		} else {
			apprSaved = ad.updateApprSave(apprData);
		}

		// history 데이터
		Map<String, Object> historyData = combinedData.get("history");
		int historySaved = ad.insertHistorySave(historyData);

	}

	public void apprReject(Map<String, Map<String, Object>> combinedData) {

		// 결재 데이터
		Map<String, Object> rejectionData = combinedData.get("rejection");
		int rejectionSaved = ad.rejectAppr(rejectionData);

		// history 데이터
		Map<String, Object> historyData = combinedData.get("history");
		int historySaved = ad.insertHistorySave(historyData);
	}

	public List<EmpDTO> getProxyApprover(Integer rank) {
		List<EmpDTO> proxApprovers = ad.findByRank(rank);
		return proxApprovers;
	}

	public void grantProxyAppr(Map<String, String> proxyForm) {
		int proxyResult = ad.grantProxyAppr(proxyForm);

	}
}
