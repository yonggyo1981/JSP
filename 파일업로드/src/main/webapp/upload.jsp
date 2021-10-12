<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.core.FileManager" %>
<%
	String uploadUrls = FileManager.upload(request);
%>
<% if (uploadUrls != null) {%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>parent.callbackImageUpload("<%=uploadUrls%>");</script>
<% }%>