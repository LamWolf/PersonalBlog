<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="datas home_type_list">
		<ul>
			<c:forEach var="hometHome" items="${hometHomeListWithType }">
				<li>
					<span>[<fmt:formatDate value="${hometHome.publishDate }" type="date" pattern="yyyy-MM-dd"/>]</span>&nbsp;&nbsp;
					<a href="home?action=show&homeId=${hometHome.homeId }" title="${hometHome.title }">${fn:substring(hometHome.title,0,42) }</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div>
		${pageCode }
	</div>
</div>