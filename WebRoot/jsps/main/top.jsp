<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/rj.js"></script>

<title>网上商城--首页</title>
</head>
<body>
<body>
	<%@include file="head.jsp"%>
	<%@include file="logo.jsp" %>
 <div style='width:100%;height:44px;text-align:center;margin:0 auto;border-bottom:#cccccc 0px solid;background:url(${pageContext.request.contextPath}/image/mnu1_bg.png) repeat-x;'>
    	<div style="width:1200px;height:44px;text-align:left;margin:0 auto;border-bottom:#cccccc 0px solid;">
			<div class="nav-wrap">
			 <div class="nav">
		    <div class="goods" style="background:url(${pageContext.request.contextPath}/image/mnu1_bg_2.png);">
                  <div>
                    <h2> <a>所有商品分类</a> </h2>
                  </div>
                  <% int i=1; %>
                  <div class="all-goods">
   <c:forEach items="${list}" var="parent">
                    <div class="item1">
                      <div class="product">
                        <h3> <a href="#">${parent.cname}</a> </h3>
                        <s></s> </div>
                      <%if(i==1){ %> 
                      <div class="product-wrap posone"> 
                      <% }else if(i==2){%> 
                        <div class="product-wrap postwo"> 
                      <%  }else if(i==3){%>
                       <div class="product-wrap posthree"> 
                         <%  }else if(i==4){%>
                          <div class="product-wrap posfour"> 
                          <%  }else if(i==5){%>
                           <div class="product-wrap posfive"> 
                             <%  }else if(i==6){%>
                             <div class="product-wrap possix"> 
                             <%  }else if(i==7){%>
                              <div class="product-wrap posseven"> 
                                <%  }else if(i==8){%>
                               <div class="product-wrap poseight"> 
                                 <%  }else if(i==9){%>
                                <div class="product-wrap posnine"> 
                                  <%  }else if(i==10){%>
                                    <div class="product-wrap posten">
                                    <%  }else if(i==11){%>
                                      <div class="product-wrap poseleven">
                           <%} %>
                             <%
                           i++;
                            %>
                        <!--宣传单-->
                        <div class="cf">
                          <div class="fl wd252 pr20">
                            <h2><a href="#">${parent.cname }</a></h2>
                            <p class="lh30">${parent.descs }</p>
                           
                  <c:forEach items="${parent.children}" var="child">
                             <ul class="cf">
                              <li> <a href="goods.action?cid=${child.cid }"  >${child.cname }</a> </li>
                             </ul>
                   </c:forEach> 
                          </div>
                        </div>
                          <dl class="fl wd185 pl20 blee">
                            <dd id="xcdads"><a href="#" target="_blank" title="宣传单"><img src="${pageContext.request.contextPath}/image/firstType/${parent.image}"></a></dd>
                          </dl>
                      </div>
                    </div>
                    
  </c:forEach>
            </div>
         </div>	
			
			
		
                <ul class="nav-list cf">
                  <li><a href="${pageContext.request.contextPath}/index.action">首页</a> </li>
                  <li><a href="${pageContext.request.contextPath}/jsps/user/info/userinfo.jsp" >用户信息</a></li>
                  <li><a href="${pageContext.request.contextPath}/jsps/user/info/updatepwd.jsp" >修改密码</a></li>
                  <li><a href="#">联系我们</a></li>
                  <li><a href="#">商城论坛</a></li>
                  <li><a href="#">组合套餐</a></li>
                  <li><a href="#">文章资讯</a></li>
                  <li><a href="#">快速选购</a></li>
                </ul>
              </div>
            </div>
        </div>
        </div>
</body>
</html>