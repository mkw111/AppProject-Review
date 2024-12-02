<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>대리결재</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/apprProxy.css">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script>
	$(function(){
		
		// popup창 닫기
		$("#cancelBtn").on("click", function(){
			window.close();
		});
		
		$("#proxyApprover").on("change", function(){
			
			// 영문직급을 한글로 변환
			function getKorPosition(engPosition) {
				switch(engPosition) {
					case "SM":
						return "부장";
					case "M":
						return "과장";
					case "AM":
						return "대리";
					default:
						return "사원";
				 };
			};
			
			// 직급란
			let selectedOption = $(this).find("option:selected");
			let proxPosition = getKorPosition(selectedOption.data("position"));
			$("#proxy").val(proxPosition);
			
			// 대리자란
			let authorizer = "${sessionScope.name}";
			let authPosition = getKorPosition("${sessionScope.position}");
			$("#approver").val(authorizer + '(' + authPosition + ')');
		
			// 리셋시 빈화면
			if(selectedOption.val() == "") {
				$("#proxy").val("");
				$("#approver").val("");
			}
		
			// 전송을 위해 대입
			$("#proxyPosition").val(selectedOption.data("position"));
			
		});	
			
			// 승인 ajax
			$("#grantBtn").on("click", function(){
				$.ajax({
					url: "/apprProxy", 
					method: "post",
					data: $("#proxyForm").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(result) {
						if(result === 'success') {
							window.alert("대리결재 승인 성공하였습니다");
							window.close();
														
						} else {
							window.alert("대리결재 승인 실패하였습니다.");
						}
					},
					error: function(xhr, status, msg) {
						console.log("Error: " + error);
					}
					
				})
			})
			
		
	})
	</script>
</head>
<body>
	<form method="post" name="proxyForm" id="proxyForm">
		대리결재자:<select name="proxyApprover" id="proxyApprover">
					<option value="">선택</option>
					<c:forEach var="proxApprover" items="${proxApprovers}" >
						<option value="${proxApprover.id}" data-position="${proxApprover.position}"><c:out value="${proxApprover.name}"/></option>
					</c:forEach>
				</select><br>
		직급: <input type="text" name="proxy" id="proxy" readonly><br>  
			 <input type="hidden" name="proxyPosition" id="proxyPosition">
		
		대리자: <input type="text" name="approver" id="approver" readonly><br>
			  <input type="hidden" name="authorizer" id="authorizer" value="${sessionScope.id}">
			  <input type="hidden" name="authPosition" id="authPosition" value="${sessionScope.position}">
			  
		<div class="button-group">
			<input type="button" name="cancelBtn" id="cancelBtn" value="취소">
			<input type="button" name="grantBtn" id="grantBtn" value="승인">
		</div>
	</form>		
</body>
</html>