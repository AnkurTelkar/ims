<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    		<div class="col-xs-12 text-center">
    			<h3>K.N Sales</h3>
    		</div>
    	</div>
      <div class="row">
        <div class="col-xs-12">
        	<table class="table table-striped">
                <tbody style="min-height: 500px;">
                	<tr style="border: 1px solid;">
						<td colspan="4" style="border: 1px solid;">Voucher Details</td>
						<td colspan="5" style="border: 1px solid;">${dateCriteria }</td>
					</tr>
                	<tr style="border: 1px solid;">
	                  <td>#</td>
	                  <td>Customer</td>
	                  <td>GSTIN</td>
	                  <td>Voucher Count</td>
	                  <td>Taxable Value</td>
	                  <td>CGST</td>
	                  <td>SGST</td>
	                  <td>Total Tax</td>
	                  <td>Total Amount</td>
	                </tr>
	                <c:set var="voucherCountTotal" value="${0}"/>
	                <c:set var="taxableValueTotal" value="${0}"/>
	                <c:set var="cgstTotal" value="${0}"/>
	                <c:set var="sgstTotal" value="${0}"/>
	                <c:set var="total" value="${0}"/>
	                <c:forEach items="${taxReportDetails}" var="taxReportDetail" varStatus="status">
		                <c:set var="voucherCountTotal" value="${voucherCountTotal + taxReportDetail[2]}" />
		                <c:set var="taxableValueTotal" value="${taxableValueTotal + taxReportDetail[3]}" />
		                <c:set var="cgstTotal" value="${cgstTotal + taxReportDetail[4]}" />
		                <c:set var="sgstTotal" value="${sgstTotal + taxReportDetail[5]}" />
	                 	<c:set var="total" value="${total + taxReportDetail[6]}" />
		                <tr>
							<td align="center">${status.count }</td>
							<td>${taxReportDetail[0]}</td>
							<td class="text-right">${taxReportDetail[1]}</td>
							<td class="text-right">${taxReportDetail[2]}</td>
							<td class="text-right">${taxReportDetail[3]}</td>
							<td class="text-right">${taxReportDetail[4]}</td>
							<td class="text-right">${taxReportDetail[5]}</td>
							<td class="text-right">${ taxReportDetail[4] + taxReportDetail[5]}</td>
							<td class="text-right">${taxReportDetail[6]}</td>
							<%-- <td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${taxReportDetail[1]}" /></td>
							<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${taxReportDetail[2]}" /></td>
							<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${taxReportDetail[3]}" /></td> --%>
						</tr>
	                </c:forEach>
                </tbody>
                <tfoot>
					<tr>
						<th class="text-right" colspan="3">Total:</th>
	                  	<th class="text-right">${voucherCountTotal }</th>
	                  	<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${taxableValueTotal }" /></th>
	                  	<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${cgstTotal }" /></th>
	                  	<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${sgstTotal }" /></th>
	                  	<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${ cgstTotal + sgstTotal }" /></th>
	                  	<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${ total }" /></th>
					</tr>
				</tfoot>
			</table>          
        </div>
      </div>
      <div class="row">
			<div class="col-xs-6">
             		<table id="example2" class="table table-bordered table-striped ellipsis">
               		<tbody>
               			<tr>
               				<th>${taxReportSummary[0][0] }</th>
               				<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${ total }" /></th>
               			</tr>
               			<tr>
               				<th>${taxReportSummary[0][2] }</th>
               				<th class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${taxReportSummary[0][3] }" /></th>
               			</tr>
               			<tr>
               				<th>${taxReportSummary[0][4] }</th>
               				<th class="text-right">${taxReportSummary[0][5] }</th>
               			</tr>
               		</tbody>
               	</table>
	        </div>
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
	} );
	
	window.onload = function () {
	  window.print();
	  setTimeout(function(){window.close();}, 1);
	}
</script>