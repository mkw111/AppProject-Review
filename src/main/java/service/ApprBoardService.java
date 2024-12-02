package service;

import java.util.List;
import java.util.Map;

import dao.ApprBoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.apprboard.ApprBoardDTO;
import dto.apprboard.ApprHistoryDTO;
import dto.common.EmpDTO;

@Service
@Transactional
public class ApprBoardService {

	private final ApprBoardDao apprBoardDao;

	@Autowired
	public ApprBoardService(ApprBoardDao apprBoardDao) {
		this.apprBoardDao = apprBoardDao;
	}

	public int getNextNo() {
        return apprBoardDao.getNextNo();
	}

	public EmpDTO getLoginedEmp(String id) {
        return apprBoardDao.findById(id);
	}

	public List<Map<String, Object>> getApprBoards(Map<String, Object> searchData) {
        return apprBoardDao.findByAll(searchData);
	}

	public ApprBoardDTO getApprBoard(int seq) {
        return apprBoardDao.findBySeq(seq);
	}

	public List<ApprHistoryDTO> getApprHistory(int seq) {
        return apprBoardDao.getApprHistory(seq);
	}

	//성공 실패 여부를 리턴해주는것이 좋음 : 하지 않을경우 사용자는 성공했는지 실패했는지 알수가 없음
	public int saveTemp(Map<String, Object> tempData) {
		String seq = (String) tempData.get("seq");

		int tempSaved = 0;
		if (seq == null || seq.isEmpty()) {
			tempSaved = apprBoardDao.insertTempSave(tempData);
		} else {
			tempSaved = apprBoardDao.updateTempSave(tempData);
		}

		return tempSaved;
	}

	//해당 메서드도 위와 동일
	public void apprSave(Map<String, Map<String, Object>> combinedData) {

		// 결재 데이터
		Map<String, Object> apprData = combinedData.get("approval");

		int apprSaved = 0;
		String seq = (String) apprData.get("seq");
		if (seq == null || seq.isEmpty()) {
			apprSaved = apprBoardDao.insertApprSave(apprData);
		} else {
			apprSaved = apprBoardDao.updateApprSave(apprData);
		}

		// history 데이터
		Map<String, Object> historyData = combinedData.get("history");
		int historySaved = apprBoardDao.insertHistorySave(historyData);

	}

	public void apprReject(Map<String, Map<String, Object>> combinedData) {

		// 결재 데이터
		Map<String, Object> rejectionData = combinedData.get("rejection");
		int rejectionSaved = apprBoardDao.rejectAppr(rejectionData);

		// history 데이터
		Map<String, Object> historyData = combinedData.get("history");
		int historySaved = apprBoardDao.insertHistorySave(historyData);
	}

	public List<EmpDTO> getProxyApprover(Integer rank) {
        return apprBoardDao.findByRank(rank);
	}

	public void grantProxyAppr(Map<String, String> proxyForm) {
		int proxyResult = apprBoardDao.grantProxyAppr(proxyForm);

	}
}
