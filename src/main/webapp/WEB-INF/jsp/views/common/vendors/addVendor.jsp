<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${vendor.id gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Vendor</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Vendor</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Vendor</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Vendor</h3>
            </div>
                <c:url var="addVendor" value="/vendors/addVendor.htm"/>
                <form:form method="post" class="form-horizontal" action="${addVendor}" modelAttribute="vendor" >
				<c:if test="${not empty message}">
					<div class="pad margin no-print">
						<div class="alert alert-warning fade in">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							${message}
						</div>
					</div>
				</c:if>
				<div class="box-body">
	                <div class="form-group">
	                    <label for="vendor_code" class="col-sm-2 control-label">Vendor No.</label>
	                    <div class="col-sm-4">
	                        <input type="hidden" id="id" name="id" value="${vendor.id}">
	                        <form:errors path="vendorCode" cssClass="text-red" />
	                        <input type="text" class="form-control" id="vendor_code" name="vendorCode" value="${vendor.vendorCode}" placeholder="Vendor No." readonly autocomplete="off">
	                    </div>
	                    <label for="vendor_name" class="col-sm-2 control-label">Vendor Name</label>
	                    <div class="col-sm-4">
	                        <form:errors path="vendorName" cssClass="text-red" />
	                        <input type="text" class="form-control" id="vendor_name" name="vendorName" value="${vendor.vendorName}" placeholder="Vendor Name" autocomplete="off">
	                    </div>
	                </div>
                    <div class="form-group">
                        <label for="gstNo" class="col-sm-2 control-label">GSTIN #</label>
                        <div class="col-sm-4">
                        	<form:errors path="gstNo" cssClass="text-red" />
                            <input type="text" class="form-control" id="gstNo" name="gstNo" value="${vendor.gstNo}" placeholder="GSTIN #" autocomplete="off">
                        </div>
                        <label for="description" class="col-sm-2 control-label">Description</label>
                        <div class="col-sm-4">
                            <textarea class="form-control" id="description" name="description" placeholder="Description" maxlength="255">${vendor.description}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-2 control-label">Address</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="address" name="address" value="${vendor.address}" placeholder="Address" maxlength="255" autocomplete="off">
                        </div>
                        <label for="city" class="col-sm-2 control-label">City</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="city" name="city" value="${vendor.city}" placeholder="City" autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="state" class="col-sm-2 control-label">State</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="state" name="state" value="${vendor.state}" placeholder="State" maxlength="20" autocomplete="off">
                        </div>
                    </div>
                    </div>
                    <div class="box-header with-border">
              			<button class="btn btn-success" id="add-vendor-contact-detail" type="button"><i class="fa fa-plus"></i></button>
              			<h3 class="box-title">Contact Details</h3>
            		</div>
            		<div class="box-body" id="vendorContactDetails">
            		<c:forEach items="${vendor.vendorDetails}" var="vendorDetail" varStatus="status">
            			<div class="row">
            				<input type="hidden" name="vendorDetails[${status.count-1}].id" id="id_${status.count-1}" value="${vendorDetail.id}">
                            
            				<label for="contactPerson" class="col-xs-1 control-label"><i class="fa fa-user"></i></label>
            				<div class="col-xs-3">
            					<form:errors path="vendorDetails[${status.count-1}].contactPerson" cssClass="text-red" />
                    			<input type="text" name="vendorDetails[${status.count-1}].contactPerson" id="contact_person_${status.count-1}" class="form-control" autocomplete="off" value="${vendorDetail.contactPerson}" maxlength="30" placeholder="Contact Person">
							</div>
							
							<label for="email" class="col-xs-1 control-label"><i class="fa fa-envelope" aria-hidden="true"></i> </label>
                            <div class="col-xs-3">
                            	<form:errors path="vendorDetails[${status.count-1}].email" cssClass="text-red" />
                                <input type="email" name="vendorDetails[${status.count-1}].email" id="email_${status.count-1}" class="form-control" autocomplete="off" value="${vendorDetail.email}" maxlength="50" placeholder="Email: Ex. 123@example.com">
                            </div>
                            <label for="phoneNo" class="col-xs-1 control-label"><i class="fa fa-phone" aria-hidden="true"></i></label>
                            <div class="col-xs-3">
                            	<form:errors path="vendorDetails[${status.count-1}].phoneNo" cssClass="text-red" />
                                <input type="number" name="vendorDetails[${status.count-1}].phoneNo" id="phone_no_${status.count-1}" class="form-control" autocomplete="off" value="${vendorDetail.phoneNo}" maxlength="11" placeholder="Phone #: Ex.7772863424">
                            </div>
           				</div>
            		</c:forEach>
            		
            		</div>
                    <div class="box-footer text-right">
                        <button type="submit" class="btn btn-info" data-btnsaveorupdate>${label}</button>
<!--                        <button type="submit" class="btn btn-default">Cancel</button>-->
                    </div>
                </form:form>
            </div>
    </section>
</div>
