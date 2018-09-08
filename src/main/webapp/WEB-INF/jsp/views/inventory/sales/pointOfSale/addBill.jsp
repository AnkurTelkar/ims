<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.ims.ApplicationConstants" %>
<style>
.payment-buttons > a:nth-of-type(even) {
  width: .5%;
}
</style>
<div id="mainDiv" style="overflow: hidden;">
	<div class="bb-alert callout callout-info" style="display: none;">
        <span id="message"></span>
    </div>
	<section class="content" style="background: #FCFEFF;">
		<div class="page-header">
			<div class='btn-toolbar pull-right'>
	            <a class='btn btn-lg btn-default' href="<c:url value='/inventory/sales/pointOfSale/login.htm' />"><i class="fa fa-chevron-left" title="Go Back" aria-hidden="true"></i></a>
				<a class='btn btn-lg btn-default' href="<c:url value='/inventory/sales/pointOfSale/logout.htm' />"><i class="fa fa-power-off" title="Logout" aria-hidden="true"></i></a>
	        </div>
			<h2>${billDto.customer.getDisplayName() }</h2>
			<div class="row" style="font-size: medium;">
				<small>
					<span class="col-sm-4 col-xs-6"> <strong>Bill #: </strong>${billDto.billNo}</span>
					<span class="col-sm-4 col-xs-6"> <strong>Created At: </strong>
						<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${billDto.createdAt}" />
					</span>
					<span class="col-sm-4 col-xs-6"> <strong>Status: </strong><span id="statusStr">${billDto.getStatusStr() }</span></span> 
					<!-- <span class="col-sm-4 col-xs-6"> <strong>Dues: </strong>$32154.54</span>
					<span class="col-sm-4 col-xs-6"> <strong>LastVisit: </strong>11/12/2016</span>
					<span class="col-sm-4 col-xs-6"> <strong>Favt. Items: </strong>Vaselene lotion</span> -->
				</small>
				<input id="posUserDto" type="hidden" value="<c:out value="${sessionScope.posUserDto.userId }" />">
				<input id="bill" name="bill" type="hidden" data-billid="${billDto.billId}" data-customerid="${billDto.customer.customerId }"
					data-userid="${billDto.user.userId }" data-status="${billDto.status }">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="col-sm-5 padding-0">
				<div class="panel panel-success" style="overflow: hidden;">
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group input-group-lg" style="margin: 1%;">
								<span class="input-group-addon" style="background: #DFF0D8;"><i class="fa fa-filter" aria-hidden="true"></i></span>
								<input id="" type="text" class="form-control autocomplete_txt" name="" placeholder="Item Desc">
							</div>
							<div class="input-group input-group-lg" style="margin: 1%;">
								<span class="input-group-addon" style="background: #DFF0D8;"><i class="fa fa-barcode" aria-hidden="true"></i></span> 
								<input id="" type="text" class="form-control barcode" name="" placeholder="Barcode" >
							</div>
						</div>
					</div>
					<div class="row">
		  				<div class="col-xs-12" style="font-size: 1.2em; background: #DFF0D8; padding: 1.5%;">
			  				<div class="col-xs-2"><b><i>Qty</i></b></div>
			  				<div class="col-xs-5"><b><i>Product</i></b></div>
			  				<div class="col-xs-2"><b><i>Price</i></b></div>
			  				<div class="col-xs-2"><b><i>Total</i></b></div>
			  				<div class="col-xs-1"></div>
		  				</div>
					</div>
					<div class="row">
			  			<div class="col-xs-12 ">
			  				<div id="billItemDiv" class=""style="overflow: auto; height: 26em;" >
				  				<table class="table" id="myTable">
									<tbody>
										<c:forEach items="${billDto.billItems}" var="billItem" varStatus="status">
											<tr class="alert alert-dismissible-justified">
												<td class="col-xs-2">
													<input type="hidden" class="bill-item" id="bill_item_${status.count-1}" name="billItems[${status.count-1}].billItemId"
													data-skuid="${billItem.sku.skuId }" 
													data-gst="${billItem.sku.gst}" 
													data-qty="${billItem.quantity }"  
													data-price="${billItem.price }" 
													data-soldby="${billItem.soldBy.userId }"
													data-itemdiscount="${billItem.discount }"
													>
													<span class="badge">
														<i>
															<span class="qty">
																<fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billItem.quantity }" />
															</span>
														</i>
													</span>
												</td>
												<td class="col-xs-5">
													<span class="desc">${billItem.getSkuDisplayName()}</span><br />
													<span class='itemDiscountDisplay ${billItem.discount gt 0 ? "show" : "hide" }'>
														<fmt:formatNumber var="discountPercent" type = "number" maxFractionDigits = "2" value = "${billItem.price gt 0 ? billItem.discount / billItem.price * 100 : 0 }" />
														<strong><em>Price: ${billItem.price }, Disc(${discountPercent }%): ${billItem.discount } @Each </em></strong>
														
													</span>
												</td>
												<td class="col-xs-2">
													<span class="badge"><i><span class="price"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billItem.price - billItem.discount }" /></span></i></span>
												</td>
												<td class="col-xs-2"><span class="badge">
													<i><span class="total-price"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billItem.quantity *  ( billItem.price - billItem.discount ) }" /></span></i></span>
												</td>
												<td class="col-xs-1">
													<a href="#" data-dismiss="alert" class="close"> <i class="fa fa-trash"></i></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
			  				</div>
			  			</div>
					</div>
					<div class="row">
						<div class="col-xs-12 text-right">
							<div class="col-xs-6" style="border-top: 1px solid;"><strong>Sub-Total:</strong></div>
							<div class="col-xs-6 sub-total" style="border-top: 1px solid;" data-type="${ApplicationConstants.TOTAL }" data-id="${billDto.getAmountDetailByTypeId( ApplicationConstants.TOTAL ).id }">
								<c:set var="total" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.TOTAL ).amount }" />
								<fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${total ne 0 ? total : 0.00 }" />
							</div>
							<div class="col-xs-6"><strong>Discount:</strong></div>
							<div class="col-xs-6 discount" data-type="${ApplicationConstants.CASH_DISCOUNT}" data-id="${billDto.getAmountDetailByTypeId( ApplicationConstants.CASH_DISCOUNT ).id }">
								<c:set var="discount" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.CASH_DISCOUNT ).amount }" />
								${discount gt 0 or discount lt 0 ? discount : 0.00 }
							</div>
							<div class="col-xs-6"><strong>CGST:</strong></div>
							<div class="col-xs-6 CGST" data-type="${ApplicationConstants.CGST}" data-id="${billDto.getAmountDetailByTypeId( ApplicationConstants.CGST ).id }">
								<c:set var="CGST" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.CGST ).amount }" />
								<fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${CGST gt 0 ? CGST : 0.00 }" />
							</div>
							<div class="col-xs-6"><strong>SGST:</strong></div>
							<div class="col-xs-6 SGST" data-type="${ApplicationConstants.SGST}" data-id="${billDto.getAmountDetailByTypeId( ApplicationConstants.SGST ).id }">
								<c:set var="SGST" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.SGST ).amount }" />
								<fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${SGST gt 0 ? SGST : 0.00 }" />
							</div>
							<div class="col-xs-6" style="border-top: 1px solid;"><strong>Total:</strong></div>
							<div class="col-xs-6 total" style="border-top: 1px solid;"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billDto.total}" /></div>
						</div>
					</div>
				</div>
				</div>
				<div class="col-sm-7">
					<div class="row">
						<div class="col-sm-6 col-xs-4 text-right btn-div">
							<div class="btn-group btn-group-justified payment-buttons">
								<a href="javascript: settlePayment('check');" class="btn btn-lg btn-primary">Check<br>
									<span id="check" data-type="${ApplicationConstants.PAYMENT_TYPE_CHECK }" class="paid-amount">${billDto.getPaymentByTypeId(ApplicationConstants.PAYMENT_TYPE_CHECK) }</span></a>
								<a href="javascript: deletePayment('check');" class="btn btn-lg btn-info">
									<i class="fa fa-times"></i>
								</a>
								<a href="javascript: settlePayment('cash');" class="btn btn-lg btn-primary">Cash <br>
									<span id="cash" data-type="${ApplicationConstants.PAYMENT_TYPE_CASH }" class="paid-amount">${billDto.getPaymentByTypeId(ApplicationConstants.PAYMENT_TYPE_CASH) }</span></a>
								<a href="javascript: deletePayment('cash');" class="btn btn-lg btn-info">
									<i class="fa fa-times"></i>
								</a>
							</div>
							<div class="btn-group btn-group-justified payment-buttons">
								<a href="javascript: settlePayment('ewallet');" class="btn btn-lg btn-primary">E-Wallet<br>
									<span id="ewallet" data-type="${ApplicationConstants.PAYMENT_TYPE_EWALLET }" class="paid-amount">${billDto.getPaymentByTypeId(ApplicationConstants.PAYMENT_TYPE_EWALLET) }</span></a>
								<a href="javascript: deletePayment('ewallet');" class="btn btn-lg btn-info">
									<i class="fa fa-times"></i>
								</a>
								<a href="javascript: settlePayment('account');" class="btn btn-lg btn-primary">Account<br>
									<span id="account" data-type="${ApplicationConstants.PAYMENT_TYPE_ACCOUNT }" class="paid-amount">${billDto.getPaymentByTypeId(ApplicationConstants.PAYMENT_TYPE_ACCOUNT) }</span></a>
								<a href="javascript: deletePayment('account');" class="btn btn-lg btn-info">
									<i class="fa fa-times"></i>
								</a>
							</div>
							<div class="btn-group btn-group-justified payment-buttons">
								<a href="javascript: settlePayment('debitCard');" class="btn btn-lg btn-primary custom-label">Debit Card 
									<span id="debitCard" data-type="${ApplicationConstants.PAYMENT_TYPE_DEBIT_CARD }" class="paid-amount col-xs-offset-1">${billDto.getPaymentByTypeId(ApplicationConstants.PAYMENT_TYPE_DEBIT_CARD) }</span></a>
								<a href="javascript: deletePayment('debitCard');" class="btn btn-lg btn-info">
									<i class="fa fa-times"></i>
								</a>
							</div>
							<div class="btn-group btn-group-justified payment-buttons">
								<a href="javascript: settlePayment('creditCard');" class="btn btn-lg btn-primary custom-label">Credit Card 
									<span id="creditCard" data-type="${ApplicationConstants.PAYMENT_TYPE_CREDIT_CARD }" class="paid-amount col-xs-offset-1">${billDto.getPaymentByTypeId(ApplicationConstants.PAYMENT_TYPE_CREDIT_CARD) }</span></a>
								<a href="javascript: deletePayment('creditCard');" class="btn btn-lg btn-info">
									<i class="fa fa-times"></i>
								</a>
							</div>
							<div class="text-right">
								<!-- <div class="page-header" style="font-size: small;">
									<div class="row" style="height:5em;  overflow:auto;">
										<span class="col-sm-5 col-xs-6 "> <strong>Cash: </strong></span><span class="col-sm-7 col-xs-6 ">512.00</span>
										<span class="col-sm-5 col-xs-6 "><strong>CC.: </strong></span><span class="col-sm-7 col-xs-6 ">488.00</span>
										<span class="col-sm-5 col-xs-6 "><strong>Debit: </strong></span><span class="col-sm-7 col-xs-6 ">9099.00</span>
										<span class="col-sm-5 col-xs-6 "> <strong>Cash: </strong></span><span class="col-sm-7 col-xs-6 ">512.00</span>
										<span class="col-sm-5 col-xs-6 "><strong>CC.: </strong></span><span class="col-sm-7 col-xs-6 ">488.00</span>
										<span class="col-sm-5 col-xs-6 "><strong>Debit: </strong></span><span class="col-sm-7 col-xs-6 ">9099.00</span>
									</div>
								</div> -->
								<div class="page-header"  style="font-size: small;">
									<div class="row">
										<span class="col-sm-5 col-xs-6 "> <strong>Paid: </strong></span><span class="col-sm-7 col-xs-6 "><span class="paid">0.00</span></span>
										<span class="col-sm-5 col-xs-6 "> <strong>Total: </strong></span><span class="col-sm-7 col-xs-6 "><span class="total">0.00</span></span>
									</div>
								</div>
								<div class="page-header"  style="font-size: small;">
									<div class="row">
										<span class="col-sm-5 col-xs-6 custom-label"> <strong>Balance: </strong></span><span class="col-sm-7 col-xs-6 "><span class="balance">0.00</span></span>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-xs-8 form-group rcorners1" style="background: #367fa9; padding: 1%;">
							<input type="text" id="field0" name="field0" class="search_field" value="0.00">
							<div id="keyboard_numbers_only"></div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4 form-group">
							<a href="javascript: processBill(${ApplicationConstants.STATUS_REFUND });" class="btn btn-lg btn-sample col-xs-12 process-bill">
								<i class="fa fa-undo" aria-hidden="true"></i> Credit Note</a>
						</div>
						<div class="col-xs-4 form-group">
								<a href="javascript: processBill(${ApplicationConstants.STATUS_DRAFT });" class="btn btn-lg btn-sample col-xs-12 process-bill">
									<i class="fa fa-floppy-o" aria-hidden="true"></i> Save</a>
							</div>
							<div class="col-xs-4 form-group">
								<a href="javascript: processBill(${ApplicationConstants.STATUS_FINALIZE });" class="btn btn-lg btn-sample col-xs-12 process-bill">
									<i class="fa fa-shopping-cart" aria-hidden="true"></i> Finalize</a>
							</div>
					</div>
					<div class="btn-group btn-group-lg btn-group-justified btn-div" style="height: 4.5em;">
						<a href="javascript: editQty();" class="btn btn-sample"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Qty.</a>
						<a href="javascript: selectAll();" class="btn btn-sample">Select All</a>
						<a href="javascript: deselectAll();" class="btn btn-sample">Deselect All</a>
						<a href="javascript: deleteSelected();" class="btn btn-danger"><i class="fa fa-trash"></i> Items</a>
					</div>

					<div class="btn-group btn-group-lg btn-group-justified btn-div" style="height: 4.5em;">
					<a href="javascript: editPrice();" class="btn btn-sample"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Price</a>
						<a id="reOpen" href="javascript: reopenBill();" class="btn btn-sample disabled"><i class="fa fa-folder-open" aria-hidden="true"></i> Re Open</a>
						<!-- <a id="print" href="javascript: printBill();" class="btn btn-sample"><i class="fa fa-print"></i> Print</a> -->
						<a id="print" href="<c:url value='/inventory/sales/pointOfSale/${billDto.billId }/printBill.htm'/>" target="_blank" class="btn btn-sample"><i class="fa fa-print"></i> Print</a>
						<a href="javascript: processBill(${ApplicationConstants.STATUS_VOID });" class="btn btn-danger">Void</a>
					</div>
					<div class="btn-group btn-group-lg btn-group-justified btn-div" style="height: 4.5em;">
						<!-- <a href="javascript: showAlertSmall( 'Work under construction!' );" class="btn btn-sample"><i class="fa fa-plus" aria-hidden="true"></i> Items</a> -->
						<a href="javascript: addItemDiscount();" class="btn btn-sample">Item Discount</a>
						<a href="javascript: showAlertSmall( 'Work under construction!' );" class="btn btn-sample"><i class="fa fa-refresh" aria-hidden="true"></i> Customer</a>
						<!-- <a href="javascript: showAlertSmall( 'Work under construction!' );" class="btn btn-sample"><i class="fa fa-refresh" aria-hidden="true"></i> Staff</a> -->
						<a href="javascript: refundItems();" class="btn btn-sample"><i class="fa fa-refresh" aria-hidden="true"></i> Refund</a>
						<a href="javascript: addDiscount();" class="btn btn-sample">Bill Discount (%)</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<div id="printDiv"></div>
