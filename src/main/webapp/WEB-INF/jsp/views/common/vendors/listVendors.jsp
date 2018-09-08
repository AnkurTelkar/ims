<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Vendors List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Vendor</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Vendors</span></li>
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
                  <th>Vendor No.</th>
                  <th>Vendor Name</th>
                  <th>Description</th>
                  <th>Address</th>
                  <th>City</th>
                  <th>State</th>
                  <th>GSTIN</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${vendorsList}" var="vendor"  varStatus="status">
                    <tr>
						<td align="center">${status.count}</td>
						<td>${vendor.vendorCode}</td>
						<td>${vendor.vendorName}</td>
                        <td>${vendor.description}</td>
                        <td>${vendor.address}</td>
                        <td>${vendor.city}</td>
                        <td>${vendor.state}</td>
                        <td>${vendor.gstNo}</td>
                        <td class="text-center">
                            <a title="View Vendor: ${vendor.vendorName}" href="<c:url value='/vendors/${vendor.id}/viewVendor.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Vendor: ${vendor.vendorName}" href="<c:url value='/vendors/${vendor.id}/editVendor.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
<!--                            <a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Vendor No.</th>
                  <th>Vendor Name</th>
                  <th>Description</th>
                  <th>Address</th>
                  <th>City</th>
                  <th>State</th>
                  <th>GSTIN</th>
                  <th>Actions</th>
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


