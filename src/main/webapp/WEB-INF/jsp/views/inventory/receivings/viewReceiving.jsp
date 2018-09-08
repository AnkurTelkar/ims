<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header" style="margin: 10px 25px;">
        <h1>
            Receiving
            <small>${receivingDto.receivingNo}</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-bar-chart-o"></i> Receiving</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Receiving</span></li>
        </ol>
    </section>
    <c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-info" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-check"></i> <em>${message}</em></h5>
            </div>
        </div>
    </c:if>

    <section class="invoice">
        <div class="row">
            <div class="col-sm-12 text-center">
                <h2 class="page-header">
                    <i class="fa fa-globe"></i> ${receivingDto.getStatusStr() }
                </h2>
                <input type="hidden" id="status" value="${receivingDto.status }" />
            </div>
            <div class="col-sm-4">
                    <c:set var="vendor" value="${receivingDto.vendor}"/>
                    <c:set var="firstVendorDetail" value="${vendor.vendorDetails[0]}"/>
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
            <div class="col-sm-4">
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
                <div class="col-sm-4">
                    <b>Receiving #: </b>${receivingDto.receivingNo}<br>
                    <b>Invoice Date:</b> <fmt:formatDate dateStyle="medium" value="${receivingDto.invoiceDate}" /><br>
<!--                    <b>Sub Total:</b> ${invoice.subTotal}<br>
                    <b>Tax:</b> ${invoice.totalTax}<br>-->
                    <b>Total:</b> ${receivingDto.total}

                </div>
        </div>
        <div class="row">
            <div class="col-sm-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                         	<th width="10%">Barcode</th>
                            <th width="20%">Particular</th>
                            <th width="5%">Qty</th>
                            <th width="10%">Cost Each</th>
                            <th width="10%">Cost</th>
                          	<th width="5%">GST(%)</th>
							<th width="10%">GST</th>
                            <th width="10%">Disc.</th>
                            <th width="10%">Amount</th>
                            <!-- <th width="15%">Ext. Cost</th> -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${receivingDto.receivingItems}" var="receivingItem"> 
                            <tr>
                                <td>${receivingItem.sku.barcode}</td>
                                <td>${receivingItem.getSkuDisplayName()}</td>
                                <td>${receivingItem.quantity}</td>
                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.cost}" /></td>
                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.getTotalCost()}" /></td>
                                <td>${receivingItem.gst}</td>
                                <td><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${receivingItem.getTotalGst() }" /></td>
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
            <div class="col-sm-offset-8 col-sm-4">
                <div class="table-responsive">
                    <table class="table text-right">
                        <tr>
                            <th style="width:50%">Total:</th>
                            <td>${receivingDto.recivingAmountDetails[0].amount}</td>
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