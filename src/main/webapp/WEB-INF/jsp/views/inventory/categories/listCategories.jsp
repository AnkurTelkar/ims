<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Categories List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Category</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Categories</span></li>
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
                  <th>Code</th>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${categoryDtoList}" var="categoryDto"  varStatus="status">
                    <tr>
						<td align="center">${status.count}</td>
						<td>${categoryDto.categoryCode}</td>
                        <td>${categoryDto.categoryName}</td>
                        <td>${categoryDto.description}</td>
                        <td class="text-center">
                            <a title="View Category: ${categoryDto.categoryName}" href="<c:url value='/inventory/categories/${categoryDto.categoryId}/viewCategory.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Category: ${categoryDto.categoryName}" href="<c:url value='/inventory/categories/${categoryDto.categoryId}/editCategory.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
<!--                            <a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <th>#</th>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Description</th>
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


