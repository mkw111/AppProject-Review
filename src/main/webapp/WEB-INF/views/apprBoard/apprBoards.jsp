<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>결재시스템</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		$(function(){
			
			// 로그아웃
			$("#logout").click(function(){
				$.ajax({
					url: "/logout",
					method: "post",
					success:function(){
						location.href="/login";
					}
				});	
			});
			
			// 검색 후 초기화
			$("#reset").click(function(){
				$("#type").val(""); 
		        $("#keyword").val(""); 
		        $("#startDate").val(""); 
		        $("#endDate").val("");
		        $("#appStatus").val("");
			});
			
			// 검색(비동기)
			$("#appStatus").change(function(){
				let searchData = {
									type: $("#type").val(),
									keyword: $("#keyword").val(),
									appStatus: $("#appStatus").val(),
									startDate: $("#startDate").val(),
									endDate: $("#endDate").val(),
								  };
				
				$.ajax({
					url: "/appStatusSearch",
					type: "post",
					data: JSON.stringify(searchData),
					contentType: "application/json; charset=utf-8",
					success:function(data){
								$("#sepApprBoards").html(data);
							},
					error:function(xhr, status, error) {
							console.error('Error: ' + error);
						   }
				});
			});
			
			// 키워드 입력란 검증
			$("#type").change(function(){
				if($(this).val() != "") {
					$("#keyword").val("키워드를 입력하세요");
				} else {
					$("#keyword").val("");
				}
			})
			
			// 상세페이지 검색
		    $("#sepApprBoards").on("click", "tr", function(){
		        let seq = $(this).find("td:eq(0)").text(); 
		        let name = $(this).find("td:eq(1)").text();
		        let appContent = $(this).find("td:eq(6)").text();
		        let apprPosition = $("#apprPosition_" + seq).val(); // S
		        location.href = "/apprBoards/" + seq + "?name=" + name + "&appContent=" + appContent + "&apprPosition=" + apprPosition;  
		    });
						
			// proxy창
			$("#approvalProxyBtn").click(function(){
				let width = 400;
				let height = 500;
				let left = (window.innerWidth / 2) - (width / 2);
				let top = (window.innerHeight / 2) - (height / 2);
				
				let rank = "${sessionScope.rank}";
				
				window.open("/apprProxy?rank=" + rank, "대리결제", "width=" + width + ", height=" + height + ", left=" + left + ", top=" + top);
			})
		})	
	</script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/apprBoards.css">
</head>
<body>
	<!-- 인사말 -->
	${sessionScope.name}<c:choose><c:when test="${sessionScope.rank eq 4}">(사원)</c:when><c:when test="${sessionScope.rank eq 3}">(대리)</c:when><c:when test="${sessionScope.rank eq 2}">(과장)</c:when><c:when test="${sessionScope.rank eq 1}">(부장)</c:when></c:choose>
	<c:if test="${sessionScope.proxyInfo.status eq 'ACTIVE'}">- ${sessionScope.proxyInfo.name}</c:if>님 환영합니다.
	<input type="button" id="logout" name="logout" value="로그아웃">
	<input type="button" name="apprBoardCreateBtn" id="apprBoardCreateBtn" value="글쓰기" onclick="location.href='/apprBoardCreate?name=' + '${sessionScope.name}'">
	<c:if test="${sessionScope.rank eq 1 ||  sessionScope.rank eq 2}">
		<input type="button" name="approvalProxyBtn" id="approvalProxyBtn" value="대리결재">
	</c:if><br>
	<c:if test="${sessionScope.proxyInfo.status eq 'ACTIVE'}">
		(대리결재일: <f:formatDate value="${sessionScope.proxyInfo.proxyStartDate}" pattern="yyyy/MM/dd hh:mm:ss"/> 
		~ 
		<f:formatDate value="${sessionScope.proxyInfo.proxyEndDate}" pattern="yyyy/MM/dd hh:mm:ss"/>)
	</c:if>
	
	<!-- 검색란 -->
	<form name="searchForm" id="searchForm" method="post">
		<fieldset>
			<select name="type" id="type">
				<option value="" selected>선택</option>
				<option value="name"<c:if test="${searchData.type == 'name'}">selected</c:if>>작성자</option>
				<option value="approver"<c:if test="${searchData.type == 'approver'}">selected</c:if>>결재자</option>
				<option value="subject"<c:if test="${searchData.type == 'subject'}">selected</c:if>>제목+내용</option>
			</select>
			<input type="text" name="keyword" id="keyword" value="${searchData.keyword}">
			<select name="appStatus" id="appStatus">
				<option value="" selected>결재상태</option>
				<option value="AA">임시저장</option>
				<option value="BB">결재대기</option>
				<option value="CC">결재중</option>
				<option value="DD">결재완료</option>
				<option value="EE">반려</option>
			</select><br>
			<input type="date" name="startDate" id="startDate" value="${searchData.startDate}"> ~ <input type="date" name="endDate" id="endDate" value="${searchData.endDate}">
			<input type="submit" value="검색">
			<input type="button" name="reset" id="reset" onclick="reset()" value="초기화">
		</fieldset>
	</form>
	
	<!-- 게시판 -->
	<div id="sepApprBoards">
		<table>
		<c:out value="총 데이터수: ${postTotal}"/>
			<thead>
				<tr>
					<th>번호</th>			
					<th>작성자</th>
					<th>제목</th>
					<th>작성일</th>
					<th>결재일</th>
					<th>결재자</th>
					<th>결재상태</th>
				</tr>	
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${postTotal != 0}">
						<c:forEach var="apprBoard" items="${apprBoards}">
						<tr>
							<td><c:out value="${apprBoard.seq}"/></td>
							<td><c:out value="${apprBoard.name}"/></td>
							<td><c:out value="${apprBoard.subject}"/></td>
							<td><f:formatDate pattern="yyyy/MM/dd" value="${apprBoard.regDate}"/></td>
							<td><f:formatDate pattern="yyyy/MM/dd" value="${apprBoard.appDate}"/></td>
							<td>
								<c:out value="${apprBoard.approver}"/>
								<input type="hidden" value="${apprBoard.position}" name="apprPosition" id="apprPosition_${apprBoard.seq}"> <!-- 승인자의 position -->
							</td>
							<td><c:out value="${apprBoard.appContent}"/></td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="7" align="center"><c:out value="데이터가 없습니다."/></td></tr>			
					</c:otherwise>
				</c:choose>	
			</tbody>
		</table>
	</div>
</body>
</html>