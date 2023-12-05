<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="app.model.DTO.UserDto" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>
<style>
    .main {
        height: 100vh;
    }

    #profile {
        margin-top: 56px;
        height: 45%;
    }

    .avatar {
        height: 100%;
    }

    .avatar > img {
        display: inline;
        align-content: center;
        height: 100%;
        width: auto;
    }

    img {
        margin: 16px;
        width: auto;
        height: 346px;
    }

    .non-display {
        display: none;
    }
</style>
<div class="main container">
    <div id="profile" class="row">
        <div class="avatar col-lg-4">
            <c:set var="imgPath" value="${user.avatar}"/>
            <img id="avatar" src="<c:out value='${imgPath}' />" alt="Avatar">
            <br>
        </div>
        <div class="infor col-lg-6">
            <span>Name: ${user.name}</span>
            <br>
            <c:set var="user" value="${requestScope.user}"/>
            <c:set var="gender" value="Unknown"/>

            <c:choose>
                <c:when test="${user.gender}">
                    <c:set var="gender" value="Nam"/>
                </c:when>
                <c:otherwise>
                    <c:set var="gender" value="Nữ"/>
                </c:otherwise>
            </c:choose>
            <span>Gender: ${gender}</span>
            <br>
            <span>Day Of Birth: ${user.dob}</span>
            <br>
            <span>Email: ${user.user_email}</span>
        </div>
        <hr>

    </div>
    <form action="/Feelbook/api/user/setAvatar" method="post" enctype="multipart/form-data">
        <input id="inputFile" type="file" name="imgFile" accept="image/png, image/jpeg, image/jpg, video/mp4"/>
        <button id="btn-submitAvatar" value="submit">Set Avatar</button>
    </form>
    <div class="row">
        <form action="/Feelbook/post" method="post" enctype="multipart/form-data">
            <input type="text" name="content">
            <br>
            <input type="file" name="post-img" accept="image/png, image/jpeg, image/jpg, video/mp4"/>
            <br>
            <button value="submit"> Post</button>
        </form>
    </div>
    <button id="autho" class="non-display" value="${autho}"></button>
    <input id="user-id" class="non-display" type="text" value="${user.user_id}" readonly>
    <div class="row" id="posts">

    </div>

</div>

<script src="/Feelbook/server-resources/javascript/profile.js"></script>
</body>
</html>