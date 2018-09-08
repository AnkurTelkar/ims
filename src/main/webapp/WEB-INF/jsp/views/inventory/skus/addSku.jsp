<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${skuDto.skuId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Item Sku</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item SKU</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Item SKU</span></li>
        </ol>
    </section>
    <c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-info" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-check"></i> <em>${message}</em></h5>
            </div>
        </div>
    </c:if>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Item SKU</h3>
            </div>
                <c:url var="addItem" value="/inventory/skus/addSku.htm"/>
                <form:form method="post" class="form-horizontal" action="${addItem}" modelAttribute="sku" >
                    <div class="box-body">
                    	<div class="form-group">
	                        <label for="itemId" class="col-sm-2 control-label">Item</label>
	                            <div class="col-sm-4">
	                                <select class="form-control" id="item.itemId" name="item.itemId" >
	                                	<option value="-1">Other</option>
	                                    <c:forEach items="${itemDtoList}" var="itemDto">
	                                        <option value="${itemDto.itemId}" ${itemDto.itemId eq skuDto.item.itemId ? 'selected' : ''}>${itemDto.getItemName()}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            <label for="description" class="col-sm-2 control-label">Description</label>
                            <div class="col-sm-4">
                                <form:errors path="description" cssClass="text-red" />
                                <input type="text" class="form-control" id="description" name="description" value="${skuDto.description}" placeholder="Description" maxlength="50">
                            </div>
						</div>
                        <div class="form-group">
                        <label for="barcode" class="col-sm-2 control-label">Barcode</label>
                            <div class="col-sm-4">
                                <form:errors path="barcode" cssClass="text-red" />
                                <input type="hidden" id="skuId" name="skuId" value="${skuDto.skuId}">
                                <input type="text" class="form-control" id="barcode" name="barcode" value="${skuDto.barcode}" placeholder="Barcode" required maxlength="13">
                            </div>
                            <label for="skuCode" class="col-sm-2 control-label">Sku Code</label>
                            <div class="col-sm-4">
                                <form:errors path="skuCode" cssClass="text-red" />
                                
                                <input type="text" class="form-control" id="skuCode" name="skuCode" value="${skuDto.skuCode}" placeholder="Sku Code" required maxlength="13">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cost" class="col-sm-2 control-label">Cost</label>
                            <div class="col-sm-4">
                                <form:errors path="cost" cssClass="text-red" />
                                <input type="number" class="form-control" id="cost" name="cost" value="${skuDto.cost}" placeholder="Cost" required max="100000" step="0.0001">
                            </div>
                            <label for="gst" class="col-sm-2 control-label">GST(%)</label>
                            <div class="col-sm-4">
                                <form:errors path="gst" cssClass="text-red" />
                                <input type="number" class="form-control" id="gst" name="gst" value="${skuDto.gst}" placeholder="GST" required max="100">
                            </div>
                        </div>
                        <%-- <div class="form-group">
                        <label for="extendedCost" class="col-sm-offset-6 col-sm-2 control-label">Extended Cost</label>
                            <div class="col-sm-4">
                                <form:errors path="extendedCost" cssClass="text-red" />
                                <input type="number" class="form-control" id="extendedCost" name="extendedCost" value="${skuDto.extendedCost()}" placeholder="Ext. Cost" readonly>
                            </div>
                        </div> --%>
                        <div class="form-group">
                        	<label for="retailPrice" class="col-sm-2 control-label">MRP</label>
                            <div class="col-sm-4">
                                <form:errors path="retailPrice" cssClass="text-red" />
                                <input type="number" class="form-control" id="retailPrice" name="retailPrice" value="${skuDto.retailPrice}" placeholder="Retail Price" required max="100000" step="0.0001">
                            </div>
                            <label for="discount" class="col-sm-2 control-label">Discount (Div.)</label>
                            <div class="col-sm-4">
                                <form:errors path="discount" cssClass="text-red" />
                                <input type="number" class="form-control" id="discount" name="discount" value="${skuDto.discount}" placeholder="Discount (Div.)" required max="100000" step="0.0001">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label for="sellingPrice" class="col-sm-2 control-label">Selling Price</label>
                            <div class="col-sm-4">
                                <form:errors path="sellingPrice" cssClass="text-red" />
                                <input type="number" class="form-control" id="sellingPrice" name="sellingPrice" value="${skuDto.sellingPrice()}" placeholder="Selling Price" readonly step="0.0001">
                            </div>
                            <label for="quantity" class="col-sm-2 control-label">Quantity</label>
                            <div class="col-sm-4">
                                <form:errors path="quantity" cssClass="text-red" />
                                <input type="number" class="form-control" id="quantity" name="quantity" value="${skuDto.quantity}" placeholder="Quantity" required max="100000" ${skuDto.skuId > 0 ? "readonly" : ""} step="0.01">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="hsn" class="col-sm-2 control-label">HSN</label>
                            <div class="col-sm-4">
                                <form:errors path="quantity" cssClass="text-red" />
                                <input type="number" class="form-control" id="hsn" name="hsn" value="${skuDto.hsn}" placeholder="HSN">
                            </div>
                            <label for="threshold" class="col-sm-2 control-label">Threshold</label>
                            <div class="col-sm-4">
                                <form:errors path="threshold" cssClass="text-red" />
                                <input type="number" class="form-control" id="threshold" name="threshold" value="${skuDto.threshold}" placeholder="Threshold" required max="1000" step="0.01">
                            </div>
                        </div>
                    </div>
                    <div class="box-footer text-right">
                        <button type="submit" class="btn btn-info">${label}</button>
                        <button type="submit" class="btn btn-default">Cancel</button>
                    </div>
                </form:form>
            </div>
    </section>
</div>
