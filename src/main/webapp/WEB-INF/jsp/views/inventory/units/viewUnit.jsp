<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>View Unit</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Unit</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Unit</span></li>
        </ol>
    </section>
    <c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-info" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-check"></i> <em>${message}</em></h5>
            </div>
        </div>
    </c:if>

    <section class="content">
        <div class="box box-primary">
            <div class="box-header">
                <h2 class="page-header">
                    <i class="fa fa-user-secret"></i> ${unitDto.unitName}
                </h2>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="col-sm-6">
                    <b>Name: </b> ${unitDto.unitName} <br><br>
                    <b>Amount: </b> ${unitDto.amount} <br><br>
                    <%-- <b>Basic Unit: </b><span class="text-capitalize">${unitDto.getDisplayName()}</span><br><br> --%>
                    <b>Basic Unit: </b><span class="text-capitalize">${unitDto.measuringUnit.name}</span><br><br>
                </div>
                <div class="col-sm-6">
                    <b>Created At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${unitDto.createdAt}" /> <br><br>
                    <b>Updated At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${unitDto.updatedAt}" /> <br><br>
                </div>
            </div>
            <div class="box-footer text-right">
            	<a href="<c:url value='/inventory/units/listUnits.htm'/>" class="btn btn-primary" role="button"> List Units</a>
                <a href="<c:url value='/inventory/units/addUnit.htm' />" class="btn btn-primary" role="button">Add New</a>
                <a href="<c:url value='/inventory/units/${unitDto.unitId}/editUnit.htm' />" class="btn btn-info" role="button">Edit</a>
                <button type="button" class="btn btn-default">Cancel</button>
            </div>
        </div>
    </section>
    
</div>