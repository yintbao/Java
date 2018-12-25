<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--logo-->
<div
	style="margin: 0 auto; width: 100%; height: 110px; text-align: center;">
	<div
		style="width: 1200px; height: 110px; text-align: center; margin: 0 auto;">
		<div
			style="height: 68px; width: 260px; float: left; margin-top: 10px;">
			<a href="index.action"><img src="${pageContext.request.contextPath}/image/logo.png" border="0" width="234" height="68"></a>
		</div>
		<div
			style="float: left; height: 65px; line-height: 65px; width: 100px; text-align: center; margin-top: 15px;">

		</div>
		<div
			style="height: 70px; width: 585px; text-align: center; float: left; padding-top: 20px;">
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				function search()
				{
				   if(checkspace(document.searchform.searchkey.value))  {
					document.searchform.searchkey.focus();
					alert("请输入查询关键字！");
					return false;
				  }
				   document.searchform.submit();
				}
				//-->
				</script>
			<div
				style="margin: 0px auto; width: 452px; height: 36px; text-align: right;">
				<form name="searchform" method="post" action="">

					<div
						style="float: left; width: 325px; *width: 325px; _width: 320px; height: 36px; line-height: 36px; PADDING-top: 0px; *PADDING-top: 1px; _PADDING-top: 0px;">

						<input
							style='width: 357px; _width: 350px; height: 32px; line-height: 32px; border-right: #ff1812 2px solid; border-left: #ff1812 2px solid; border-top: #ff1812 2px solid; border-bottom: #ff1812 2px solid; color: #999999; font-size: 11pt; background: url(${pageContext.request.contextPath}/image/bh.png) -473px -102px no-repeat; background-color: #f9f9f9; PADDING-left: 25px;'
							name="searchkey" type="text" maxlength="30" value="输入你要查询商品关键词"
							onFocus="this.value='' ">
					</div>
					<div
						style='float: left; width: 100px; height: 36px; line-height: 36px; PADDING-top: 0px; *PADDING-top: 1px; _PADDING-top: 1px;'>

						<input
							style='width: 100px; height: 37px; line-height: 37px; border-right: #e1e1e1 0px solid; border-left: #e1e1e1 0px solid; border-top: #e1e1e1 0px solid; border-bottom: #e1e1e1 0px solid;'
							name="Submit2" type="image" src="${pageContext.request.contextPath}/image/bg_sea.png" onClick="">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>