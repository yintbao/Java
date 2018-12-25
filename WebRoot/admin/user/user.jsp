<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

  <head>
	<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/demo/demo.css">
	<script type="text/javascript" src="../jquery-easyui-1.3.3/jquery.min.js"></script>
	<script type="text/javascript" src="../jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
 <script>
		var url;
		function deleteUser(){
			var row=$('#dg').datagrid('getSelected');
			if(row){
				$.messager.confirm("系统提示","您确定要删除这条记录吗?",function(r){
					if(r){
						$.post('user!deleteUser.action',{id:row.ID},function(result){
							if(result.success){
								$.messager.alert("系统提示","已成功删除这条记录!");
								$("#dg").datagrid("reload");
							}else{
								$.messager.alert("系统提示",result.errorMsg);
							}
						},'json');
					}
				});
			}
		}
	</script>
</head>
<body>
	<table id="dg" title="会员列表" class="easyui-datagrid" style="width:700px;height:365px"
            url="user!ShowUser.action"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true" fit="true">
        <thead>
            <tr>
            	<th field="ID" width="50" hidden="true">编号</th>
                <th field="USERNAME" width="50">会员名称</th>
                <th field="PASSWORD" width="50">会员密码</th>
                <th field="EMAIL" width="50">会员邮箱</th>
                <th field="STATE" width="50">会员是/否激活</th>
                <th field="LASTLOGINTIME" width="50">最后一次登录时间</th>
                <th field="IP" width="50">登陆的IP</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除会员</a>
    </div>
	

   
</body>
</html>