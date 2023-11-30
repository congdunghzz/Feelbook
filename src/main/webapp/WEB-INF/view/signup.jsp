<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<title>Title</title>
</head>
<body>
<style>
.title{
           text-align: center;
       }
       .main{
           margin-top: 32px;
           height: 100vh;

       }
       .form{
           width: 35%;
           align-items: center;
       }
       img{
           margin: 16px;
           width: auto;
           height: 346px;
       }
       .btn-submit{
           margin: 32px;
           size: 16px;
       }
</style>
	<div class="main container">
		<h1 class="title">SIGN UP</h1>
		<hr>
		<form action="/Feelbook/sign-up" method="post">
			<div class="form row">
			<%--@declare id=""--%><label for="">Username</label>
			<input type="text" name="username">
			<br>
			<label for="">Password</label>
			<input type="text" name="password">
            <br>
			<label for="">Email</label>
			<input type="email" name="email">
            <br>
            <label for="">Name</label>
			<input type="text" name="name">
            <br>
			<label for="">Gender</label>
			<input name="gender" type="radio" value="true" />Nam
			<input name="gender" type="radio" value="false" />Nữ
            <br>
			<label for="">Day of birth</label>
			<input type="date" name="dob">
            <br>
		</div>
        <button class="btn-submit">SIGN UP</button>
		</form>
	</div>
</body>
</html>