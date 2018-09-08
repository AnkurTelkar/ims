<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">  

<%@page pageEncoding="UTF-8" %>
<html>  
<head>  
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../jsp/commonImportCSS.jsp" %>

	<title><tiles:insertAttribute name="title" ignore="true" /></title>

</head>  
<body class="hold-transition skin-blue-light sidebar-mini">
    <div class="wrapper">
        <div><tiles:insertAttribute name="header" /></div>  
        <div style="float:left;">
            <tiles:insertAttribute name="menu" />
        </div>  
        <div>  
            <tiles:insertAttribute name="body" />
        </div>  
        <div style="clear:both"><tiles:insertAttribute name="footer" /></div>  
    </div>
</body>  
</html>
<%@ include file="../jsp/commonImportJS.jsp" %>