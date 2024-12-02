<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>결재시스템</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		$(function() {
			// 체크박스
			let statusContent = "${appContent}";
			let apprPosition = "${apprPosition}";
						
			if(statusContent == "결재대기") {
				$("#reqStatus").prop("checked", true);
			} else if(statusContent == "결재중") {
				$("#reqStatus").prop("checked", true);
				$("#mgrStatus").prop("checked", true);
			} else if(statusContent == "결재완료") {
				$("input[type=checkbox]").prop("checked", true);
			} else if(statusContent == "반려") {
				$("input[type=checkbox]").prop("checked", false);
			}
			
			// 공통 ajax(임시저장, 반려, 승인)
			function sendAjax(url, data, msg) {
				$.ajax({
					url: url,
					type: "post",
					data: JSON.stringify(data),
					contentType: "application/json; charset=utf-8",
					success: function(result) {
						if(result === "success") {
							alert(msg);
							location.href = "/apprBoards";
						}
					},
					error: function(xhr, status, err) {
						console.error('Error: ' + err);
					}
				})
			}
			
					
			// 임시저장
			$("#tempSaveBtn").click(function() {
				let tempData = {
					seq: $("#seq").val(),
					id: $("#id").val(),
					subject: $("#subject").val(),
					content: $("#content").val(),
					appStatus: 'AA',
					position: $("#position").val()  
				};
			
			sendAjax("/tempSave", tempData, "임시저장되었습니다.");
			
			})
			
			
			// 결재
			$("#approvalBtn").click(function() {
				
				let position;
				if('${sessionScope.exPosition}' && '${sessionScope.exPosition}' !== '') {
					
					if($("#seq").val() == '' || statusContent == '임시저장') {
						position = '${sessionScope.exPosition}';
					} else if(('${sessionScope.exPosition}' == 'S' || '${sessionScope.exPosition}' == 'AM') && '${sessionScope.position}' == 'M' && statusContent == '결재대기') {
						position = 'M';
					} else if('${sessionScope.exPosition}' == 'M' && '${sessionScope.position}' == 'SM' && statusContent == '결재중') {
						position = 'SM';
					}
				
				} else {
					position = "${sessionScope.position}";
				}
							
				
				let appStatus; // 결재상태
				if(position == "S" || position == "AM") {
					appStatus = "BB";
				} else if(position == "M") {
					appStatus = "CC"
				} else if(position == "SM") {
					appStatus = "DD"
				}
												
				// 결재정보
				let approvalData = {
					seq: $("#seq").val(),
					id: $("#id").val(),
					subject: $("#subject").val(),
					content: $("#content").val(),
					position: position,
					appDate: "",
					approver: "${sessionScope.id}",  
					appStatus: appStatus
				};
							
				// 히스토리 정보
				let historyData = {
					seq: "${no}",
					approver: "${sessionScope.id}",
					position: position,
					appStatus: appStatus
				};
				
				// ajax로 2개 데이터 묶어서 보냄
				let combinedData = {
					approval: approvalData,
					history: historyData
				}
					
				sendAjax("/apprSave", combinedData, (position == "S" || position == "AM")? "결재요청하였습니다." : "결재하였습니다.");
			})
				
			// 반려
			$("#rejectionBtn").click(function() {
				// 반려 정보
				let rejectionData = {
					seq: $("#seq").val(),
					id: $("#id").val(),
					subject: $("#subject").val(),
					content: $("#content").val(),
					position: $("#position").val(),
					approver: "${sessionScope.id}",
					appStatus: 'EE'
				};
				
				// 히스토리 정보
				let historyData = {
						seq: "${no}",
						approver: "${sessionScope.id}",
						appStatus: 'EE',
						position: $("#position").val()
	
				};
				
				// ajax로 2개 데이터 묶어서 보냄
				let combinedData = {
					rejection: rejectionData,
					history: historyData
				};
				
				sendAjax("/apprReject", combinedData, "반려하였습니다.");
			})
		})
	</script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/apprBoard.css">
