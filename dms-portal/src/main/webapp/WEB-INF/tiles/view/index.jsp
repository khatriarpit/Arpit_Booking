<%@page import="com.emxcel.dms.portal.constants.UrlConstants"%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="page-content">
	<div class="row">
		<div class="col-lg-6">
			<h1 class="grey_te">${welcome}</h1>
		</div>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<c:forEach items="${chatData}" var="tsm" varStatus="loop">
				<tr>
					<td><input type="hidden" value="${tsm.fromUser}"
						class="chatcss" /></td>
				</tr>
			</c:forEach>
	
		</c:if>

	</div>

<!--END CONTENT-->
<script type="text/javascript">
window.location.href = "<%=UrlConstants.DASHBOARD%>";
</script>
</div>