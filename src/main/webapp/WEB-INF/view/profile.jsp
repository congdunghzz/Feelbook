<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="app.model.DTO.UserDto" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="/Feelbook/server-resources/css/profile.css">
        <link rel="stylesheet" href="/Feelbook/server-resources/css/homepage.css">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Profile</title>
    </head>
    <body>
        <!--NAV-->
        <div class="profile-container">
            <div class="navigation">
                <div class="search-bar">
                    <input type="text" placeholder="Search" id="searchInput">
                    <button type="button" onclick="handleSearch()">Search</button>
                </div>
                <div class="logout">
                    <form action="/Feelbook/logout" method="get">
                        <button class="logout-btn">Logout</button>

                    </form>
                </div>
            </div>
       </div> 
       <br>
       <!--Profile-->
       <div class="profile-section">
            <div class="row">
                <!--Profile left-->
                <div class="col-md-3">
                    <div class="profile-left">
                        <div class="user-info">
                            <img src="${you.avatar}" class="profile-picture">${you.name}
                        </div>
                        <div class="select">
                            <a href="/Feelbook/home"><img src="/Feelbook/server-resources/img/image/home.svg" onclick="">Home</a>
                            <a href="#"><img src="/Feelbook/server-resources/img/image/notification.svg" onclick="">Notifications</a>

                            <a href="/Feelbook/profile"><img src="/Feelbook/server-resources/img/image/user.svg" onclick="">Profile</a>
                        </div>
                    </div>
                    <div class="box-design friend-site">
                        <button id="autho" class="non-display" value="${autho}"></button>
                        <input id="user-id" class="non-display" type="text" value="${user.user_id}" readonly>
                        <span>Friends</span>
                        
                        <div class="all-images">
                            <a href="#">See all friends</a>
                        </div>
                        <div class="images" id="list-friend">


                        </div>
                    </div>
                </div>
                <!--Profile right-->
                <div class="col-md-9">
                    <div class="wrapper">
                        <div class="cover">
                            <img src="/Feelbook/server-resources/img/image/cover.jpg">
                        </div>
                    </div>
                    <div class="id-section">
                        <div class="square">
                            <img src="${user.avatar}" class="avatar" style="height: 100px; width: 100px">4
                            <c:if test="${autho eq true}">
                                <div class="photo-upload">
                                    <div class="post-video">

                                        <form action="/Feelbook/api/user/setAvatar" method="post"
                                              enctype="multipart/form-data">
                                            <label for="imgFile">
                                                <img id="video" src="/Feelbook/server-resources/img/image/photo.svg">
                                                Upload your avatar
                                            </label>

                                            <input type="file" id="imgFile" style="display: none;"
                                                   accept="image/png, image/jpeg, image/jpg" name="imgFile">
                                            <input type="submit" value="submit" class="">
                                        </form>
                                    </div>
                                </div>
                            </c:if>

                            <div class="id">
                                <!--icon edit, share-->
                                <c:if test="${autho eq true}">
                                    <div class="icon">
                                        <img src="/Feelbook/server-resources/img/image/edit.svg"
                                             onclick="handleEditClick()">
                                    </div>
                                </c:if>
                                 <!-- Modal for editing profile -->

                                <!--Detail-->
                                <h5>${user.name}</h5>
                                <h6>Email: ${user.user_email}</h6>


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

                                <p></p>
                            </div>
                            <c:if test="${friendStatus != -2}">
                                <div id="friend-status" class="requests-btns">
                                    <c:if test="${friendStatus == 0}">
                                    <button id="cancel" class="btn1" onclick="Cancel(${user.user_id})">Cancel</button>
                                    </c:if>
                                    <c:if test="${friendStatus == 1}">
                                        <button id="unfriend" class="send-request" onclick="UnFriend(${user.user_id})">Unfriend</button>
                                    </c:if>
                                    <c:if test="${friendStatus == -1}">
                                        <button id="send" class="send-request" onclick="AddFriend(${user.user_id})">Add friend</button>
                                    </c:if>
                                    <c:if test="${friendStatus == 2}">
                                        <button id="accept" class="btn1" onclick="Accept(${user.user_id})">Accept</button>
                                        <button id="reject" class="btn2" onclick="Reject(${user.user_id})">Reject</button>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                        </div>

                    <!--NEW POST-->
                    <c:if test="${autho eq true}">
                        <div class="box-design-post">
                            <form action="/Feelbook/post" method="post" enctype="multipart/form-data">
                                <div class="post-upload">
                                    <div class="profile-img">
                                        <a href="" id="profile-link">
                                            <img id="Avatar" src="${user.avatar}">
                                        </a>
                                    </div>
                                    <div class="text-post">
                                        <input type="text" placeholder="What's on your mind?" id="post-sth" name="content">
                                    </div>
                                    <div class="photo-upload">
                                        <div class="post-video">
                                            <p>
                                                <label for="fileInput">
                                                    <img id="video"
                                                         src="/Feelbook/server-resources/img/image/photo.svg">
                                                    Photo/Video
                                                </label>
                                                <input type="file" id="fileInput" name="post-img" style="display: none;"
                                                       accept="image/*, video/*" onchange="handleFileSelect(event)">
                                            </p>
                                        </div>
                                    </div>
                                    <div class="submit-photo">
                                        <button class="btn btn-primary" value="submit" onclick="submitPost()">Post
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </c:if>
                    <!--POSTED-->
                    <div class="posted" id="posts">

                    </div>
                          <%--  edit info in here--%>
                    <div id="editInfoModal" class="edit-info-modal comment-modal">
                        <div class="comment-modal-content">
                            <span class="close-modal" onclick="closeEditInfoModal()">&times;</span>
                            <h3 align="center">Edit your information</h3>
                            <hr>
                            <form action="/Feelbook/api/user/edit" method="post">
                                <div class="comment-input">
                                <input type="text" name="name" value="${you.name}">
                                <br>
                                <input type="email" name="email" value="${you.user_email}">
                                <br>
                                <input type="date" name="dob" value="${you.dob}">
                                <br>
                                </div>
                                <br>
                                <label for="">Gender</label>

                                <input name="gender" type="radio" value="true" />Nam

                                <input name="gender" type="radio" value="false" />Nữ
                                <br>
                                <button class="send-request" type="submit">Confirm</button>
                            </form>
                        </div>
                    </div>

                            <%--editPost modal--%>
                    <div id="editPostModal" class="comment-modal">
                        <div class="comment-modal-content">
                            <div class="posted" id="postEditing">

                            </div>
                            <span class="close-modal" onclick="closeEditPostModal()">&times;</span>
                        </div>
                    </div>

                        <%--commment modal--%>
                    <div id="commentModal" class="comment-modal">
                        <div class="comment-modal-content">
                            <div class="modal-container" id="comments">
                                <!-- Container for comments -->
                            </div>
                            <span class="close-modal" onclick="closeCommentModal()">&times;</span>
                            <div class="wrapper-img">
                                <div class="profile-img">
                                    <img id="Avatar" src="${you.avatar}">
                                </div>
                                <div class="comment-input">

                                    <input  id="commentText" rows="2" placeholder="Type your comment here"></input>
                                </div>
                                <div class="submit-btn">
                                    <button onclick="submitComment()" class="btn btn-primary">Submit</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
       </div>
        <script type="text/javascript" src="/Feelbook/server-resources/javascript/profile2.js"></script>
      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>