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
						$.post('goods!deleteType.action',{id:row.ID},function(result){
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
				$("#dlg").dialog('open').dialog('setTitle','编辑商品');
				$('#fm').form('load',row);
				url='category!modifeFistType.action';
			}
		}
		
		
		
		function newUser(){
			$("#dlg").dialog('open').dialog('setTitle','添加商品');
			$('#fm').form('clear');
			url='goods!addGoods.action';
		}
		function saveUser(){
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
						$.messager.alert("系统提示","保存成功");
						$('#dlg').dialog('close');
						$("#dg").datagrid("reload");
					}
				}
			});
		}
		function searchGoods(){
	    $('#dg').datagrid('load',{
    	NAME:$('#NAME').val()
  	
		});
         }
		
			
		//导出打开上传excel的窗口
		function openUploadFileDialog(){
		 $("#dlg2").dialog('open').dialog('setTitle','批量导入数据');
		}
		//导出excel
		function downLoad(){
		window.open('goods!uploadExcel.action');
		}
				function openUploadFileDialog(){
			$("#dlg2").dialog('open').dialog('setTitle','批量导入数据');
		}
		//下载excel模板
		function downloadTemplate(){
			window.open('/Shopping/template/goodsExporTemplate .xls');
		}
		//批量导入
		function uploadFile(){
			$("#uploadForm").form("submit",{
				success:function(result){
					var result=eval('('+result+')');
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg);
					}else{
						$.messager.alert("系统提示","上传成功");
						$("#dlg2").dialog("close");
						$("#dg").datagrid("reload");
					}
				}
			});
		}
	</script>
</head>
<body>
	<table id="dg" title="商品列表" class="easyui-datagrid" style="width:700px;height:365px"
            url="goods!ShowGoods.action"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true" fit="true">
        <thead>
            <tr>
            	<th field="ID" width="50" hidden="true">商品编号</th>
            	<th field="TYPEID" width="50" hidden="true">种类编号</th>
                <th field="CNAME" width="50">商品所属类别</th>
                <th field="NAME" width="50">商品名称</th>
                <th field="GOODSNO" width="50">商品号</th>
                <th field="IMG" width="50">商品图片</th>
                <th field="PRICE" width="50">市场价</th>
                <th field="DISCOUNT" width="50">优惠价</th>
                <th field="QTY" width="50">数量</th>
                <th field="DESCRIBE" width="50">商品描述</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
 onclick="newUser()">添加商品</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑商品</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除商品</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-member" plain="true" onclick="downLoad()">导出excel</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="openUploadFileDialog()">批量导入数据</a>
        
        
        <div>
			<form id="export" method="post">
			  &nbsp;商品名称：&nbsp;<input  id="NAME" name="NAME" size="10" "/>   
	
			  <a href="javascript:searchGoods()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</form>
		</div>
    </div>
	
	<div id="dlg" class="easyui-dialog" style="width:550px;height:400px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" enctype="multipart/form-data">
        	<table cellspacing="10px;">
        	    
        		<tr>
        			<td>商品类别：</td>
        			<td><select    class="easyui-combobox" name="goods.typeid" style="width:200px"  data-options="panelHeight:'auto',editable:false,valueField:'CID',textField:'CNAME',url:'goods!ShowgoodsType.action'"></select></</td>
        		</tr>
        		<tr>
        			<td>商品名称：</td>
        			<td><input name="goods.name" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>商品号：</td>
        			<td><input name="goods.goodsno" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>商品图片：</td>
        			<td><input  type="file" name="img"  style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>市场价：</td>
        			<td><input name="goods.price" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>优惠价：</td>
        			<td><input name="goods.discount" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>数量：</td>
        			<td><input name="goods.qty" class="easyui-validatebox" required="true" style="width: 200px;"></td>
        		</tr>
        		<tr>
        			<td>商品描述：</td>
        			<td><textarea  cols="50" rows="5"  name="goods.descs" class="easyui-validatebox"  required="true"> </textarea>
        		</tr>
        		
        	</table>
        </form>
	</div>
    
    	<div id="dlg2" class="easyui-dialog" style="width:400px;height:180px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons2">
        <form id="uploadForm" action="goods!uploadGoods.action" method="post" enctype="multipart/form-data">
        	<table>
        		<tr>
        			<td>下载模版：</td>
        			<td><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="downloadTemplate()">下载</a></td>
        		</tr>
        		<tr>
        			<td>上传文件：</td>
        			<td><input type="file" name="goodsUploadFile"></td>
        		</tr>
        	</table>
        </form>
	</div>
    
    <div id="dlg-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadFile()">上传</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">关闭</a>
	</div>
    
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
</body>
</html>