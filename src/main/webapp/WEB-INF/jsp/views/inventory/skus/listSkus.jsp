<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Items SKU List
            </h1>
            <ol class="breadcrumb">
                <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
                <li><a href="#"><i class="fa fa-user-secret"></i> Item SKU</a></li>
                <li class="active"><i class="fa fa-list"></i> <span>Show All SKUs</span></li>
            </ol>
        </section>

                <%@include file="listSkusDetails.jsp" %>
    
    </div>


