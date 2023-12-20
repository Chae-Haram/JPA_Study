<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../layout/header.jsp"%>
<h2>write</h2>
<hr>

<div class="container">
    <form method="post">
        <p>
            <label>제목</label>
            <input type="text" class="form-control" name="title" value="${board.title}" required>
        </p>
        <p>
            <label>내용</label>
            <textarea class="form-control" name="content" rows="20" cols="150" required>${board.content}</textarea>
        </p>
        <input type="submit" value="수정 완료">
    </form>

</div>