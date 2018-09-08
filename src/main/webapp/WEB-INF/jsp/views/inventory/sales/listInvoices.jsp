<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.ims.ApplicationConstants" %>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Invoice Details ${customerDisplayName }
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Sales Summary</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Invoice Details</span></li>
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
                  <th>#</th>
                  <th>Bill No.</th>
                  <th>Status</th>
                  <th>Total</th>
                  <th>Paid</th>
                  <th>Balance</th>
                  <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${invoiceList}" var="invoice" varStatus="status">
                	<c:set var="statusStr" value="${invoice.getStatusStr() }"></c:set>
					<c:set var="rowId" value="row_${status.count }"></c:set>
					<c:set var="balance" value="${ invoice.status == ApplicationConstants.STATUS_VOID ? 0.00 : invoice.total - invoice.getPaidAmount()}"></c:set>
	                <tr id="${rowId }">
						<td align="center">${status.count }</td>
						<td>${invoice.billNo}</td>
						<td class="statusStr">
							<span class="label ${invoice.status eq ApplicationConstants.STATUS_VOID ? 'label-danger' : (invoice.status eq ApplicationConstants.STATUS_DRAFT ? 'label-warning' : (invoice.status eq ApplicationConstants.STATUS_REFUND ? 'label-success' : 'label-info'))}">${statusStr}</span>
						</td>
						<td class="text-right">${invoice.total}</td>
						<td class="text-right paid">${invoice.getPaidAmount()}</td>
						<td class="text-right balance">${balance}</td>
						<td class="action">
							<c:if test="${statusStr ne 'Void' and balance ne 0}">
	                            <a class="btn btn-primary" title="Finalize By Cash" href="javascript: updateBill( ${ApplicationConstants.PAYMENT_TYPE_CASH}, '${rowId }', ${invoice.billId } );">Finalize By Cash</a>
	                            <a class="btn btn-info" title="Finalize By Check" href="javascript: updateBill( ${ApplicationConstants.PAYMENT_TYPE_CHECK}, '${rowId }', ${invoice.billId } );">Finalize By Check</a>
							</c:if>
						</td>
					</tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Bill No.</th>
                  <th>Status</th>
                  <th>Total</th>
                  <th>Paid</th>
                  <th>Balance</th>
                  <th>Action</th>
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


