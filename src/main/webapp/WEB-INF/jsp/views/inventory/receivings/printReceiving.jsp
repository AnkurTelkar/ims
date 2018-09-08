<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.ims.ApplicationConstants"%>
<style>
.row, .invoice{
	margin: 0px;
	padding: 0px;
}

table, thead, tbody, tfoot, th, tr, td {
	border: 1px solid;
	
}
</style>
<div class="" style="min-height: 450px; margin: 0px;">
	<div class="content-wrapper" style="min-height: 450px; margin: 0px;">
	    <section class="invoice">
	        <div class="row">
	            <div class="col-xs-12 text-center"><h3 class="page-header">${ receivingDto.status eq ApplicationConstants.STATUS_RETURN ? receivingDto.getStatusStr() : "Purchase Invoice" }</h3></div>
	            <div class="col-xs-4">
                    <c:set var="vendor" value="${receivingDto.vendor}"/>
                    <c:set var="firstVendorDetail" value="${vendor.vendorDetails[0]}"/>
                    <input type="hidden" id="status" value="${receivingDto.status }" />
               From:
               <div id="div-from">
                    <address>
                        <strong>${vendor.getDisplayName()}</strong><br>
                        ${vendor.address}<br>
                        GSTIN: ${vendor.gstNo}<br> 
                        Phone: ${firstVendorDetail.phoneNo}<br>
                        Email: ${firstVendorDetail.email}
                    </address>
                </div>
             </div>
            <div class="col-xs-4">
            	To:
            	<div id="div-to">
            		<address>
                        <strong>K.N Sales</strong><br>
                        17/2/3, Block-B, 1st Floor, National<br>
                        Market, Jawar Marg, Siyaganj, Indore <br>
                        Contact: 888 999 8286<br>
                        GSTIN: 23DVUPK6855F1Z2
                    </address>
            	</div>
			</div>
	                <!-- /.col -->
	                <div class="col-xs-4">
	                    <b>Receiving #: </b>${receivingDto.receivingNo}<br>
	                    <b>Invoice Date:</b> <fmt:formatDate dateStyle="medium" value="${receivingDto.invoiceDate}" /><br>
	<!--                    <b>Sub Total:</b> ${invoice.subTotal}<br>
	                    <b>Tax:</b> ${invoice.totalTax}<br>-->
	                    <b>Total:</b> ${receivingDto.total}
	
	                </div>
	        </div>
	        <div class="row">
	            <div class="col-xs-12 table-responsive">
	                <table class="table table-striped">
	                    <thead>
	                    	<tr>
	                            <td width="25%"><strong>Particular</strong></td>
	                            <td width="5%"><strong>Qty</strong></td>
	                            <td width="10%"><strong>Cost Each</strong></td>
	                            <td width="10%"><strong>Cost</strong></td>
	                            <td width="5%"><strong>CGST(%)</strong></td>
	                          	<td width="10%"><strong>CGST</strong></td>
								<td width="5%"><strong>SGST(%)</strong></td>
	                          	<td width="10%"><strong>SGST</strong></td>
	                          	<td width="10%"><strong>Disc.</strong></td>
	                            <td width="10%"><strong>Amount</strong></td>
	                            <!-- <th width="15%">Ext. Cost</th> -->
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:forEach items="${receivingDto.receivingItems}" var="receivingItem"> 
	                            <tr>
	                                <td>${receivingItem.getSkuDisplayName()}</td>
	                                <td>${receivingItem.quantity}</td>
	                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.cost}" /></td>
	                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.getTotalCost()}" /></td>
	                                <td>${receivingItem.getCgst()}</td>
	                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.getTotalCgst() }" /></td>
	                                <td>${receivingItem.getSgst()}</td>
	                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.getTotalSgst() }" /></td>
	                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.getTotalDiscount() }" /></td>
	                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${ receivingItem.getTotalCost() - receivingItem.getTotalDiscount() }" /></td>
	                            </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	        <div class="row">
	            <!-- accepted payments column -->
	            <!-- /.col -->
	            <div class="col-xs-offset-8 col-xs-4">
	                <div class="table-responsive">
	                    <table class="table text-right">
	                        <tr>
	                            <th style="width:50%; border:1px solid;">Total:</th>
	                            <td style="border:1px solid;">${receivingDto.recivingAmountDetails[0].amount}</td>
	                        </tr>
	                        <tr>
	                            <th>Cash Discount</th>
	                            <td>${receivingDto.recivingAmountDetails[1].amount * -1}</td>
	                        </tr>
	                        <%-- <tr>
	                            <th>Display Discount</th>
	                            <td>${receivingDto.recivingAmountDetails[2].amount * -1}</td>
	                        </tr> --%>
	                        <tr>
	                            <th>CGST</th>
	                            <td id="cgst">${receivingDto.recivingAmountDetails[3].amount}</td>
	                        </tr>
	                        <tr>
	                            <th>SGST</th>
	                            <td id="sgst">${receivingDto.recivingAmountDetails[4].amount}</td>
	                        </tr>
	                        <tr>
	                            <th>Grand Total:</th>
	                            <td>${receivingDto.total}</td>
	                        </tr>
	                    </table>
	                </div>
	            </div>
	            <!-- /.col -->
	        </div>
	        <!-- /.row -->
	
	        <!-- this row will not appear when printing -->
	        <div class="box-footer text-right" style="padding-left: 0px; padding-right: 0px;">
	        	<a href="<c:url value='/inventory/receivings/${receivingDto.receivingId}/printReceiving.htm'/>" id="printReceiving" target="_blank" class="btn btn-primary"><i class="fa fa-print"></i> Print</a>
	        	<a href="<c:url value='/inventory/receivings/listReceivings.htm'/>" class="btn btn-primary" role="button"> List Receivings</a>
	            <a href="<c:url value='/inventory/receivings/addReceiving.htm'/>" class="btn btn-primary" role="button">Add New</a>
	            <a href="<c:url value='/inventory/receivings/${receivingDto.receivingId}/editReceiving.htm'/>" class="btn btn-info" role="button">Edit</a>
	<!--            <button type="button" class="btn btn-default">Cancel</button>-->
	        </div>
	    </section>
	</div>
</div>

<script src="../../../resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script>
	$(document).ready( function() {
		$(".main-header").closest("div").hide();
		$(".main-footer").closest("div").hide();
		$(".main-sidebar").closest("div").hide();
		$(".box-footer").hide();
	} );
	
	window.onload = function () {
	  window.print();
	  setTimeout(function(){window.close();}, 1);
	}
</script>