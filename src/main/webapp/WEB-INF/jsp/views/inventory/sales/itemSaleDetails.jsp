<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Item Sale Details
      </h1>
      <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item Sale Details</a></li>
            <li class="active"><i class="fa fa-list"></i> <span>Item Sale Details</span></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    <div class="row">
    	<div class="col-xs-12">
			<div class="box">
				<div class="box-body">
	                <div class="input-group pull-right">
	                  <button type="button" class="btn btn-default pull-right" id="daterange-btn">
	                    <span>
	                      <i class="fa fa-calendar"></i> ${dateCriteria }
	                    </span>
	                    <i class="fa fa-caret-down"></i>
	                  </button>
	                </div>
				</div>
			</div>
    	</div>
    </div>
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <!-- /.box-header -->
            <div class="box-body table-responsive">
              <table id="example1" class="table table-bordered table-striped ellipsis">
                <thead>
                <tr>
                  <th>#</th>
                  <th>Item</th>
                  <th>Quantity</th>
                  <th>Discount</th>
                  <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${itemSaleDetails}" var="itemSaleDetail" varStatus="status">
                <tr>
					<td align="center">${status.count }</td>
					<td>${itemSaleDetail[0].description}</td>
					<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${itemSaleDetail[1]}" /></td>
					<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${itemSaleDetail[2]}" /></td>
					<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${itemSaleDetail[3]}" /></td>
				</tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                  <tr>
                  <th>#</th>
                  <th>Item</th>
                  <th>Quantity</th>
                  <th>Discount</th>
                  <th>Price</th>
                </tr>
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


<script>
function postChangeDate( start, end ) {
	$('#daterange-btn span').html(start.toDateString() + ' - ' + end.toDateString());
	location.href = "../pointOfSale/itemSaleDetails.htm?start=" +start.toDateString() + "&end=" + end.toDateString();
}
</script>