<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}

/* flexslider */
.flexslider{position:relative;height:400px;overflow:hidden;background:url(${pageContext.request.contextPath}/image/loading.gif) 50% no-repeat;}
.slides{position:relative;z-index:1;}
.slides li{height:400px;}
.flex-control-nav{position:absolute;bottom:10px;z-index:2;width:100%;text-align:center;}
.flex-control-nav li{display:inline-block;width:14px;height:14px;margin:0 5px;*display:inline;zoom:1;}
.flex-control-nav a{display:inline-block;width:14px;height:14px;line-height:40px;overflow:hidden;background:url(${pageContext.request.contextPath}/image/dot.png) right 0 no-repeat;cursor:pointer;}
.flex-control-nav .flex-active{background-position:0 0;}

.flex-direction-nav{position:absolute;z-index:3;width:100%;top:45%;}
.flex-direction-nav li a{display:block;width:50px;height:50px;overflow:hidden;cursor:pointer;position:absolute;}
.flex-direction-nav li a.flex-prev{left:40px;background:url(${pageContext.request.contextPath}/image/prev.png) center center no-repeat;}
.flex-direction-nav li a.flex-next{right:40px;background:url(${pageContext.request.contextPath}/image/next.png) center center no-repeat;}
</style>
</head>

<body>



<div class="flexslider">
	<ul class="slides">
		<li style="background:url(${pageContext.request.contextPath}/image/img1.jpg) 50% 0 no-repeat;"></li>
		<li style="background:url(${pageContext.request.contextPath}/image/img2.jpg) 50% 0 no-repeat;"></li>
		<li style="background:url(${pageContext.request.contextPath}/image/img3.jpg) 50% 0 no-repeat;"></li>
		<li style="background:url(${pageContext.request.contextPath}/image/img4.jpg) 50% 0 no-repeat;"></li>
		<li style="background:url(${pageContext.request.contextPath}/image/img5.jpg) 50% 0 no-repeat;"></li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.flexslider-min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('.flexslider').flexslider({
		directionNav: true,
		pauseOnAction: false
	});
});
</script>


</body>
</html>
