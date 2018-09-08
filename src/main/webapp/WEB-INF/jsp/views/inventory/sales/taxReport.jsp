<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Tax Report
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Tax Report</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Tax Report</span></li>
        </ol>
    </section>

    	<input type="hidden" name="startDate" id="startDate" value="<fmt:formatDate type ="date" pattern="yyyy-MM-dd" value = "${startDate}" />">
		<input type="hidden" name="endDate" id="endDate" value="<fmt:formatDate type ="date" pattern="yyyy-MM-dd" value = "${endDate}" />">
    
    <section class="content">
    <div class="row">
    	<div class="col-xs-12">
			<div class="box">
				<div class="box-body">
	                <div class="input-group pull-right">
	                	<a id="print" href="#" onclick="printTaxReport();" target="_blank" class="btn btn-primary"><i class="fa fa-print"></i> Print</a>
	                  	<button type="button" class="btn btn-default pull-right" id="daterange-btn">
	                    	<span><i class="fa fa-calendar"></i> ${dateCriteria }</span>
	                    	<i class="fa fa-caret-down"></i>
	                  	</button>
	                </div>
				</div>
			</div>
    	</div>
    </div>
    <input type="hidden" value="${dateCriteria }" id="dateCriteria" />
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <!-- /.box-header -->
            <div class="box-body table-responsive">
              <table id="example1" class="table table-bordered table-striped ellipsis">
                <thead>
                <tr>
                  <th>#</th>
                  <th>Customer</th>
                  <th>GSTIN</th>
                  <th>Voucher Count</th>
                  <th>Taxable Value</th>
                  <th>CGST</th>
                  <th>SGST</th>
                  <th>Total Tax</th>
                  <th>Total Amount</th>
                </tr>
                </thead>
                <tbody>
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
                  <tr>
                  <th>#</th>
                  <th>Customer</th>
                  <th>GSTIN</th>
                  <th>Voucher Count</th>
                  <th>Taxable Value</th>
                  <th>CGST</th>
                  <th>SGST</th>
                  <th>Total Tax</th>
                  <th>Total Amount</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
		<div class="row">
			<div class="col-xs-6">
	          	<div class="box">
	            	<div class="box-body table-responsive">
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
	        </div>
		</div>
    </section>
    <!-- /.content -->
  </div>


<script>
function postChangeDate( start, end ) {
	$('#daterange-btn span').html(start.toDateString() + ' - ' + end.toDateString());
	location.href = "../pointOfSale/taxReport.htm?start=" +start.toDateString()  + "&end=" + end.toDateString() ;
}

function printTaxReport() {
	var startDate = new Date( $( "#startDate" ).val() ).toDateString();
	var endDate = new Date( $( "#endDate" ).val() ).toDateString();
	$("#print").attr("href", "../pointOfSale/printTaxReport.htm?start=" + startDate + "&end=" + endDate );
	return false;
}
</script>