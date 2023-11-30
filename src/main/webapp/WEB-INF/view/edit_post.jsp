<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <title>Document</title>
</head>
<body>
    <div class="main container">
        <div class="post row">
            <div>
                <form action="/Feelbook/post/edit?post_id=${post.post.post_id}" method="post">
                    <a href = "/Feelbook/profile?user_id=${post.user.user_id}">${post.user.name}</a>
                    <br>
                    <span>${post.post.create_at} hours ago</span>
                    <br>
                    <input type="text" name="content" value="${post.post.content}">
                    <br>
                    <img src="${post.post.post_img}" alt="image">
                    <br>
                    <button value="Submit"> Submit</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
