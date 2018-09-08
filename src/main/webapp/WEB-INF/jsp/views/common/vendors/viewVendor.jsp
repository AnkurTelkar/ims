<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>View Vendor</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Vendor</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Vendor</span></li>
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
                    <i class="fa fa-user-secret"></i> ${vendor.vendorName}
                </h2>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="col-sm-6">
                    <b>Vendor No.:</b> ${vendor.vendorCode} <br><br>
                    <b>GSTIN:</b> ${vendor.gstNo} <br><br>
                    <b>Description:</b> ${vendor.description} <br><br>
                    <b>Address:</b> ${vendor.address} <br><br>
                    <b>Created At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${vendor.createdAt}" /> <br><br>
                </div>
                <div class="col-sm-6">
                	<b>Vendor:</b> ${vendor.vendorName} <br><br>
                	<b>City:</b> ${vendor.city} <br><br>
                	<b>State:</b> ${vendor.state} <br><br>
                    <b>Updated At: </b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${vendor.updatedAt}" /> <br><br>
                </div>
            </div>
            <div class="box-header">
                <h2 class="page-header">
                    <i class="fa fa-envelope"></i>&nbsp;&nbsp;<i class="fa fa-phone"></i> Contact Details
                </h2>
            </div>
            <div class="box-body">
	            <c:forEach items="${vendor.vendorDetails}" var="vendorDetail" varStatus="status">
	            	<div class="row">
	            		<div class="col-sm-1"><b>${status.count}.</b>  <br><br></div>
		            	<div class="col-sm-3"><b>Contact Person:</b> ${vendorDetail.contactPerson} <br><br></div>
		            	<div class="col-sm-4"><b>Email:</b> ${vendorDetail.email} <br><br></div>
		            	<div class="col-sm-4"><b>Phone No.:</b> ${vendorDetail.phoneNo} <br><br></div>
	            	</div>
	            </c:forEach>
            </div>
            <div class="box-footer text-right">
            	<a href="<c:url value='/vendors/listVendors.htm'/>" class="btn btn-primary" role="button"> List Vendors</a>
                <a href="<c:url value='/vendors/addVendor.htm' />" class="btn btn-primary" role="button">Add New</a>
                <a href="<c:url value='/vendors/${vendor.id}/editVendor.htm' />" class="btn btn-info" role="button">Edit</a>
<!--                <button type="button" class="btn btn-default">Cancel</button>-->
            </div>
        </div>
    </section>
    
</div>