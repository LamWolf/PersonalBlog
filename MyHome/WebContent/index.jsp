<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>匆匆过客主页</title>
<link href="${pageContext.request.contextPath}/style/myhome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
<jsp:include page="/foreground/common/head.jsp"/>

<div class="row-fluid">
	<div class="span9">
		<div>
			<DIV style="width: 330px; height: 228px;" class="tuhuo">
				<A href="" target="_blank"><IMG style="width: 330px; height: 208px;" id="fou_img" src=""></A>
					<!--图片轮转（图片）  -->
					<c:forEach var="imageHome" items="${imageHomeList}">
						<A href="home?action=show&homeId=${imageHome.homeId }"> 
							<IMG style="display: none;" class="tu_img" src="${imageHome.imageName }" width="330" height="208" />
						</A>
					</c:forEach>
					<!--图片乱转（标题）  -->
					<c:forEach var="imageHome" items="${imageHomeList}">
						<P style="height: 20px;" class="tc">
							<A href="home?action=show&homeId=${imageHome.homeId }" target="_blank" title="${imageHome.title }">${fn:substring(imageHome.title,0,18) }</A>
						</P>
					</c:forEach>
					<!--图片轮转（数字）  -->
					<UL>
					  <LI class="fouce">1</LI>
					  <LI>2</LI>
					  <LI>3</LI>
					  <LI>4</LI>
					  <LI>5</LI>
					 </UL>
			</DIV>
		</div>
		
		<!--最新（置顶）  -->
		<div class="homeHeader_list">
			<div class="homeHeader">
				<h3><a href="home?action=show&homeId=${headHome.homeId }" title="${headHome.title }">${fn:substring(headHome.title,0,10) }</a></h3>
				<p>${fn:substring(headHome.content,0,40) }...<a href="home?action=show&homeId=${headHome.homeId }" >[查看全文]</a></p>
			</div>
			
			<!--最近更新板块  -->
			<div class="currentUpdate">
				<div class="currentUpdateHeader">最近更新</div>
				<div class="currentUpdateDatas">
				<table width="100%">
					<c:forEach var="hometHome" items="${hometHomeList }" varStatus="status">
						<c:if test="${status.index%2==0 }">
							<tr>
						</c:if>
						<td width="50%">
							<a href="home?action=show&homeId=${hometHome.homeId }" title="${hometHome.title }">${fn:substring(hometHome.title,0,12) }</a>
						</td>
						<c:if test="${status.index%2==1 }">
							</tr>
						</c:if>
					</c:forEach>
				</table>
				</div>
			</div>
		</div>
	</div>
	
	<!--热门文章板块  -->
	<div class="span3">
		<div class="data_list hotspot_home_list">
			<div class="dataHeader">热门文章</div>
			<div class="datas">
				<ul>
					<c:forEach var="hotSpotHome" items="${hotSpotHomeList }">
						<li><a href="home?action=show&homeId=${hotSpotHome.homeId }" title="${hotSpotHome.title }">${fn:substring(hotSpotHome.title,0,15) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>

<!--主要内容板块  -->
<c:forEach var="allIndexHome" items="${allIndexHomeList }" varStatus="allStatus">
	<c:if test="${allStatus.index%3==0 }">
		<div class="row-fluid">
	</c:if>
	<c:forEach var="indexHome" items="${allIndexHome }" varStatus="oneStatus">
		<c:if test="${oneStatus.first }">
			<div class="span4">
			<div class="data_list home_list" >
				<div class="dataHeader">${homeTypeList.get(allStatus.index).typeName }<span class="more"><a href="home?action=list&typeId=${homeTypeList.get(allStatus.index).homeTypeId }">更多...</a></span></div>
				<div class="datas">
					<ul>
		</c:if>
		<li><fmt:formatDate value="${indexHome.publishDate }" type="date" pattern="MM-dd"/>&nbsp;<a href="home?action=show&homeId=${indexHome.homeId }" title="${indexHome.title }">${fn:substring(indexHome.title,0,18) }</a></li>
		<c:if test="${oneStatus.last }">
					</ul>
						</div>
					</div>
				</div>
		</c:if>
	</c:forEach>
	<c:if test="${allStatus.index%3==2 || allStatus.last }">
		</div>
	</c:if>
</c:forEach>

<jsp:include page="/foreground/common/link.jsp"/>
<jsp:include page="/foreground/common/foot.jsp"/>
</div>
</body>
<script type="text/javascript">
	var auto;
	var index=0;
	$('.tuhuo ul li').hover(function(){
		clearTimeout(auto);
		index=$(this).index();
		move(index);
		},function(){
			auto=setTimeout('autogo('+index+')',3000);
	});
	
	function autogo(){
		if(index<5){
			move(index);
			index++;
		}
		else{
			index=0;	
			move(index);
			index++;
		}
	}
	function move(l){
		var src=$('.tu_img').eq(index).attr('src');
		$("#fou_img").css({  "opacity": "0"  });
		$('#fou_img').attr('src',src);
		$('#fou_img').stop(true).animate({ opacity: '1'},1000);
		$('.tuhuo ul li').removeClass('fouce');
		$('.tuhuo ul li').eq(index).addClass('fouce');
		$('.tuhuo p').hide();
		$('.tuhuo p').eq(index).show();
		var ao=$('.tuhuo p').eq(index).children('a').attr('href');
		$('#fou_img').parent('a').attr("href",ao);
	}
	autogo();
	setInterval('autogo()',3000);
</script>
</html>