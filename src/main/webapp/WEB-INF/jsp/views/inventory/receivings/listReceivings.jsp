<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Receiving List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Receiving</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Receiving</span></li>
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
                  <th style="width: 8%;">receiving #</th>
                  <th style="width: 25%;">Vendor</th>
                  <th style="width: 8%;">Invoice #</th>
                  <th style="width: 10%;">Invoice Date</th>
                  <th style="width: 5%;">Status</th>
                  <th style="width: 20%;">Description</th>
                  <th style="width: 8%;">Total</th>
                  <th style="width: 14%;">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${receivingDtoList}" var="receiving"  varStatus="status">
                    <tr>
						<td align="center">${status.count}</td>
						<td>${receiving.receivingNo}</td>
                        <td>${receiving.getVendor().getDisplayName()}</td>
                        <td >${receiving.invoiceNo}</td>
                        <td ><fmt:formatDate pattern = "dd/MM/yyyy" value = "${receiving.invoiceDate}" /></td>
                        <td >${receiving.getStatusStr()}</td>
                        <td>${receiving.description}</td>
                        <td class="text-right">${receiving.total}</td>
                        <td class="text-center">
                        	<a title="Print Receiving" href="<c:url value='/inventory/receivings/${receiving.receivingId}/printReceiving.htm'/>" id="printReceiving" target="_blank" class="btn btn-primary"><i class="fa fa-print"></i></a>
                            <a title="View Receiving: ${receiving.receivingNo}" href="<c:url value='/inventory/receivings/${receiving.receivingId}/viewReceiving.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Receiving: ${receiving.receivingNo}" href="<c:url value='/inventory/receivings/${receiving.receivingId}/editReceiving.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
<!--                            <a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                        </td>
                    </tr>

                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th style="width: 2%;">#</th>
                  <th style="width: 8%;">receiving #</th>
                  <th style="width: 25%;">Vendor</th>
                  <th style="width: 8%;">Invoice #</th>
                  <th style="width: 10%;">Invoice Date</th>
                  <th style="width: 5%;">Status</th>
                  <th style="width: 20%;">Description</th>
                  <th style="width: 8%;">Total</th>
                  <th style="width: 14%;">Action</th>
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


