<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>

<html>
<head>
    <title>源码由阿莫之家提供</title>
    <base href="<%=basePath%>">
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <script type="text/javascript">
	    function resetValue(){
			document.getElementById("userName").value="";
			document.getElementById("password").value="";
		}
	    function checkForm(){
    		var userName=document.getElementById("userName").value;
    		var password=document.getElementById("password").value;
    		if(userName==null || userName==""){
    			alert("请输入用户名！");
    			return;
    		}else if(password==null || password==""){
    			alert("请输入密码！");
    			return;
    		}else{
    			document.getElementById("loginForm").submit();
    		}
    	}
        function loadImage(){
	        document.getElementById("randImage").src = "image.jsp?"+Math.random(); //Math.random()方法非常重要，它使每次的请求都不同以便重新发送。如果每次的请求都一样，那么不会重新生成页面
          } 
    </script>
</head>
<body bgcolor="#E7ECEF">
    <center>
    	<form id="loginForm" action="admin.action" method="post">
            <table border="0" cellspacing="0" cellpadding="0" style="margin-top:130">
                <tr><td><img src="<%=request.getContextPath() %>/image/logon_top.gif"></td></tr>                
                <tr height="180">
                    <td background="<%=request.getContextPath() %>/image/logon_middle.gif" align="center" valign="top">
                         <table border="0" width="90%" cellspacing="0" cellpadding="0">
                             <tr height="50"><td colspan="2" align="center"><font color="red">${error }</font></td></tr>
                             <tr height="30">
                                 <td align="right" width="40%">用户名：&nbsp;&nbsp;</td>
                                 <td style="text-indent:5"><input type="text" name="admin.username" id="userName" value="${admin.username }" size="30"/></td>
                             </tr>                
                             <tr height="30">
                                 <td align="right">密&nbsp;&nbsp;码：&nbsp;&nbsp;</td>
                                 <td style="text-indent:5"><input type="password" name="admin.password" id="password" value="${admin.password }" size="30"/></td>
                             </tr>
                               <tr height="30">
                                 <td align="right">验证码：&nbsp;&nbsp;</td>
                                 <td style="text-indent:5"><input type="text" name="image"  size="12"/><img onclick="javascript:loadImage();"  title="换一张试试" id="randImage" 
					src="image.jsp" width="60" height="20" border="1" align="absmiddle"></td>
                             </tr>
                             <tr height="60">
                                 <td></td>
                                 <td>
                                 	<input type="button" value="登录" onclick="checkForm()"/>
                                 	<input type="button" value="重置" onclick="resetValue()"/>
                                 	<a href="index.jsp">[返回首页]</a>
                                 </td>
                             </tr>
                      </table>
                    </td>
                </tr>
                <tr><td><img src="<%=request.getContextPath() %>/image/logon_end.gif"></td></tr>
                </table>
                </form>
    </center>
</body>
</html>