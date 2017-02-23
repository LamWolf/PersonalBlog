<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div>
		<div class="home_title"><h3>${home.title }</h3></div>
		<div class="home_info">
			发布时间：[<fmt:formatDate value="${home.publishDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;作者：${home.author }
			&nbsp;&nbsp;文章类别：[${home.typeName }]&nbsp;&nbsp;阅读次数：${home.click }
		</div>
		<div class="home_content">
			${home.content }
		</div>
	</div>
	<div class="upDownPage">
		${pageCode }
	</div>
</div>

<div class="data_list comment_list"> 
	<div class="dataHeader">用户评论</div>
	<div class="commentDatas">
		<c:forEach var="comment" items="${commentList }">
			<div class="comment">
				<font>${comment.userIP }：</font>${comment.content }&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.commentDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;]
			</div>
		</c:forEach>
	</div>
</div>

<div class="publish_list">
	<form action="comment?action=save" method="post">
		<div>
			<input type="hidden" value="${home.homeId }" id="homeId" name="homeId"/>
			<textarea style="width: 98%" rows="3" id="content" name="content"></textarea>
		</div>
		<div class="publishButton">
			<button class="btn btn-primary" type="submit">发表评论</button>
		</div>
	</form>
</div>