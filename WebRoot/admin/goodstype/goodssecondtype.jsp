<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
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
						$.post('category!deleteSecondType.action',{cid:row.CID},function(result){
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
		
		
		function editUser(){
			var row=$('#dg').datagrid('getSelected');
			if(row){
				$("#dlg").dialog('open').dialog('setTitle','编辑二级分类');
				$('#fm').form('load',row);
				url='';
			}
		}
		
		
		
		function newUser(){
			$("#dlg").dialog('open').dialog('setTitle','添加二级分类');
			$('#fm').form('clear');
			url='category!addSecondType.action';
		}
		function saveUser(){
			  $('#fm').form('submit',{
				url:url,
				onSubmit:function(){
				$.messager.alert("系统提示",$(this).form('validate'));
					return $(this).form('validate');
				},
				success:function(result){
					var result=eval('('+result+')');
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg);
						return;
					}else{
						$.messager.alert("系统提示","保存成功");
						$('#dlg').dialog('close');
						$("#dg").datagrid("reload");
					}
				}
			});
		}
		
	</script>
</head>
<body>
	<table id="dg" title="二级分类" class="easyui-datagrid" style="width:550px;height:350px"
            url="category!ShowSecondType.action"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true" fit="true">
        <thead>
            <tr>
                <th field="CNAME" width="50">二级分类名称</th>
                <th field="DESCS" width="50">二级分类描述</th>
            	<th field="CID" width="50" hidden="true">编号</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
 onclick="newUser()">添加二级分类</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑二级分类</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除二级分类</a>
    </div>
	
	<div id="dlg" class="easyui-dialog" style="width:550px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" enctype="multipart/form-data">
        	<table cellspacing="10px;">
        	     <tr>
        			<td>所属一级分类：</td>
        			<td><input class="easyui-combobox" name="cid"  data-options="panelHeight:'auto',editable:false,valueField:'CID',textField:'CNAME',url:'category!ShowType.action'"/></td>
        		</tr>
        		<tr>
        			<td>二级分类名称：</td>
        			<td><input name="cname" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<<tr>
        			<td>描述：</td>
        			<td><textarea name="desc" class="easyui-validatebox" required="true" cols="50" rows="5"> </textarea>
        		</tr>
        	 </table>
        </form>
	</div>
    
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
</body>
</html>