<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>View Item</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Item</span></li>
        </ol>
    </section>
    <c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-danger" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-cross"></i> <em>${message}</em></h5>
            </div>
        </div>
    </c:if>

    <section class="content">
        <div class="box box-primary">
            <div class="box-header">
                <h2 class="page-header">
                    <i class="fa fa-user-secret"></i> ${itemDto.itemName}
                </h2>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="col-sm-6">
                    <b>Code: </b> ${itemDto.itemCode} <br><br>
                    <b>Brand: </b> ${itemDto.brand.brandName} <br><br>
                    <b>Name: </b> ${itemDto.itemName} <br><br>
                    <b>Category: </b> ${itemDto.category.categoryName} <br><br>
                    
                </div>
                <div class="col-sm-6">
                    <b>Track Unit: </b> ${ itemDto.trackUnit.unitName } <br><br>
                    <b>Status: </b> ${itemDto.status eq 1 ? "Active" : "Inactive"} <br><br>
                    <%-- <b>Track Unit: </b> ${ itemDto.packUnitName } <br><br> --%>
                    <b>Created At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${itemDto.createdAt}" /> <br><br>
                    <b>Updated At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${itemDto.updatedAt}" /> <br><br>
                </div>
            </div>
            <div class="box-footer text-right">
            	<a href="<c:url value='/inventory/items/listItems.htm'/>" class="btn btn-primary" role="button"> List Items</a>
            	<a href="<c:url value='/inventory/items/${itemDto.itemId}/addSkus.htm' />" class="btn btn-primary" role="button">Add/Update SKUs</a>
                <a href="<c:url value='/inventory/items/addItem.htm' />" class="btn btn-primary" role="button">Add New</a>
                <a href="<c:url value='/inventory/items/${itemDto.itemId}/editItem.htm' />" class="btn btn-info" role="button">Edit</a>
                <button type="button" class="btn btn-default">Cancel</button>
            </div>
        </div>
    </section>
    
</div>