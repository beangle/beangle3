[#ftl]
<%@ page import="org.beangle.security.context.SecurityContextHolder" %>
<%@ page import="org.beangle.security.core.Authentication" %>
<%@ page import="org.beangle.security.ui.DefaultAccessDeniedHandler" %>

<h1>Sorry, access is denied</h1>


<p>
<%= request.getAttribute(DefaultAccessDeniedHandler.ACCESS_DENIED_EXCEPTION_KEY)%>

<p>

<%		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) { %>
			Authentication object as a String: <%= auth.toString() %><BR><BR>
<%      } %>
