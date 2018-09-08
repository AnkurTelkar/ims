<%
response.setHeader("Cache-Control", "no-cache");
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
</head>
 
<body>
   <tiles:insertAttribute name="body" />
</body>
</html>