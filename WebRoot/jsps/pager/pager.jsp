<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.bold {font-weight: 900;}
.divBody {
	background: url(/Shopping/image/paginating_bg.png) repeat-x;
	border-top: 2px solid #FE7C2C;
	height: 35px;
	width: 100%;
	text-align: right;
}
.divContent {
	line-height: 35px;
	margin-right: 15px;
}
.spanBtnSelect {
	border: 1px solid #F75A00; 
	color: #FFFFFF;
	background-color: #FE7C2C; 
	text-align:center; 
	padding: 4px 8px 3px 8px;
	text-decoration: none;
}
.aBtn {
	border: 1px solid #c1c1c1; 
	text-align:center; 
	background-color: #FFFFFF; 
	color: #555555;
	padding: 4px 8px 3px 8px;
	text-decoration: none;
}
.spanBtnDisabled {
	border: 1px solid #c1c1c1;
	text-align:center; 
	background-color: #FFFFFF; 
	color: #c1c1c1;
	padding: 4px 8px 3px 8px;
	font-weight: 900;
}
.spanApostrophe {
	font-family: Arial;
}
.aBtn:HOVER {
	border: 1px solid #F75A00;
	background-color: #FE7C2C; 
	color: #FFFFFF;
}

.inputPagecode {
	border: 1px solid #c1c1c1;
	width: 25px;
	padding: 0px;
}
.aSubmit {
	border: 1px solid #c1c1c1;
	padding:1px 4px 1px 4px;
	color: #555555;
	text-decoration: none;
}
.aSubmit:HOVER {
	border: 1px solid #c1c1c1;
	padding:1px 4px 1px 4px;
	color: #555555;
	background: url(/Shopping/image/qd.png) repeat-x;
}
</style>
<script type="text/javascript">
	function _go() {
		var pc = $("#pageCode").val();//获取文本框中的当前页码
		if(!/^[1-9]\d*$/.test(pc)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(pc > ${pb.tp}) {//判断当前页码是否大于最大页
			alert('请输入正确的页码！');
			return;
		}
		location = "${pb.url}&pc=" + pc;
	}
</script>


<div class="divBody">
  <div class="divContent">
    <%--上一页 --%>
    <c:choose>
      <c:when test="${pb.pc eq 1 }">
        <span class="spanBtnDisabled">上一页</span>
      </c:when>
      <c:otherwise>
        <a href="${pb.url }&pc=${pb.pc-1}" class="aBtn bold">上一页</a>
      </c:otherwise>
    </c:choose>
    
    <%-- 计算begin和end --%>
    <c:choose>
      <%-- 如果总页数<=6，那么显示所有页码，即begin=1 end=${pb.tp} --%>
      <c:when test="${pb.tp <= 6 }">
        <c:set var="begin" value="1"/>
        <c:set var="end" value="${pb.tp }"/>
      </c:when>
      <c:otherwise>
        <%-- 设置begin=当前页码-2，end=当前页码+3 --%>
        <c:set var="begin" value="${pb.pc-2 }"/>
        <c:set var="end" value="${pb.pc+3 }"/>
        <c:choose>
          <%-- 如果begin<1，那么让begin=1 end=6 --%>
          <c:when test="${begin<1}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="6"/>
          </c:when>
          <%-- 如果end>最大页，那么begin=最大页-5 end=最大页 --%>
          <c:when test="${end>pb.tp}">
            <c:set var="begin" value="${pb.tp-5 }"/>
            <c:set var="end" value="${pb.tp}"/>
          </c:when>
        </c:choose>
      </c:otherwise>
    </c:choose>
    
    <%-- 显示页码列表 --%>
    <c:forEach var="pc" begin="${begin }" end="${end }">
      <c:choose>
        <c:when test="${pb.pc eq pc }">
          <span class="spanBtnSelect">${pc }</span>
        </c:when>
        <c:otherwise>
          <a href="${pb.url}&pc=${pc}" class="aBtn">${pc }</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    
    <%-- 显示点点点 --%>
    <c:if test="${end < pb.tp }">
      <span class="spanApostrophe">...</span> 
    </c:if>
    
     <%--下一页 --%>
     <c:choose>
      <c:when test="${pb.pc eq pb.tp }">
        <span class="spanBtnDisabled">下一页</span>
      </c:when>
      <c:otherwise>
        <a href="${pb.url }&pc=${pb.pc+1}" class="aBtn bold">下一页</a>
      </c:otherwise>
    </c:choose>   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    
    <%-- 共N页 到M页 --%>
    <span>共${pb.tp }页</span>
    <span>到</span>
    <input type="text" class="inputPageCode" id="pageCode" value="${pb.pc }"/>
    <span>页</span>
    <a href="javascript:_go();" class="aSubmit">确定</a>
  </div>
</div>