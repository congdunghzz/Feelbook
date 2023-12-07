<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home page</title>
    <link rel="stylesheet" href="/Feelbook/server-resources/css/homepage.css">
    <link rel="stylesheet" href="/Feelbook/server-resources/css/profile.css">

    <script src="https://kit.fontawesome.com/09bba422f6.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
            integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
</head>
<body>
<div class="main">
    <nav>
        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="nav-left container-fluid">
                <p class="search-box mb-0 col-lg-9 col-12">
                    <img src="/Feelbook/server-resources/img/image/search.png">
                    <input type="text" name="key" placeholder="Search">
                    <button class="btn-search" onclick="Search()">Search</button>
                </p>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarResponsive">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link active" href="http://localhost:8080/Feelbook/profile">My Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="http://localhost:8080/Feelbook/logout">Log out</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

    </nav>
    <div id="commentModal" class="comment-modal">
        <div class="comment-modal-content">
            <div class="modal-container" id="comments">


            </div>
            <span class="close-modal" onclick="closeCommentModal()">&times;</span>
            <div class="wrapper-img">
                <div class="profile-img">
                    <img id="Avatar" src="${user.avatar}">
                </div>
                <div class="comment-input">

                    <input  id="commentText" name="content" rows="2" placeholder="Type your comment here"></input>
                </div>
                <div class="submit-btn">
                    <button onclick="submitComment(/*làm sao đ truyền vào post_id*/)" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container">

        <div class="main-content" id="posts">

        </div>

        <div class="right-sidebar">
            <h5 class="sugget">REQUESTS</h5>
            <div class="friend-request-container" id="request-list">


            </div>
        </div>
    </div>
</div>

<div class="modal-comment"></div>
    <script src="/Feelbook/server-resources/javascript/homepage.js"></script>
</body>
</html>