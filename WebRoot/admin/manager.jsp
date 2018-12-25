<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="UTF-8">
<title>阿莫之家信息管理系统主界面</title>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
	 function reset(){
		$.messager.confirm("系统提示","您确定要退出该系统吗?",function(r){
			if(r){
				 window.location.href="admin!logout.action";
			}
			});
	    }
	    
	   function updatePassword(){
			$("#dlg").dialog('open').dialog('setTitle','修改密码');
			url='admin!updatePassword.action';
		}
		function savePassword(){
			  $('#fm').form('submit',{
				url:url,
				onSubmit:function(){
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval('('+result+')');
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg);
						return;
					}else{
						$.messager.alert("系统提示","修改成功");
						$('#dlg').dialog('close');
					}
				}
			});
		}
		
	</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height:90px;">
	    <div align="left" style="width: 80%;float: left"><img src="../image/logo4.png"></div>
		<div style="padding-top: 70px;">管理员：&nbsp;<font color="red" size="36">${Curentadmin.username }</font></div>
    </div>
    
	<div region="center">
        <iframe name="info" frameborder='0' scrolling='auto' style='width:100%;height:100%' ></iframe>
    </div>
	
    <div region="west" style="width: 180px;" title="导航菜单" split="true">
      <div class="easyui-accordion" data-options="fit:true,border:false">
                <div title="商品类别管理" style="padding:15px;">
					<a href="goodstype/goodstype.jsp" class="easyui-linkbutton" iconCls="icon-remove"  target="info"><font color="red" >一级管理</font></a><br><br>
					<a href="goodstype/goodssecondtype.jsp" class="easyui-linkbutton" iconCls="icon-remove"  target="info" ><font color="red" >二级管理</font></a>
			    </div>
				
				 <div title="商品管理 "style="padding:15px;">
					<a href="goods/goods.jsp" class="easyui-linkbutton" iconCls="icon-remove"  target="info"><font color="red" >商品管理</font></a>
				</div>
				 <div title="订单管理 "style="padding:15px;">
					<a href="" target="info" class="easyui-linkbutton" iconCls="icon-remove" ><font color="red" >订单管理</font></a>
				</div>
				<div title="会员管理 "style="padding:15px;" >
					<a href="user/user.jsp" class="easyui-linkbutton" iconCls="icon-remove"  target="info" ><font color="red" >会员管理</font></a>
				</div>
				<div title="系统设置"style="padding:15px;">
					<a href="javascript:updatePassword()" class="easyui-linkbutton" iconCls="icon-remove" ><font color="red" >更改密码</font></a><br><br>
					 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="$('#w').window('open')"><font color="red" >关于系统</font></a><br><br>
					 <a href="javascript:reset()"  class="easyui-linkbutton" iconCls="icon-remove"><font color="red" >退出系统</font></a>
				</div>
               
					
			
	   </div>
	</div>
	          
	<div region="south" style="height: 25px;" align="center">版权所有<a href="http://www.0766city.com">源码由阿莫之家提供</a>
	
	
	<div id="dlg" class="easyui-dialog" style="width:300px;height:215px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" enctype="multipart/form-data">
        	<table cellspacing="10px;">
        	    
        		<tr>
        			<td>原密码：</td>
        			<td><input name="oldpassword" class="easyui-validatebox" type="password" required="true" style="width: 100px;"></td>
        		</tr>
        		<tr>
        			<td>新密码：</td>
        			<td><input name="newpassword" class="easyui-validatebox" type="password" required="true" style="width: 100px;"></td>
        		</tr>
        		
        		<tr>
        			<td>确认密码：</td>
        			<td><input name="repassword" class="easyui-validatebox" type="password" required="true" style="width: 100px;"> 
        		</tr>
        		
        	</table>
        </form>
	</div>
    </div>
    
    <div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePassword()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
	
	<div id="w" class="easyui-window" title="关于该系统" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
	<font color="red" size="+2">网上商城</font>又称在线商城系统，是一个功能完善的在线购物系统，主要为在线销售和在线购物服务。其功能主要包含商品的管理、会员的管理、订单的管理、库存的管理、优惠的管理、在线支付等
    </div>
</body>
</html>
