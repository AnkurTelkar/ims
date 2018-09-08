<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Customers List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Customer</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Customers</span></li>
        </ol>
    </section>
	<c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-info" style="margin-bottom: 0!important;">
                <h5><em>${message}</em></h5>
            </div>
        </div>
    </c:if>
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
                  <th>Cust. No.</th>
                  <th>Name</th>
                  <th>Desc.</th>
                  <th>Address</th>
                  <th>GSTIN</th>
                  <th style="width: 20%;">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${customersList}" var="customer"  varStatus="status">
                    <tr>
						<td align="center">${status.count}</td>
						<td>${customer.customerCode}</td>
						<td>${customer.customerName}</td>
                        <td>${customer.description}</td>
                        <td>${customer.address}, ${customer.city}, ${customer.state}</td>
                        <td>${customer.gstNo}</td>
                        <td class="text-center">
                        	<a title="View All Bills: ${customer.customerName}" href="<c:url value='/inventory/sales/pointOfSale/${customer.customerId}/listInvoices.htm'/>" class="btn btn-info" role="button"><i class="fa fa-list-alt" aria-hidden="true"></i></a>
                            <a title="View Customer: ${customer.customerName}" href="<c:url value='/customers/${customer.customerId}/viewCustomer.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Customer: ${customer.customerName}" href="<c:url value='/customers/${customer.customerId}/editCustomer.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
                            
<!--                            <a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Cust. No.</th>
                  <th>Name</th>
                  <th>Desc.</th>
                  <th>Address</th>
                  <th>GSTIN</th>
                  <th style="width: 20%;">Actions</th>
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


