<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//获取session
	if(session.getAttribute("user") == null){
		//确定这个请求没有经过登陆
		response.sendRedirect("../login.jsp");
	}
%>
<%@ include file="../../main/head.jsp"%>
<%@ include file="../../main/logo.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.5.1.js"></script>
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,p,blockquote,th,td
	{
	margin: 0;
	padding: 0;
}
.form-signin {
	max-width: 500px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 15px;
}

.form-signin input[type="text"],.form-signin input[type="password"] {
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}

</style>
<title>查看个人信息</title>
</head>
<body>
     <form action="" class="form-signin" >
      <h2 class="form-signin-heading">会员信息</h2>
	  <table >
		<tr>
			<th >用户名</th>
			<td><input type="text" value="${user.username}" readonly></td>
		</tr>
		<tr>
			<th >邮箱</th>
			<td><input type="text" value="${user.email}" readonly></td>
		</tr>
		
		<tr>
			<th >登录时间</th>
			<td><input type="text"  value="${user.lastlogintime}" readonly></td>
		</tr>
		<tr>
			<th>登录IP</th>
			<td><input type="text"  value="${user.ip}" readonly></td>
		</tr>
		 <tr>
                 <td></td>
                 <td><button class="btn btn-primary" type="submit">修改</button>
                      <input type="reset" class="btn btn-primary" value="清 除">
                 </td>
        </tr>
	</table>
	</form>
</body>
</html>