</head>
<body>
	<!-- 체크박스 -->
	<table border="1">
		<tr>
			<th>결재요청</th>
			<th>과 장</th>
			<th>부 장</th>
		</tr>
		<tr>
			<td><input type="checkbox" id = "reqStatus" disabled></td>
			<td><input type="checkbox" id = "mgrStatus" disabled></td>
			<td><input type="checkbox" id = "smgrStatus" disabled></td>
		</tr>
	</table>

	<!-- 본문 -->
	<form action="/approvalCreate" method="post">
		<fieldset>
			번호: ${no} 
				<input type="hidden" name="seq" id="seq" value="${apprBoard.seq}"><br> 
			작성자: ${name} 
				  <input type="hidden" name="id" id="id" value="${sessionScope.id}">
				  <input type="hidden" id="position" name="position" value="${sessionScope.position}"><br>
			제목: <c:choose>
					<c:when test="${appContent == '결재대기' || appContent == '결재중' || appContent == '결재완료' 
										|| ((sessionScope.position == 'M' || sessionScope.position == 'SM') && appContent == '반려')}">
						<input type="text" name="subject" id="subject" value="${apprBoard.subject}" readonly="readonly">
					</c:when>
					<c:otherwise>
						<input type="text" name="subject" id="subject" value="${apprBoard.subject}"	>
					</c:otherwise>
				 </c:choose>
			내용:	<c:choose>
					<c:when test="${appContent == '결재대기' || appContent == '결재중' || appContent == '결재완료' 
										|| ((sessionScope.position == 'M' || sessionScope.position == 'SM') && appContent == '반려')}">
						<textarea rows="5" cols="25" name="content" id="content" readonly="readonly">${apprBoard.content}</textarea>
					</c:when>
					<c:otherwise>
						<textarea rows="5" cols="25" name="content" id="content">${apprBoard.content}</textarea>
					</c:otherwise>
				 </c:choose> 
		
			<div class="button-group">
				<c:if
					test="${(sessionScope.position == 'S' || sessionScope.position == 'AM') && (appContent == null || appContent == '임시저장' || appContent == '반려')}">
					<input type="button" id="tempSaveBtn" name="tempSaveBtn" value="임시저장">
					<input type="button" id="approvalBtn" name="approvalBtn" value="결재">
				</c:if>
			
				<c:if
					test="${(sessionScope.position == 'M' || sessionScope.position == 'SM') && (appContent == null || appContent == '임시저장')}">
					<input type="button" id="tempSaveBtn" name="tempSaveBtn" value="임시저장">
					<input type="button" id="approvalBtn" name="approvalBtn" value="결재">
				</c:if>
			
				<c:if test="${sessionScope.position == 'M' && appContent == '결재대기'}">
					<input type="button" id="rejectionBtn" name="rejectionBtn" value="반려">
					<input type="button" id="approvalBtn" name="approvalBtn" value="결재">
				</c:if>
		
				<c:if test="${sessionScope.position == 'SM' && appContent == '결재중'}">
					<input type="button" id="rejectionBtn" name="rejectionBtn" value="반려">
					<input type="button" id="approvalBtn" name="approvalBtn" value="결재">
				</c:if>
			</div>
		</fieldset>
	</form>

	<!-- history -->
	<table id="historyTable" border="1">
		<tr>
			<th>번호</th>
			<th>결재일</th>
			<th>결재자</th>
			<th>결재상태</th>
		</tr>
		<c:forEach var = "apprHistory" items="${apprHistory}">
		<tr>
			<td><c:out value="${apprHistory.no}"/></td>
			<td><c:out value="${apprHistory.regDate}"/></td>
			<td><c:out value="${apprHistory.approver}"/></td>
			<td><c:out value="${apprHistory.appStatus}"/></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>