<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cpath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Simple Board</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${cpath}/home">게시판</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${cpath}/member/join">회원가입</a>
                </li>
                <c:if test="${empty login}">
                <li class="nav-item">
                    <a class="nav-link" href="${cpath}/member/login">로그인</a>
                </li>
                </c:if>
                <c:if test="${not empty login}">
                <li class="nav-item">
                    <a class="nav-link" href="${cpath}/member/logout">로그아웃</a>
                </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="${cpath}/board/list">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${cpath}/board/write">글쓰기</a>
                </li>
            </ul>
            <form class="d-flex m-auto">
                <input class="form-control me-2" type="text" placeholder="Search">
                <button class="btn btn-primary" type="button">Search</button>
            </form>
        </div>
    </div>
</nav>
