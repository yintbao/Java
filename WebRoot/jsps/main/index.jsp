<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
 String  Default=(String)request.getAttribute("default");
 if(Default==null){
   Default="default.jsp";
 }
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 

  </head>
  
  <body>
	<center>
        <table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="white">
            <tr><td colspan="2"><jsp:include page="top.jsp"/></td></tr>
            <tr>
                <td width="330" valign="top" align="center"></td>
                <td width="780px" height="700" align="center" valign="top" bgcolor="#FFFFFF"><jsp:include page="<%=Default%>"/>
                </td>
            </tr>
            <tr><td colspan="2" width="100%"><%@ include file="foot.jsp" %></td></tr>
        </table>        
    </center>
  </body>
</html>
