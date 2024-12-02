<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 결재게시판 -->
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
			<c:if test="${postTotal == 0}">
				<tr><td colspan="7" align="center"><c:out value="데이터가 없습니다."/></td></tr>
			</c:if>
			<c:forEach var="apprboard" items="${apprBoards}">
			<tr>
				<td><c:out value="${apprboard.seq}"/></td>
				<td><c:out value="${apprboard.name}"/></td>
				<td><c:out value="${apprboard.subject}"/></td>
				<td><f:formatDate pattern="yyyy/MM/dd" value="${apprboard.regDate}"/></td>
				<td><f:formatDate pattern="yyyy/MM/dd" value="${apprboard.appDate}"/></td>
				<td><c:out value="${apprboard.approver}"/></td>
				<td><c:out value="${apprboard.appContent}"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>	
