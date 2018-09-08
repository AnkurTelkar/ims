<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Items List
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Show All Items</span></li>
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
                  <th>Category</th>
                  <th>Brand</th>
                  <!-- <th>Pack Unit</th> -->
                  <th>Track Unit</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${itemDtoList}" var="itemDto"  varStatus="status">
                    <tr>
			<td align="center">${status.count}</td>
                        <td>${itemDto.itemCode}</td>
			<td>${itemDto.itemName}</td>
                        <td>${itemDto.category.categoryName}</td>
                        <td>${itemDto.brand.brandName}</td>
                        <%-- <td>${itemDto.packUnit.unitName }</td> --%>
                        <td>${itemDto.trackUnit.unitName }</td>
                        <td>${itemDto.status eq 1 ? "Active" : "Inactive"}</td>
                        <td class="text-center">
                        <c:set var="title" value="SKU Details for: ${itemDto.itemName}"/>
                            <a title="List SKUs for: ${itemDto.itemName}" href="javascript: openModal( this, '<c:url value='/inventory/items/${itemDto.itemId}/listSkus.htm'/>', '<c:out value="${title}" />' )" class="btn btn-dropbox" role="button"><i class="fa fa-list-alt" aria-hidden="true"></i></a>
                            <a title="View Item: ${itemDto.itemName}" href="<c:url value='/inventory/items/${itemDto.itemId}/viewItem.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                            <a title="Edit Item: ${itemDto.itemName}" href="<c:url value='/inventory/items/${itemDto.itemId}/editItem.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
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
                  <th>Category</th>
                  <th>Brand</th>
                  <!-- <th>Pack Unit</th> -->
                  <th>Track Unit</th>
                  <th>Status</th>
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