<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>대리결재 로그인</title>
	<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/loginForm.css"> --%>
	<style>
	   body {
        font-family: Arial, sans-serif; /* 기본 폰트 설정 */
        background-color: #f9f9f9; /* 배경색 */
        margin: 0; /* 기본 여백 제거 */
        padding: 0; /* 기본 패딩 제거 */
        height: 100vh; /* 전체 화면 높이 */
        display: flex; /* 플렉스 박스 사용 */
        align-items: center; /* 수직 중앙 정렬 */
        justify-content: center; /* 수평 중앙 정렬 */
    }

    .container {
        background-color: white; /* 컨테이너 배경색 */
        border-radius: 8px; /* 테두리 둥글게 */
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
        padding: 20px; /* 패딩 */
        width: 300px; /* 가로 너비 */
    }

    fieldset {
        border: 1px solid #ddd; /* 테두리 색상 */
        padding: 10px; /* 패딩 */
        margin: 0; /* 여백 없애기 */
    }

    h2 {
        text-align: center; /* 제목 중앙 정렬 */
        color: #4CAF50; /* 제목 색상 */
    }

    .form-group {
        display: flex; /* 플렉스 박스 사용 */
        align-items: center; /* 수직 중앙 정렬 */
        margin-bottom: 10px; /* 아래쪽 여백 */
    }

    label {
        flex: 0 0 80px; /* 고정 너비 */
        margin-right: 10px; /* 오른쪽 여백 */
        color: #333; /* 글자색 */
    }

    input[type="text"], input[type="password"] {
        flex: 1; /* 남은 공간을 차지 */
        padding: 8px; /* 패딩 */
        border: 1px solid #ddd; /* 테두리 색상 */
        border-radius: 4px; /* 테두리 둥글게 */
        box-sizing: border-box; /* 패딩 포함한 너비 계산 */
    }

    input[type="submit"] {
        background-color: #4CAF50; /* 버튼 배경색 */
        color: white; /* 버튼 글자색 */
        border: none; /* 테두리 없음 */
        padding: 10px; /* 패딩 */
        width: 100%; /* 너비 100% */
        cursor: pointer; /* 포인터 커서 */
        border-radius: 4px; /* 테두리 둥글게 */
    }

    input[type="submit"]:hover {
        background-color: #45a049; /* 버튼 호버 시 색상 */
    }
	</style>
	
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script>
	$(function(){
		if (${not empty id}) {
            alert("등록되지않은 사용자입니다.");
            $("#id").focus();
        }
        
        if (${not empty password}) {
            alert("비밀번호가 일치하지 않습니다.");
            $("#id").focus();
        }
               
	})
	
	</script>
	
</head>
<body>
	<div class="container" align="center">
		<form action="/login" method="post">
	    	<fieldset>
	        <h2>Login</h2>
	        <div class="form-group">
		        <label for="id">아이디:</label>
		        <input type="text" id="id" name="id" placeholder="아이디를 입력하세요." value="${longinId}" required><br>
			</div>
			<div class="form-group">
		        <label for="password">비밀번호:</label>
		        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요." value="${loginPassword}" required><br>
			</div>
	        <input type="submit" value="로그인">
	    	</fieldset>
	    </form>
	</div>
</body>
</html>