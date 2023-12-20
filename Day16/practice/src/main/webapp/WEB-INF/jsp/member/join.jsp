<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../layout/header.jsp"%>
<h2>join</h2>
<hr>

<div>
    <form method="post">
        <p><input type="text" name="userId" placeholder="ID를 입력하세요" required></p>
        <p><input type="password" name="userPw" placeholder="PW를 입력하세요" required></p>
        <p><input type="text" name="username" placeholder="이름을 입력하세요" required></p>
        <p><input type="number" name="age" placeholder="나이를 입력하세요" required></p>
        <p><input type="text" name="address" placeholder="주소를 입력하세요" required></p>
        <p><input type="text" name="email" placeholder="이메일을 입력하세요" required></p>
        <p><input type="text" name="pNum" placeholder="전화번호를 입력하세요" required></p>
        <input type="submit">
    </form>

</div>

</body>
</html>