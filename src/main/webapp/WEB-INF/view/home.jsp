<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <title>Title</title>

</head>
<body>
<style>
    .img {
        margin: 16px;
        width: auto;
        height: 346px;
    }
    .header{
        height: 46px;
        display: block;
        width: 100%;
        background-color: blanchedalmond;
        margin: 0;
        left: 0;
        right: 0;
        position: fixed;
    }
    .main{
        top: 46px !important;
    }
    .nav{
        text-align: center !important;
        display: inline-block !important;
    }
    .nav li {
        display: inline-block;
        height: 46px;
        position: relative;
    }
    .nav > li:hover > a{
        background-color: rgb(231, 140, 95);
        font-size: 20px;
    }
    .nav li a{
        font-size: 16px;
        color: black;
        text-decoration: none;
        line-height: 46px;
        padding: 0 24px;
        display: inline-block;
    }
    .go-to-profile,
    .logout{
        float: right;
    }
</style>

<div class="header">
    <ul class="nav">
        <li><div style="height:40px;">
            <input type="text" name="key" placeholder="Search">
            <button class="btn-search" onclick="Search()">Search</button>
        </div></li>
        <li class="go-to-profile"><a href="/Feelbook/profile">My Profile</a></li>
        <li class="logout"><a href="/Feelbook/logout">Log out</a></li>

    </ul>

</div>

<div class="main container">

    <div id="posts" class="row">
`
    </div>

    <!-- Phân trang bằng các button -->
    <%--<div class="row">
        <nav aria-label="Page navigation example">
            <ul id="get-btn" class="pagination">

            </ul>
        </nav>
    </div>--%>

    <div>
        <button class="paging" style='display:none'>Load more</button>
    </div>
</div>

<script type="text/javascript" src="/Feelbook/server-resources/javascript/home.js">

</script>
</body>

</html>