<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>View Item SKU</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Item SKU</span></li>
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
                    <i class="fa fa-user-secret"></i> ${skuDto.skuCode}
                </h2>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="col-sm-6">
                	<b>Barcode: </b> ${skuDto.barcode} <br><br>
                	<b>Item: </b> ${skuDto.item.getItemName()} <br><br>
                	<b>Cost: </b> ${skuDto.cost} <br><br>
                	<b>MRP: </b> ${skuDto.retailPrice} <br><br>
                	<b>Selling Price: </b> ${skuDto.sellingPrice()} <br><br>
                    <b>Threshold: </b> ${skuDto.threshold} <br><br>
                    <b>Created At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${skuDto.createdAt}" /> <br><br>
                </div>
                <div class="col-sm-6">
					<b>Code: </b> ${skuDto.skuCode} <br><br>
					<b>Description: </b> ${skuDto.description} <br><br>                	
                    <b>GST(%): </b> ${skuDto.gst} <br><br>
                    <b>Discount (Div.): </b> ${skuDto.discount} <br><br>
                    <b>Quantity: </b> ${skuDto.quantity} <br><br>
                    <b>HSN: </b> ${skuDto.hsn} <br><br>
                    <b>Updated At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${skuDto.updatedAt}" /> <br><br>
                </div>
            </div>
            <div class="box-footer text-right">
                <a href="<c:url value='/inventory/skus/addSku.htm' />" class="btn btn-primary" role="button">Add New</a>
                <a href="<c:url value='/inventory/skus/${skuDto.skuId}/editSku.htm' />" class="btn btn-info" role="button">Edit</a>
                <button type="button" class="btn btn-default">Cancel</button>
            </div>
        </div>
    </section>
    
</div>