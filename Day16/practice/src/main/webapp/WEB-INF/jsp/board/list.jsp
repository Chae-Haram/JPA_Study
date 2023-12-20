<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../layout/header.jsp"%>
<h2>list</h2>
<hr>

<div class="container mt-3">
    <table class="table table-hover">
        <thead>
            <tr>
                <th>글번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
            </thead>
        <tbody>
            <c:forEach items="${list}" var="dto">
            <tr>
                <td>${dto.boardId}</td>
                <td><a href="${cpath}/board/view/${dto.boardId}">${dto.title}</a></td>
                <td>${dto.writer.username}</td>
                <td>${dto.createdDate}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
