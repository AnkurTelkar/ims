<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Sales Summary
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Sales Summary</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Customer's Sales Summary</span></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <!-- /.box-header -->
            <div class="box-body table-responsive">
              <table id="example1" class="table table-bordered table-striped ellipsis">
                <thead>
                <tr>
                	<th colspan="4"></th>
                	<th colspan="3" class="text-center">Payments</th>
                	<th colspan="2"></th>
                </tr>
                <tr>
                  <th>#</th>
                  <th>Customer</th>
                  <th>Count</th>
                  <th>Purchases</th>
                  <th>Check</th>
                  <th>Cash</th>
                  <th>Other</th>
                  <th>On Account</th>
                  <th>Dues</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${saleSummaryDtoList}" var="saleSummaryDto" varStatus="status">
                <tr>
					<td align="center">${status.count }</td>
					<td>${saleSummaryDto.customer.getDisplayName()}</td>
					<td class="text-right">
						<a title="List Invoices" class="btn badge" href="<c:url value='/inventory/sales/pointOfSale/${saleSummaryDto.customer.customerId}/listInvoices.htm'/>">
							<i><fmt:formatNumber value="${saleSummaryDto.totalBills}" pattern="0"/></i>
						</a>
					</td>
					<td class="text-right">${saleSummaryDto.totalPurchaseAmount}</td>
					<td class="text-right">${saleSummaryDto.paidViaCheck}</td>
					<td class="text-right">${saleSummaryDto.paidViaCash}</td>
					<td class="text-right">${saleSummaryDto.paidOther}</td>
					<td class="text-right">${saleSummaryDto.onAccount}</td>
					<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${ saleSummaryDto.getRemainingDues()}" /></td>
				</tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Customer</th>
                  <th>Count</th>
                  <th>Purchases</th>
                  <th>Check</th>
                  <th>Cash</th>
                  <th>Other</th>
                  <th>On Account</th>
                  <th>Dues</th>
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
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>


