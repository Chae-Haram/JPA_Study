<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../layout/header.jsp"%>
<h2>login</h2>
<hr>

<form method="post">
    <p><input type="text" name="userId" placeholder="ID를 입력하세요" required></p>
    <p><input type="password" name="userPw" placeholder="PW를 입력하세요" required></p>
    <p><input type="submit"></p>
</form>

</body>
</html>