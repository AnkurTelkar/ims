<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="label" value="Update" />
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>Add/Update SKUs</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>Add/Update SKUs</span></li>
        </ol>
    </section>
    	<div class="pad margin no-print" style="display:none">
            <div class="callout callout-danger" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-check"></i> <em><span id="messageFH">
                ${message}
                </span></em></h5>
            </div>
        </div>
        <c:if test="${skuDtoList.size() eq 0}">
        <div class="pad margin no-print">
            <div class="callout callout-danger" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-times"></i> <em><span id="messageFH">
         			Please create default SKU First
                </span></em></h5>
            </div>
        </div>
        </c:if>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              	<h3 class="box-title">${itemDto.itemCode} - ${itemDto.itemName } - ${itemDto.brand.brandName } - ${itemDto.category.categoryName }</h3>
            </div>
            
                <c:url var="addUpdateSkus" value="/inventory/items/addUpdateSkus.htm"/>
                <form:form method="post" class="form-horizontal" action="${addUpdateSkus}" modelAttribute="skuDtoList" >
                	<input type="hidden" id="itemId" data-identifier="itemId" value="${itemDto.itemId}">
                	<c:if test="${skuDtoList.size() gt 0}">
                    <div class="box-body">
                    	<div class="col-xs-12 well">
            				<label for="skuCode" class="col-xs-1">SKU Code</label>
            				<label for="barcode" class="col-xs-2">Bar code</label>
            				<label for="description" class="col-xs-2">Desc.</label>
            				<label for="cost" class="col-xs-1">Cost</label>
            				<label for="gst" class="col-xs-1">GST (%)</label>
            				<label for="mrp" class="col-xs-1">MRP</label>
            				<label for="discount" class="col-xs-1">Discount (Div.)</label>
            				<label for="sellingPrice" class="col-xs-1">Selling Price</label>
            				<label for="qty" class="col-xs-1">Qty.</label>
            				<label for="threshold" class="col-xs-1">Thresh.</label>
           				</div>
           				<div class="col-xs-12 well" id="skuListDiv">
           				
	            		<c:forEach items="${skuDtoList}" var="skuDto" varStatus="status">
		          				<div class="form-group">
		           					<div class="col-xs-1">
		           						<input type="hidden" id="skuId" data-identifier="skuId" value="${skuDto.skuId}">
		                                <input type="text" class="form-control skuCode" data-identifier="skuCode" value="${skuDto.skuCode}" placeholder="Sku Code" required maxlength="13" readonly>
		           					</div>
		           					<div class="col-xs-2">
		                               <input type="text" class="form-control barcode" data-identifier="barcode" value="${skuDto.barcode}" placeholder="Barcode" required maxlength="13">
		           					</div>
		           					<div class="col-xs-2">
		                                <input type="text" class="form-control description" data-identifier="description" value="${skuDto.description}" placeholder="Description" maxlength="50">
		           					</div>
		           					<div class="col-xs-1">
		                                <input type="number" class="form-control cost" data-identifier="cost" value="${skuDto.cost}" placeholder="Cost" required max="100000">
		           					</div>
		           					<div class="col-xs-1">
		                                <input type="number" class="form-control gst" data-identifier="gst" value="${skuDto.gst}" placeholder="GST" required max="100">
		                            </div>
		                            <div class="col-xs-1">
		                                <input type="number" class="form-control retailPrice" data-identifier="retailPrice" value="${skuDto.retailPrice}" placeholder="Retail Price" required max="100000" step="0.01">
		                            </div>
		                            <div class="col-xs-1">
		                                <input type="number" class="form-control discount" data-identifier="discount" value="${skuDto.discount}" placeholder="Discount (Div.)" required max="100000" step="0.01" onblur="populateSellingPrice( this );">
		                            </div>
		                            <div class="col-xs-1">
		                                <input type="number" class="form-control sellingPrice" data-identifier="sellingPrice" value="${skuDto.sellingPrice()}" placeholder="Selling Price" required max="100000" readonly>
		                            </div>
		                            <div class="col-xs-1">
		                                <input type="number" class="form-control quantity" data-identifier="quantity" value="${skuDto.quantity}" placeholder="Quantity" required max="100000">
		                            </div>
		                            <div class="col-xs-1">
		                                <input type="number" class="form-control threshold" data-identifier="threshold" value="${skuDto.threshold}" placeholder="Threshold" required max="1000">
		                            </div>
								</div>
	            		</c:forEach>
            		</div>
           				<button class="btn btn-success" id="add-sku-row" type="button"><i class="fa fa-plus"></i></button>
            		</div>
                    <div class="box-footer text-right">
                        <button type="button" id="update" class="btn btn-info">${label}</button>
                        <button type="submit" class="btn btn-default">Cancel</button>
                    </div>
                    </c:if>
                </form:form>
            </div>
    </section>
</div>
