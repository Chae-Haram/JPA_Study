<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../layout/header.jsp"%>
<h2>view</h2>
<hr>

<div class="container mt-3">
    <table class="table">
        <thead>
        <tr>
            <th>글번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${board.id}</td>
                <td>${board.title}</td>
                <td>${board.member.username}</td>
                <td>${board.createdDate}</td>
            </tr>
            <tr>
                <td>${board.content}</td>
            </tr>
        </tbody>
    </table>
    <div>
        <a href="${cpath}/board/update/${board.id}"><button class="btn btn-success">수정하기</button></a>
        <a href="${cpath}/board/delete/${board.id}"><button class="btn btn-danger">삭제하기</button></a>
    </div>
</div>