package z_domain.apprboard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import z_domain.common.EmpDTO;

@Repository
public class ApprBoardDao {

	private final SqlSession session;

	@Autowired
	public ApprBoardDao(SqlSession session) {
		this.session = session;
	}

	public int getNextNo() {
		int result = 0;

		try {
			result = session.selectOne("getNextNo");
		} catch (Exception e) {
			System.out.println("ApprDao getNextNo e.getMessage() -> " + e.getMessage());
		}
		
		return result;
	}

	public EmpDTO findById(String id) {
		EmpDTO loginedEmp = null;

		try {
			loginedEmp = session.selectOne("ApprBoardDao.findById", id);
		} catch (Exception e) {
			System.out.println("ApprDao e.getMessage() -> " + e.getMessage());
		}
		
		return loginedEmp; 
	}

	public List<Map<String, Object>> findByAll(Map<String, Object> searchData) {
		List<Map<String, Object>> apprBoards = null;

		try {
			apprBoards = session.selectList("findByAll", searchData);
		} catch (Exception e) {
			System.out.println("ApprDao findByAll e.getMessage() -> " + e.getMessage());
		}
		
		return apprBoards;
	}

	public ApprBoardDTO findBySeq(int seq) {
		ApprBoardDTO apprBoard = null;

		try {
			apprBoard = session.selectOne("findBySeq", seq);
		} catch (Exception e) {
			System.out.println("ApprDao findBySeq e.getMessage() -> " + e.getMessage());
		}
		
		return apprBoard;
	}

	public List<ApprHistoryDTO> getApprHistory(int seq) {
		List<ApprHistoryDTO> ApprHistory = null;
		
		try {
			ApprHistory = session.selectList("getApprHistory", seq);
		} catch (Exception e) {
			System.out.println("ApprDao getApprHistory e.getMessage() -> " + e.getMessage());
		}
		
		return ApprHistory;
	}

	public int insertTempSave(Map<String, Object> tempData) {
		int insertTempSave = 0;

		try {
			insertTempSave = session.insert("tempSave", tempData);
		} catch (Exception e) {
			System.out.println("ApprDao tempSave e.getMessage() -> " + e.getMessage());
		}
		
		return insertTempSave;
	}

	public int updateTempSave(Map<String, Object> tempData) {
		int updateTempSave = 0;

		try {
			updateTempSave = session.update("tempUpdate", tempData);
		} catch (Exception e) {
			System.out.println("ApprDao tempUpdate e.getMessage() -> " + e.getMessage());
		}
		
		return updateTempSave;
	}

	public int insertApprSave(Map<String, Object> apprData) {
		int insertApprSave = 0;

		try {
			insertApprSave = session.insert("approvalSave", apprData);
		} catch (Exception e) {
			System.out.println("ApprDao approvalSave e.getMessage() -> " + e.getMessage());
		}
		
		return insertApprSave;
	}

	public int updateApprSave(Map<String, Object> apprData) {
		int updateApprSave = 0;

		try {
			updateApprSave = session.update("approvalUpdate", apprData);
		} catch (Exception e) {
			System.out.println("ApprDao approvalUpdate e.getMessage() -> " + e.getMessage());
		}
		
		return updateApprSave;
	}

	public int insertHistorySave(Map<String, Object> historyData) {
		int insertHistorySave = 0;

		try {
			insertHistorySave = session.insert("historySave", historyData);
		} catch (Exception e) {
			System.out.println("ApprDao historySave e.getMessage() -> " + e.getMessage());
		}
		
		return insertHistorySave;
	}

	public int rejectAppr(Map<String, Object> rejectionData) {
		int rejectAppr = 0;

		try {
			rejectAppr = session.update("approvalReject", rejectionData);
		} catch (Exception e) {
			System.out.println("ApprDao approvalReject e.getMessage() -> " + e.getMessage());
		}
		
		return rejectAppr;
	}

	public List<EmpDTO> findByRank(Integer rank) {
		List<EmpDTO> proxApprovers = null;
		
		try {
			proxApprovers = session.selectList("findByRank", rank);
		} catch (Exception e) {
			System.out.println("ApprDao findByRank e.getMessage() -> " + e.getMessage());
		}
		
		return proxApprovers;
	}
	
	public int grantProxyAppr(Map<String, String> proxyForm) {
		int proxyResult = 0;
		
		try {
			proxyResult = session.insert("saveProxyAppr", proxyForm);
		} catch (Exception e) {
			System.out.println("ApprDao saveProxyAppr e.getMessage() -> " + e.getMessage());
		}
		
		return proxyResult;
	}

	public void updateProxyStatus() {
		try {
			session.update("updateProxyStatus");
		} catch (Exception e) {
			System.out.println("ApprDao updateProxyStatus e.getMessage() -> " + e.getMessage());
		}
		
	}

	

}
