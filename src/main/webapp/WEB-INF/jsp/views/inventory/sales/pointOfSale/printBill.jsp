<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.ims.ApplicationConstants" %>

<style>
.row, .invoice{
	margin: 0px;
	padding: 0px;
}

table, thead, tbody, tfoot, th, tr, td {
	border: 1px solid;
	
}
</style>

<div class="content-wrapper" style="min-height: 450px; margin: 0px;">
    <section class="invoice">
    
    	<div class="page-header row text-center ${billDto.getStatus() eq  ApplicationConstants.STATUS_REFUND ? "" : "hide" }">
            <div class="col-xs-12"><h3>${billDto.getStatusStr()}</h3></div>
            </div>
        <div class="row">
            <div class="col-xs-4">
                    From:
                    <address>
                        <strong>K.N Sales</strong><br>
                        17/2/3, Block-B, 1st Floor, National<br>
                        Market, Jawar Marg, Siyaganj, Indore <br>
                        <b>Contact:</b> 7999 6969 72<br>
                        <b>GSTIN:</b> 23DVUPK6855F1Z2
                 	   </address>
                </div>
            <div class="col-xs-4">
                    To:
                    <address>${billDto.customer.getChitDisplayName() }</address>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <b>Bill #: </b>${billDto.billNo}<br>
                    <b>Date:</b> <fmt:formatDate type="both" dateStyle="medium" value="${billDto.createdAt}" /><br>
                    <b>Total:</b> ${billDto.total}

                </div>
        </div>
        <div class="row">
            <div class="col-xs-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr style="border: 1px solid;">
                         	<th style="width:3%;  border: 1px solid;">No.</th>
                            <th style="width:38%; border: 1px solid;">Particular</th>
                            <th style="width:11%; border: 1px solid;">HSN</th>
                          	<th style="width:9%; border: 1px solid;">GST(%)</th>
							<th style="width:9%; border: 1px solid;">Qty.</th>
							<th style="width:7%; border: 1px solid;">Disc.</th>
                            <th style="width:11%; border: 1px solid;">Rate</th>
                            <th style="width:12%; border: 1px solid;">Amount</th>
                        </tr>
                    </thead>
                    <tbody style="min-height: 500px;">
                        <c:forEach items="${billDto.billItems}" var="billItem" varStatus="status"> 
                            <tr>
                            <c:set var="tdCount" value="${status.count }"></c:set>
                                <td>${tdCount}</td>
                                <td>${billItem.getSkuDisplayName()}
                                	<%-- <span class='itemDiscountDisplay ${billItem.discount gt 0 ? "show" : "hide" }'>
										<fmt:formatNumber var="discountPercent" type = "number" maxFractionDigits = "1" value = "${billItem.price gt 0 ? billItem.discount / billItem.price * 100 : 0 }" />
										<strong><em>Price: ${billItem.price }, Disc(${discountPercent }%): ${billItem.discount } @Each </em></strong>
									</span> --%>                                
                                </td>
                                <td>${billItem.sku.hsn}</td>
                                <td class="text-right">${billItem.sku.gst }</td>
                                <td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billItem.quantity }" /></td>
                                <td class="text-right"><fmt:formatNumber type = "percent" maxFractionDigits = "1" value = "${billItem.price gt 0 ? billItem.discount / billItem.price : 0 }" /></td>
                                <td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billItem.price - billItem.discount }" /></td>
                                <td class="text-right"><b><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${billItem.quantity *  ( billItem.price - billItem.discount ) }" /></b></td>
                            </tr>
                        </c:forEach>
                    	<c:if test="${tdCount lt 5 }">
		                    <tr style="height: ${50 * (5-tdCount)}px">
		                    	<td colspan="8"></td>
		                    </tr>
                    	</c:if>
	                    	<tr>
	                            <th colspan="7">Sub-Total:</th>
	                            <td class="text-right"><c:set var="total" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.TOTAL ).amount }" />
									<b><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${total ne 0 ? total : 0.00 }" /></b></td>
	                        </tr>
	                        <tr>
	                        	<c:set var="discount" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.CASH_DISCOUNT ).amount }" />
	                        	<fmt:formatNumber var="discountP" maxFractionDigits="2" value="${total gt 0 ? -discount * 100.0 / total : 0 }"/>
	                            <th colspan="7">Discount (${discountP}):</th>
	                            <td class="text-right"><b>${discount gt 0 or discount lt 0 ? discount : 0.00 }</b></td>
	                        </tr>
	                        <tr>
	                            <th colspan="7">CGST</th>
	                            <td id="cgst" class="text-right"><c:set var="CGST" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.CGST ).amount }" />
									<b><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${CGST gt 0 ? CGST : 0.00 }" /></b></td>
	                        </tr>
	                        <tr>
	                            <th colspan="7">SGST</th>
	                            <td id="sgst" class="text-right"><c:set var="SGST" value="${billDto.getAmountDetailByTypeId( ApplicationConstants.SGST ).amount }" />
									<b><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${SGST gt 0 ? SGST : 0.00 }" /></b></td>
	                        </tr>
	                        <tr>
	                            <th colspan="7" >Grand Total:</th>
	                            <td class="text-right" id="grandTotal"><b>${billDto.total}</b></td>
	                        </tr>
                    </tbody>
                </table>
            </div>
            
        </div>
        <div class="row amountInWordDiv">
        	<div class="col-xs-12">Amount in Words: <div id="amountInWords2"></div></div>
        </div>
		<div id="demo" class="container">
  			<input id="num" type="text" class="form-control hide" value="${billDto.total}"><br>
  			<input id="trans" type="button" value="Convert to words" class="btn btn-danger hide"><br>
  			<div class="well hide" id="amountInWords"></div>
  		</div>
	</section>
</div>