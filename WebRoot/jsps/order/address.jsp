<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*,com.inspur.model.*"%>
<%@include file="../main/head.jsp"%>
<%@include file="../main/logo.jsp" %>
<%
	//获取session
	if(session.getAttribute("user") == null){
		//确定这个请求没有经过登陆
		response.sendRedirect("../user/login.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body {
	margin: 0;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
	line-height: 20px;
	/* color: #333333; */
	/* background-color: #f5f5f5; */
}

a {
	text-decoration: none;
	color: #f32343;
}

a:link {
	text-decoration: none; /* 未访问的链接 */
	color: #f32343;
}

a:visited {
	color: #f32343;
	text-decoration: none; /* 已访问的链接 */
}

a:hover {
	color: #f32343;
	text-decoration: none; /* 鼠标在链接上 */
	text-shadow:0 0 10px #00F;      /*没有偏移的模糊设置*/		//发光效果
}

a:active {
	color: #595959;
	text-decoration: none; /* 点击激活链接 */
	text-shadow:0 0 10px #00F;      /*没有偏移的模糊设置*/
}

table {
	max-width: 100%;
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
	
}

.table {
	width: 50%;
	margin-bottom: 20px;
}

.table th,.table td {
	padding: 8px;
	line-height: 20px;
	text-align: left;
	vertical-align: top;
	vertical-align: middle;
	border-top: 1px solid #dddddd;
	border-left: 1px solid #dddddd;
	border-right: 1px solid #dddddd;
	border-bottom: 1px solid #dddddd;
}

.table th {
	font-weight: bolder;
	font-size:16px;
	font-family:宋体;
	text-align: right;
}

.table thead th {
	vertical-align: bottom;
}

.table-striped tbody>tr:nth-child(odd)>td,.table-striped tbody>tr:nth-child(odd)>th
	{
	background-color: #f9f9f9;
}

hr {
	margin: 20px 0;
	border: 0;
	border-top: 1px solid #eeeeee;
	border-bottom: 1px solid #ffffff;
}

select,
textarea,
input[type="text"],
input[type="password"],
input[type="datetime"],
input[type="datetime-local"],
input[type="date"],
input[type="month"],
input[type="time"],
input[type="week"],
input[type="number"],
input[type="email"],
input[type="url"],
input[type="search"],
input[type="tel"],
input[type="color"],
.uneditable-input {
  display: inline-block;
  height: 30px;
  padding: 4px 6px;
  margin-bottom: 10px;
  font-size: 14px;
  line-height: 20px;
  color: #555555;
  vertical-align: middle;
  -webkit-border-radius: 4px;
     -moz-border-radius: 4px;
          border-radius: 4px;
}

label,
input,
button,
select,
textarea {
  font-size: 14px;
  font-weight: normal;
  line-height: 20px;
}

input,
button,
select,
textarea {
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

input {
	*overflow: visible;
	line-height: normal;
}

button,
input,
select,
textarea {
  margin: 0;
  font-size: 100%;
  vertical-align: middle;
}

select {
  width: 80px;
  background-color: #ffffff;
  border: 1px solid #cccccc;
}

input:focus:invalid,
textarea:focus:invalid,
select:focus:invalid {
  color: #b94a48;
  border-color: #ee5f5b;
}

input:focus:invalid:focus,
textarea:focus:invalid:focus,
select:focus:invalid:focus {
  border-color: #e9322d;
  -webkit-box-shadow: 0 0 6px #f8b9b7;
     -moz-box-shadow: 0 0 6px #f8b9b7;
          box-shadow: 0 0 6px #f8b9b7;
}

select:focus,
input[type="file"]:focus,
input[type="radio"]:focus,
input[type="checkbox"]:focus {
  outline: thin dotted #333;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}

label,
select,
button,
input[type="button"],
input[type="reset"],
input[type="submit"],
input[type="radio"],
input[type="checkbox"] {
  cursor: pointer;
}

input[type="text"],input[type="email"] {
	font-weight: normal;
	line-height: 20px;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	display: inline-block;
	height: 25px;
	padding: 4px 6px;
	/* margin-bottom: 10px; */
	font-size: 14px;
	line-height: 20px;
	color: #555555;
	vertical-align: middle;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	background-color: #ffffff;
	border: 1px solid #cccccc;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-webkit-transition: border linear 0.2s, box-shadow linear 0.2s;
	-moz-transition: border linear 0.2s, box-shadow linear 0.2s;
	-o-transition: border linear 0.2s, box-shadow linear 0.2s;
	transition: border linear 0.2s, box-shadow linear 0.2s;
}

input::-moz-focus-inner {
	padding: 0;
	border: 0;
}

select,
input[type="file"] {
  height: 30px;
  /* In IE7, the height of the select element cannot be changed by height, only font-size */

  *margin-top: 4px;
  /* For IE7, add top margin to align select with labels */

  line-height: 30px;
}

input[type="text"]:focus,input[type="email"]:focus {
	border-color: rgba(82, 168, 236, 0.8);
	outline: 0;
	outline: thin dotted \9;
	/* IE6-9 */
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(82, 168, 236, 0.6);
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(82, 168, 236, 0.6);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(82, 168, 236, 0.6);
}

input[disabled] {
	cursor: not-allowed;
	background-color: #eeeeee;
}

#labels {
	text-align: right;
}

.btn {
	display: inline-block;
	*display: inline;
	padding: 4px 12px;
	margin-bottom: 0;
	height:35px;
	*margin-left: .3em;
	font-size: 14px;
	line-height: 20px;
	color: #333333;
	text-align: center;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	vertical-align: middle;
	cursor: pointer;
	background-color: #f5f5f5;
	*background-color: #e6e6e6;
	background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff),
		to(#e6e6e6));
	background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
	background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
	background-repeat: repeat-x;
	border: 1px solid #cccccc;
	*border: 0;
	border-color: #e6e6e6 #e6e6e6 #bfbfbf;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	border-bottom-color: #b3b3b3;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',
		endColorstr='#ffe6e6e6', GradientType=0);
	filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
	*zoom: 1;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
}

.btn:hover,.btn:focus,.btn:active,.btn.active,.btn.disabled,.btn[disabled]
	{
	color: #333333;
	background-color: #e6e6e6;
	*background-color: #d9d9d9;
}

.btn:active,.btn.active {
	background-color: #cccccc \9;
}

.btn:first-child {
	*margin-left: 0;
}

.btn:hover,.btn:focus {
	color: #333333;
	text-decoration: none;
	background-position: 0 -15px;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
}

.btn:focus {
	outline: thin dotted #333;
	outline: 5px auto -webkit-focus-ring-color;
	outline-offset: -2px;
}

.btn.active,.btn:active {
	background-image: none;
	outline: 0;
	-webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px
		rgba(0, 0, 0, 0.05);
}

.btn.disabled,.btn[disabled] {
	cursor: default;
	background-image: none;
	opacity: 0.65;
	filter: alpha(opacity =           65);
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
}

.btn-large {
	padding: 11px 19px;
	font-size: 17.5px;
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	border-radius: 6px;
}

.btn-large [class^="icon-"],.btn-large [class*=" icon-"] {
	margin-top: 4px;
}

.btn-small {
	padding: 2px 10px;
	font-size: 11.9px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
}

.btn-small [class^="icon-"],.btn-small [class*=" icon-"] {
	margin-top: 0;
}

.btn-mini [class^="icon-"],.btn-mini [class*=" icon-"] {
	margin-top: -1px;
}

.btn-mini {
	padding: 0 6px;
	font-size: 10.5px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
}

.btn-block {
	display: block;
	width: 100%;
	padding-right: 0;
	padding-left: 0;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.btn-block+.btn-block {
	margin-top: 5px;
}

input[type="submit"].btn-block,input[type="reset"].btn-block,input[type="button"].btn-block
	{
	width: 100%;
}

.btn-primary.active,.btn-warning.active,.btn-danger.active,.btn-success.active,.btn-info.active,.btn-inverse.active
	{
	color: rgba(255, 255, 255, 0.75);
}

.btn-primary {
	color: #ffffff;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	background-color: #006dcc;
	*background-color: #0044cc;
	background-image: -moz-linear-gradient(top, #0088cc, #0044cc);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc),
		to(#0044cc));
	background-image: -webkit-linear-gradient(top, #0088cc, #0044cc);
	background-image: -o-linear-gradient(top, #0088cc, #0044cc);
	background-image: linear-gradient(to bottom, #0088cc, #0044cc);
	background-repeat: repeat-x;
	border-color: #0044cc #0044cc #002a80;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff0088cc',
		endColorstr='#ff0044cc', GradientType=0);
	filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
}

.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled]
	{
	color: #ffffff;
	background-color: #0044cc;
	*background-color: #003bb3;
}

.btn-primary:active,.btn-primary.active {
	background-color: #003399 \9;
}

button.btn,input[type="submit"].btn {
	*padding-top: 3px;
	*padding-bottom: 3px;
}

button.btn::-moz-focus-inner,input[type="submit"].btn::-moz-focus-inner
	{
	padding: 0;
	border: 0;
}

button.btn.btn-mini,input[type="submit"].btn.btn-mini {
	*padding-top: 1px;
	*padding-bottom: 1px;
}

.btn-primary .caret {
	border-top-color: #ffffff;
	border-bottom-color: #ffffff;
}

input:-moz-placeholder,textarea:-moz-placeholder {
	color: #999999;
}

input:-ms-input-placeholder,textarea:-ms-input-placeholder {
	color: #999999;
}

input::-webkit-input-placeholder,textarea::-webkit-input-placeholder {
	color: #999999;
}
.container {
  margin-right: auto;
  margin-left: auto;
  *zoom: 1;
}

.container:before,
.container:after {
  display: table;
  line-height: 0;
  content: "";
}

.container:after {
  clear: both;
}

.container-fluid {
  padding-right: 20px;
  padding-left: 20px;
  *zoom: 1;
}

.container-fluid:before,
.container-fluid:after {
  display: table;
  line-height: 0;
  content: "";
}

.container-fluid:after {
  clear: both;
}
</style>
<title>填写送货地址</title>
</head>
<body>
	<div
		style="margin: auto; text-align: left; width: 50%; font: 宋体; margin-bottom: 5px;">
		<span style="font-size:18px;font-weight:bolder;">下订单步骤：</span>
		<span style="font-size:18px;font-weight:bolder;">1.确认订单 >> </span>
		<span style="font-size:18px;font-weight:bolder;color: #f32343;"><a href="${pageContext.request.contextPath}/order/shipaddress.jsp" style="color:#f32343;">2.填写送货地址</a></span>
		<span style="font-size:18px;font-weight:bolder;"> >> 3.付款 >> 4.订单提交完成</span>
	</div>
	<form class="form-signin"
			action="${pageContext.request.contextPath}/ShipAddressServlet"
			method="post">
		<table class="table" align="center">
			<tbody>
				<tr>
					<th id="labels" width="120px" height="30px">收件人：</th>
					<td><input type="text" name="recipient" title="收件人" value="" placeholder="收件人" /></td>
				</tr>
				<tr>
					<th id="labels" width="120px" height="30px">送货区域：</th>
					<td>
						(运费：￥10元)</span>
					</td>
				</tr>
				<tr>
					<th id="labels" width="120px" height="30px">送货地址：</th>
					<td>
						<input type="text" name="address" title="送货地址" value="" placeholder="送货地址" style="width:410px;"/>
						<input type="checkbox" value="1" name="isDefault">是否默认
					
								 <input type="radio" value="" /><br>
					
					</td>
				</tr>
				<tr>
					<th id="labels" width="120px" height="30px">邮政编码：</th>
					<td><input type="text" name="zipcode" title="邮政编码" value="" placeholder="邮政编码" /></td>
				</tr>
				<tr>
					<th id="labels" width="120px" height="30px">联系电话：</th>
					<td><input type="text" name="telno" title="联系电话" value="" placeholder="联系电话" /></td>
				</tr>
			</tbody>
			
		<% 
	     	List<Map<String,Object>> carlist=(List<Map<String,Object>>)session.getAttribute("carlist");
		    float totalPrice=0;
		    for(Map<String,Object> map :carlist){
		    totalPrice+=((Goods)map.get("goods")).getDiscount()*Integer.parseInt(map.get("count").toString());
		    }
		  %>
			<tfoot>
				<tr>
					<td colspan="2" align="right" style="text-align:right;">
					<span style="color:#f32343;">￥<%=totalPrice %>元</span>(商品全额总计) +
					<span style="color:#f32343;">￥10元</span>(运费) = <span style="color:#f32343;">￥<%=totalPrice+10 %>元</span>(应支付)
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right" style="text-align:right;">
						<input type="hidden" name="memberid" value="" />
						<input type="button" class="btn btn-primary" style="height:32px;color:#fff;" onClick="window.history.go(-1)" value="上一步" />&nbsp;&nbsp;&nbsp;
						<a href="#" class="btn btn-primary" style="height:22px;color:#fff;">下一步</a>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>