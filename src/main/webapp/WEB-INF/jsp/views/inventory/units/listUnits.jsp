<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Units List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Unit</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Units</span></li>
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
                  <th>Name</th>
                  <th>Amount</th>
                  <th>Basic Unit</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${unitDtoList}" var="unitDto"  varStatus="status">
                    <tr>
						<td align="center">${status.count}</td>
						<td>${unitDto.unitName}</td>
                        <td>${unitDto.amount}</td>
                        <%-- <td class="text-capitalize">${unitDto.getDisplayName()}</td> --%>
                        <td class="text-capitalize">${unitDto.measuringUnit.name}</td>
                        <td class="text-center">
                            <a title="View Unit: ${unitDto.unitName}" href="<c:url value='/inventory/units/${unitDto.unitId}/viewUnit.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Unit: ${unitDto.unitName}" href="<c:url value='/inventory/units/${unitDto.unitId}/editUnit.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
<!--                            <a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Name</th>
                  <th>Amount</th>
                  <th>Basic Unit</th>
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


