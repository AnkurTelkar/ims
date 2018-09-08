<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${customer.customerId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Customer</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Customer</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Customer</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Customer</h3>
            </div>
                <c:url var="addCustomer" value="/customers/addCustomer.htm"/>
                <form:form method="post" class="form-horizontal" action="${addCustomer}" modelAttribute="customer" >
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
	                    <label for="customer_code" class="col-sm-2 control-label">Customer No.</label>
	                    <div class="col-sm-4">
	                        <input type="hidden" id="customerId" name="customerId" value="${customer.customerId}">
	                        <form:errors path="customerCode" cssClass="text-red" />
	                        <input type="text" class="form-control" id="customer_code" name="customerCode" value="${customer.customerCode}" placeholder="Customer No." readonly autocomplete="off">
	                    </div>
	                    <label for="customer_name" class="col-sm-2 control-label">Customer Name</label>
	                    <div class="col-sm-4">
	                        <form:errors path="customerName" cssClass="text-red" />
	                        <input type="text" class="form-control" id="customer_name" name="customerName" value="${customer.customerName}" placeholder="Customer Name" autocomplete="off">
	                    </div>
	                </div>
	                <div class="form-group">
                        <label for="gstNo" class="col-sm-2 control-label">GSTIN #</label>
                        <div class="col-sm-4">
                        	<form:errors path="gstNo" cssClass="text-red" />
                            <input type="text" class="form-control" id="gstNo" name="gstNo" value="${customer.gstNo}" placeholder="GSTIN #" autocomplete="off">
                        </div>
                        <label for="description" class="col-sm-2 control-label">Description</label>
                        <div class="col-sm-4">
                            <textarea class="form-control" id="description" name="description" placeholder="Description" maxlength="255">${customer.description}</textarea>
                        </div>
                    </div>
					<div class="form-group">
						<label for="address" class="col-sm-2 control-label">Address</label>
                        <div class="col-sm-4">
                       		<input type="text" class="form-control" id="address" name="address" value="${customer.address}" placeholder="Address" maxlength="255" autocomplete="off">
						</div>
                        <label for="city" class="col-sm-2 control-label">City</label>
                        <div class="col-sm-4">
                        	<input type="text" class="form-control" id="city" name="city" value="${customer.city}" placeholder="City" autocomplete="off">
						</div>
					</div>
                    <div class="form-group">
                    	<label for="state" class="col-sm-2 control-label">State</label>
                        <div class="col-sm-4">
                        	<input type="text" class="form-control" id="state" name="state" value="${customer.state}" placeholder="State" maxlength="20" autocomplete="off">
						</div>
                    </div>
                    </div>
                    <div class="box-header with-border">
              			<button class="btn btn-success" id="add-customer-contact-detail" type="button"><i class="fa fa-plus"></i></button>
              			<h3 class="box-title">Contact Details</h3>
            		</div>
            		<div class="box-body" id="customerContactDetails">
            		<c:forEach items="${customer.customerDetails}" var="customerDetail" varStatus="status">
            			<div class="row">
            				<input type="hidden" name="customerDetails[${status.count-1}].id" id="id_${status.count-1}" value="${customerDetail.id}">
                            
            				<label for="contactPerson" class="col-xs-1 control-label"><i class="fa fa-user"></i></label>
            				<div class="col-xs-3">
            					<form:errors path="customerDetails[${status.count-1}].contactPerson" cssClass="text-red" />
                    			<input type="text" name="customerDetails[${status.count-1}].contactPerson" id="contact_person_${status.count-1}" class="form-control" autocomplete="off" value="${customerDetail.contactPerson}" maxlength="30" placeholder="Contact Person">
							</div>
							
							<label for="email" class="col-xs-1 control-label"><i class="fa fa-envelope" aria-hidden="true"></i> </label>
                            <div class="col-xs-3">
                            	<form:errors path="customerDetails[${status.count-1}].email" cssClass="text-red" />
                                <input type="email" name="customerDetails[${status.count-1}].email" id="email_${status.count-1}" class="form-control" autocomplete="off" value="${customerDetail.email}" maxlength="50" placeholder="Email: Ex. 123@example.com">
                            </div>
                            <label for="phoneNo" class="col-xs-1 control-label"><i class="fa fa-phone" aria-hidden="true"></i></label>
                            <div class="col-xs-3">
                            	<form:errors path="customerDetails[${status.count-1}].phoneNo" cssClass="text-red" />
                                <input type="number" name="customerDetails[${status.count-1}].phoneNo" id="phone_no_${status.count-1}" class="form-control" autocomplete="off" value="${customerDetail.phoneNo}" maxlength="11" placeholder="Phone #: Ex.7772863424">
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
