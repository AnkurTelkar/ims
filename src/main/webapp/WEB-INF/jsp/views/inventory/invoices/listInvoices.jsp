<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Invoices List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Invoice</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Invoices</span></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-sm-12">
          <div class="box">
            <!-- /.box-header -->
            <div class="box-body table-responsive">
              <table id="example1" class="table table-bordered table-striped ellipsis">
                <thead>
                <tr>
                  <th style="width: 2%;">#</th>
                  <th style="width: 5%;">Invoice #</th>
                  <th style="width: 25%;">Customer</th>
                  <th style="width: 20%;">Description</th>
                  <th style="width: 10%;">Sub Total</th>
                  <th style="width: 8%;">Tax %</th>
                  <th style="width: 10%;">Tax Amount</th>
                  <th style="width: 10%;">Grand Total</th>
                  <th style="width: 10%;">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${invoicesList}" var="invoice"  varStatus="status">
                    <tr>
			<td align="center">${status.count}</td>
			<td>${invoice.bookNo} - ${invoice.invoiceNumber}</td>
                        <td>${invoice.getCustomer().getDisplayName()}</td>
                        <td>${invoice.description}</td>
                        <td class="text-right">${invoice.subTotal}</td>
                        <td class="text-right">${invoice.getTaxPercentage()}</td>
                        <td class="text-right">${invoice.totalTax}</td>
                        <td class="text-right">${invoice.totalTax + invoice.subTotal}</td>
                        <td class="text-center">
                            <a title="View Invoice: ${invoice.bookNo} - ${invoice.invoiceNumber}" href="<c:url value='/inventory/invoices/${invoice.id}/viewInvoice.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Invoice: ${invoice.bookNo} - ${invoice.invoiceNumber}" href="<c:url value='/inventory/invoices/${invoice.id}/editInvoice.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
<!--                            <a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Invoice #</th>
                  <th>Customer</th>
                  <th>Description</th>
                  <th>Sub Total</th>
                  <th>Tax %</th>
                  <th>Tax Amount</th>
                  <th>Grand Total</th>
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


