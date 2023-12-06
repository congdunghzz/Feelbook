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
                            <img src="${user.avatar}" class="profile-picture">${user.name}
                        </div>
                        <div class="select">
                            <a href="/Feelbook/home"><img src="/Feelbook/server-resources/img/image/home.svg" onclick="">Home</a>
                            <a href="#"><img src="/Feelbook/server-resources/img/image/notification.svg" onclick="">Notifications</a>

                            <a href="#"><img src="/Feelbook/server-resources/img/image/user.svg" onclick="">Profile</a>
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


                            <div class="id">
                                <!--icon edit, share-->
                                <div class="icon">
                                    <img src="/Feelbook/server-resources/img/image/edit.svg" onclick="handleEditClick()">
                                </div>
                                 <!-- Modal for editing profile -->
                                <div id="editModal" class="modal">
                                    <div class="modal-content">
                                        <span class="close" onclick="closeEditModal()">&times;</span>
                                <!-- Edit profile form -->
                                         <h2>Edit Profile</h2>
                                        <form id="editForm">
                                            <label for="username">Username:</label>
                                            <input type="text" id="username" name="username">

                                            <label for="email">Email:</label>
                                            <input type="email" id="email" name="email">

                                            <label for="work">Work:</label>
                                            <input type="text" id="work" name="work">

                                <!-- Add more form fields as needed -->

                                            <button type="button" onclick="submitEditForm()">Save Changes</button>
                                        </form>
                                    </div>
                                </div>
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
                        </div>
                    </div>
                    <!--NEW POST-->
                    <div class="box-design-post">
                        <form action="">
                            <div class="post-upload">
                                <div class="profile-img">
                                    <a href="" id="profile-link">
                                        <img id="Avatar" src="${user.avatar}">
                                    </a>
                                </div>
                                <div class="text-post">
                                    <input type="text" placeholder="What's on your mind?" id="post-sth">
                                </div>
                                <div class="photo-upload">
                                    <div class="post-video">
                                        <p>
                                            <label for="fileInput">
                                                <img id="video" src="/Feelbook/server-resources/img/image/photo.svg">
                                                Photo/Video
                                            </label>
                                            <input type="file" id="fileInput" style="display: none;" accept="image/*, video/*" onchange="handleFileSelect(event)">
                                        </p>
                                    </div>
                                </div>
                                <div class="submit-photo">
                                    <button class="btn btn-primary" value="submit" onclick="submitPost()">Post</button>
                                </div>
                            </div>
                        </form>
                        
                    </div>
                    <!--POSTED-->
                    <div class="posted">
                        <div class="post-information">
                            <div class="profile-img">
                                <a href="#" id="profile-link">
                                    <!-- Avatar người dùng -->
                                    <img id="Avatar" src="/Feelbook/server-resources/img/image/avatar.jpg">
                                </a>
                            </div>
                            <div class="person-name">
                                <h2>LE CONG DUNG</h2>
                                <p>
                                    <a href="%">35hours</a>
                                    <span><img src="/Feelbook/server-resources/img/image/user-group.svg"></span>
                                </p>
                                <div class="dot"><img src="/Feelbook/server-resources/img/image/dot.svg"></div>
                            </div>
                            <p class="post-header-text">Xin chao Viet Nam</p>
                            <img src="" class="post-images">
                            
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