<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>View Category</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Category</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Category</span></li>
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
                    <i class="fa fa-user-secret"></i> ${categoryDto.categoryName}
                </h2>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="col-sm-6">
                    <b>Code:</b> ${categoryDto.categoryCode} <br><br>
                    <b>Name:</b> ${categoryDto.categoryName} <br><br>
                    <b>Description:</b> ${categoryDto.description} <br><br>
                </div>
                <div class="col-sm-6">
                    <b>Created At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${categoryDto.createdAt}" /> <br><br>
                    <b>Updated At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${categoryDto.updatedAt}" /> <br><br>
                </div>
            </div>
            <div class="box-footer text-right">
            	<a href="<c:url value='/inventory/categories/listCategories.htm'/>" class="btn btn-primary" role="button"> List Categories</a>
                <a href="<c:url value='/inventory/categories/addCategory.htm' />" class="btn btn-primary" role="button">Add New</a>
                <a href="<c:url value='/inventory/categories/${categoryDto.categoryId}/editCategory.htm' />" class="btn btn-info" role="button">Edit</a>
                <button type="button" class="btn btn-default">Cancel</button>
            </div>
        </div>
    </section>
    
</div>