<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body class="skin-blue-light">
	<div class="bb-alert callout callout-danger" style="display: none;">
		<span id="messageFH">${message}</span>
    </div>
	<section class="content" style="min-height: 700px; background: #FCFEFF;">
	<div class="page-header">
			<div class='btn-toolbar pull-right'>
	            <a class='btn btn-lg btn-default' href="<c:url value='/inventory/sales/pointOfSale/logout.htm' />"><i class="fa fa-power-off" aria-hidden="true"></i></a>
	        </div>
			<h3><strong>Staff: </strong>${posUserDto.getFullName()}</h3>
			<div class="row" style="font-size: medium;">
				<small>
					<span class="col-sm-4 col-xs-6"> <strong>Login Time: </strong><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${loggedInTime}" /></span> 
					<!-- <span class="col-sm-4 col-xs-6"> <strong>Dues: </strong>$32154.54</span>
					<span class="col-sm-4 col-xs-6"> <strong>LastVisit: </strong>11/12/2016</span>
					<span class="col-sm-4 col-xs-6"> <strong>Favt. Items: </strong>Vaselene lotion</span> -->
				</small>
			</div>
			
			<!-- <div class="col-xs-2">
					
				</div> -->
		</div>
		<c:url var="loginPointOfSale" value="/inventory/sales/pointOfSale/addBill.htm" />
			<form:form method="post" class="form-horizontal" id="postLoginForm" action="${loginPointOfSale}" modelAttribute="">
				<div class="row">
					<div class=" col-sm-5">
						<div class="row form-group">
							<div class="col-sm-12 col-xs-6">
							<div class="col-xs-12">
								<jsp:useBean id="now" class="java.util.Date" />
								<input type="hidden" name="findBillsStartDate" id="findBillsStartDate" value="<fmt:formatDate type ="date" pattern="yyyy-MM-dd" value = "${sessionScope.findBillsStartDate eq null ? now : sessionScope.findBillsStartDate }" />">
								<input type="hidden" name="findBillsEndDate" id="findBillsEndDate" value="<fmt:formatDate type ="date" pattern="yyyy-MM-dd" value = "${sessionScope.findBillsEndDate eq null ? now : sessionScope.findBillsEndDate }" />">
								<input type="hidden" name="infoType" id="infoType" value="0">
								<input type="hidden" name="customerInfoSubmit" id="customerInfoSubmit" value="0">
                            	<input type="text" name="customerInfo" id="customerInfo" class="form-control input-lg" autocomplete="off" placeholder="Customer Detail">
                            	<input type="hidden" class="customer-info-hidden" id="customer-info-hidden" name="customer-info-hidden"
													data-id="" data-customername="" data-phoneno="" data-email="">
							</div>
							<div class="col-xs-12">
								<div>
									<a id="newBill" href="javascript: newBill('<c:url value='/inventory/sales/pointOfSale/addBill.htm'/>');" class="btn btn-primary btn-lg btn-block" style="font-size: 30px;" role="button">
										<i class="fa fa-plus" aria-hidden="true"></i> New
									</a>
								</div>
							</div>
							</div>
							<div class="col-sm-12 col-xs-6">
								<div class="col-xs-12">
									<input type="text" id="billNo" name="billNo" class="form-control input-lg" placeholder="Bill No.">
								</div>
								<div class="col-xs-12">
									<div>
										<a id="openBill" href="javascript: openBill('<c:url value='/inventory/sales/pointOfSale/openBill.htm'/>');" class="btn btn-primary btn-lg btn-block" style="font-size: 30px;" role="button">
											Open
										</a>
									</div>
								</div>
							</div>									
						</div>
					</div>
					<div class="col-sm-7">
						<div class="box">
							<div class="box-body">
				                <div class="input-group">
				                  <button type="button" class="btn btn-default pull-right" id="daterange-btn">
				                    <span>
				                      <i class="fa fa-calendar"></i> Date range picker
				                    </span>
				                    <i class="fa fa-caret-down"></i>
				                  </button>
				                </div>
							</div>
				            <div class="box-body" id="bill-div">
				            </div>
	          			</div>
					</div>
				</div>
			</form:form>
	</section>
</body>
