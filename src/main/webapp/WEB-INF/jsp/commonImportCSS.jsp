
<%-- <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/font-awesome-4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/ionicons-2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/dist/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/iCheck/flat/blue.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/morris/morris.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/datepicker/datepicker3.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jQueryUI/jquery-ui.min.css"> --%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.ims.ApplicationConstants" %>

<tiles:importAttribute name="stylesheets" />
<c:forEach var="stylesheet" items="${stylesheets}">
	<link rel="stylesheet" href="<c:url value="${stylesheet}"/>">
</c:forEach>

<style>
.custom-combobox {
	position: relative;
	display: inline-block;
}

.custom-combobox-toggle {
	position: absolute;
	top: 0;
	bottom: 0;
	margin-left: -1px;
	padding: 0;
}

.custom-combobox-input {
	margin: 0;
	padding: 5px 10px;
}

.search_field {
	display: block;
	margin: 0 auto;
	padding: 5px 10px;
	font-size: 28px;
	width: 50%;
}

.btn-sample {
	color: #505739;
	background-color: #EAE0C2;
	border-color: #333029;
}

.btn-sample:hover, .btn-sample:focus, .btn-sample:active, .btn-sample.active,
	.open .dropdown-toggle.btn-sample {
	color: #505739;
	background-color: #CCC2A6;
	border-color: #333029;
}

.btn-sample:active, .btn-sample.active, .open .dropdown-toggle.btn-sample
	{
	background-image: none;
}

.btn-sample.disabled, .btn-sample[disabled], fieldset[disabled] .btn-sample,
	.btn-sample.disabled:hover, .btn-sample[disabled]:hover, fieldset[disabled] .btn-sample:hover,
	.btn-sample.disabled:focus, .btn-sample[disabled]:focus, fieldset[disabled] .btn-sample:focus,
	.btn-sample.disabled:active, .btn-sample[disabled]:active, fieldset[disabled] .btn-sample:active,
	.btn-sample.disabled.active, .btn-sample[disabled].active, fieldset[disabled] .btn-sample.active
	{
	background-color: #EAE0C2;
	border-color: #333029;
}

.btn-sample .badge {
	color: #EAE0C2;
	background-color: #505739;
}

.bb-alert
{
    position: fixed;
    /* bottom: 25%;
    right: 0;
    margin-bottom: 0; */
    font-size: 1.2em;
    padding: 1em 1.3em;
    z-index: 2000;
    width: 100% !important;
}
</style>
<script>

	var STATUS_VOID = ${ApplicationConstants.STATUS_VOID};
	var STATUS_DRAFT = ${ApplicationConstants.STATUS_DRAFT };
	var STATUS_FINALIZE = ${ApplicationConstants.STATUS_FINALIZE };
	var STATUS_REFUND = ${ApplicationConstants.STATUS_REFUND };
	var STATUS_RETURN = ${ApplicationConstants.STATUS_RETURN };
</script>