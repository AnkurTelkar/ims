<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
              <div class="box">
                <!-- /.box-header -->
                <div class="box-body table-responsive">
                  <table id="invSkuDetailsTable" class="table table-bordered table-striped ellipsis">
                    <thead>
	                    <tr>
							<th>#</th>
		                    <th>Barcode</th>
		                    <th>Code</th>
		                    <th>Item</th>
		                    <th>Desc</th>
		                    <th>Cost</th>
		                    <th>GST(%)</th>
		                    <th>HSN</th>
		                    <!-- <th>Ext. Cost</th> -->
		                    <th>MRP</th>
		                    <th>S. Price</th>
		                    <th>qty.</th>
		                    <th>Threshold</th>
		                    <th>Actions</th>
	                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${skuDtoList}" var="skuDto"  varStatus="status">
                        <tr>
                            <td align="center">${status.count}</td>
                            <td>${skuDto.barcode}</td>
                            <td>${skuDto.skuCode}</td>
                            <td>${skuDto.item.itemName}</td>
                            <td>${skuDto.description}</td>
                            <td>${skuDto.cost}</td>
                            <td>${skuDto.gst}</td>
                            <td>${skuDto.hsn}</td>
                            <%-- <td>${skuDto.extendedCost()}</td> --%>
                            <td>${skuDto.retailPrice}</td>
                            <td>${skuDto.sellingPrice()}</td>
                            <td class="text-right">${skuDto.quantity}</td>
                            <td class="text-right">${skuDto.threshold}</td>
                            <td class="text-center">
                                <a  title="View Item: ${skuDto.skuCode}" href="<c:url value='/inventory/skus/${skuDto.skuId}/viewSku.htm'/>" class="btn btn-primary" role="button"><i class="fa fa-list" aria-hidden="true"></i></a>
                                <a title="Edit Item: ${skuDto.skuCode}" href="<c:url value='/inventory/skus/${skuDto.skuId}/editSku.htm'/>" class="btn btn-info" role="button"><i class="fa fa-edit" aria-hidden="true"></i></a>
                                <!--<a href="#" class="btn btn-warning" role="button"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
						<tr>
							<th>#</th>
                      		<th>Barcode</th>
                      		<th>Code</th>
                      		<th>Item</th>
                      		<th>Desc</th>
                      		<th>Cost</th>
                      		<th>GST(%)</th>
                      		<th>HSN</th>
                      		<!-- <th>Ext. Cost</th> -->
                      		<th>MRP</th>
                      		<th>S. Price</th>
                      		<th>qty.</th>
                      		<th>Threshold</th>
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