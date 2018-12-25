<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../main/head.jsp"%>
<%@include file="../main/logo.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	 /**
                     生成验证码
     */
     
     function loadImage(){
	  document.getElementById("randImage").src = "<%=request.getContextPath()%>/image.jsp?"+Math.random(); //Math.random()方法非常重要，它使每次的请求都不同以便重新发送。如果每次的请求都一样，那么不会重新生成页面
     }
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsps/js/user/register.js"></script>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet">
<title>会员注册</title>
<style type="text/css">
body {
	margin:0;
	padding:0;
	background-color: #f5f5f5;
}
.form-signin {
	max-width: 550px;
	padding: 19px 29px 29px;
	margin:0 auto;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}
table {
	/* max-width: 100%; */
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
}

.table {
	width: 40%;
	margin-bottom: 20px;
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 10px;
}
.form-signin input[type="text"],.form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
.labelError{line-height: 9px; font-size: 10pt; color: #f40000; border: 1px #ffb8b8 solid; padding: 8px 8px 8px 35px; background: url(/Shopping/image/error.png) no-repeat; background-color: #fef2f2;}
</style>

</head>
<body>

       <div class="reg">
            <form action="user!register.action"   method="post" class="form-signin" onsubmit="return check();">
 			<h2 class="form-signin-heading" style="font:楷体;">会员注册</h2>
 			<hr>
 			<table>
            	<tr>
            	<td width="100px">用户名：</td>
            	<td><input type="text" name="user.username"  id="username" class="input"></td>
            	<td><label class="labelError" id="usernameError"></label></td>
            	</tr>
            	
               <tr>
               <td width="100px">登录密码</td>
               <td><input type="password" name="user.password" id="password" class="input" ></td>
               <td ><label class="labelError" id="passwordError"></label></td>
               </tr>
                
                <tr>
                <td width="100px">确认密码：</td>
                <td><input type="password" name="user.repassword"  id="repassword" class="input" ></td>
                <td ><label class="labelError" id="repasswordError"></label></td>
                </tr>
                
                <tr>
                <td width="100px">邮箱</td>
                <td><input type="text" name="user.email" id="email" class="input" ></td>
                <td > <label class="labelError" id="emailError"></label></td>
                </tr>
                
                <tr>
         		<td width="100px">验证码:</td>
         		<td><input type="text" name="user.imageValue"  id="imageValue" class="input" size="9"></td>
			    <td><label class="labelError" id="imageValueError"></label></td>
                </tr>
               
                <tr>
                 <td></td>
                 <td><img onClick="javascript:loadImage();"  title="换一张试试" id="randImage" 
				  src="<%=request.getContextPath()%>/image.jsp" width="60" height="20" border="1" ></td>
                 <td></td>
                </tr>
               
                 <tr>
                 <td></td>
                 <td><button class="btn btn-primary" type="submit">注册</button></td>
                 <td></td>
                </tr>
            </table>
            <span class="clew_txt">如果您已有帐号，可<a href="${pageContext.request.contextPath}/jsps/user/login.jsp">直接登录</a></span>
         <font color="red">${error }</font>
        </form>
   
	
                     
    </div>
</body>
</html>