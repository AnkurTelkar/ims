<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${purchaseOrder.id gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Purchase Order</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-bar-chart-o"></i> Purchase Order</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Purchase Order</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">Purchase Order</h3>
            </div>
            <c:url var="addPurchaseOrder" value="/inventory/purchaseOrders/addPurchaseOrder.htm"/>
            <form:form method="post" class="form-horizontal" action="${addPurchaseOrder}" modelAttribute="purchaseOrder" >
                <div class="box-body">
                    <div class="form-group">
                        <div class='col-sm-8'>
                            <h3><label class=""></label></h3>
                            <div class="form-group">
                                <h5><label for="purchaseNumber" class="col-sm-2">PO #:</label></h5>
                                <div class="col-sm-6">
                                	<input type="hidden" class="form-control" id="id" name="id" value="${purchaseOrder.id}">
                                    <input type="hidden" class="form-control" id="type" name="type" value="1">
                                    <input type="hidden" class="form-control" id="status" name="status" value="1">
                                    <input step="any" type="number" class="form-control" id="purchaseNumber" name="purchaseNumber" placeholder="PO No" value="${purchaseOrder.purchaseNumber}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <h5><label for="purchaseOrderDate" class="col-sm-2 ">PO Date:</label></h5>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <input type="text" readonly class="form-control myDatePicker" id="purchaseOrderDate">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <h3><label class="">Vendor:</label></h3>
                            <select class="form-control js-example-basic-single" id="vendorId" name="vendorId" >
                                <option value="-1">None</option>
                                <c:forEach items="${vendors}" var="vendor">
                                    <option value="${vendor.id}"${vendor.id eq purchaseOrder.vendorId ? 'selected' : ''}>${vendor.getDisplayName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <hr>
                        <div class='col-sm-12'>
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th width="2%"><input id="check_all" type="checkbox" ${purchaseOrder.id ge 0 ? 'disabled' : '' }/></th>
                                            <th width="10%">Barcode</th>
                                            <th width="20%">Particular</th>
                                            <th width="8%">Quantity</th>
                                            <th width="8%">Rate</th>
                                            <th width="8%">Cost</th>
                                            <th width="8%">VAT(%)</th>
                                            <th width="8%">Ext. Cost</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%-- <c:forEach items="${purchaseOrder.invPurchaseDetails}" var="purchaseOrderDetail" varStatus="status">
                                        
                                        </c:forEach> --%>
                                        <tr>
                                            <td><input class="case" type="checkbox" ${purchaseOrder.id gt 0 ? 'disabled' : '' }/></td>
                                            <td>
                                                <input type="hidden" name="invPurchaseDetails[${status.count}].id" id="id_${status.count}" value="${purchaseOrderDetail.id}">
                                                <input type="hidden" name="invPurchaseDetails[${status.count}].purchaseOrderId" id="purchase_order_id_${status.count}" value="${purchaseOrderDetail.purchaseOrderId}">
                                                <input type="hidden" name="invPurchaseDetails[${status.count}].itemSkuId" id="sku_id_${status.count}" value="${purchaseOrderDetail.itemSkuId}" class="skuIds">
                                                 <input type="text" name="invPurchaseDetails[${status.count}].barcode" id="barcode_${status.count}" class="form-control barcode" autocomplete="off" value="${purchaseOrderDetail.getBarcode()}" maxlength="13">
											</td>
											<td>
                                                <input type="text" data-type="sku" name="sku[]" id="sku_${status.count}" class="form-control autocomplete_txt" autocomplete="off" value="${purchaseOrderDetail.getItemSkuDisplayName()}">
                                            </td>
                                            <td><input step="any" type="number" name="invPurchaseDetails[${status.count}].quantity" id="quantity_${status.count}" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" value="${purchaseOrderDetail.quantity}"></td>
                                            <td><input step="any" type="number" name="invPurchaseDetails[${status.count}].rate" id="rate_${status.count}" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" value="${purchaseOrderDetail.rate}"></td>
                                            <td><input step="any" type="number" name="invPurchaseDetails[${status.count}].cost" id="cost_${status.count}" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" value="${purchaseOrderDetail.cost}"></td>
                                            <td><input step="any" type="number" name="invPurchaseDetails[${status.count}].vat" id="vat_${status.count}" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" value="${purchaseOrderDetail.vat}" readonly></td>
                                            <td><input step="any" type="number" name="extcost[]" id="extcost_${status.count}" class="form-control extendedCost" autocomplete="off" onkeypress="return IsNumeric(event);" readonly value="${purchaseOrderDetail.quantity * purchaseOrderDetail.cost}"></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class='col-sm-6'>
                            <button class="btn btn-danger delete" type="button"><i class="fa fa-remove"></i> Delete</button>
                            <button class="btn btn-success add-purchase-detail" type="button"><i class="fa fa-plus"></i> Add More</button>
                        </div>

                        <div class='col-sm-6'>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label">Total:</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input step="any" type="number" class="form-control" id="total" name="total" placeholder="Total" onkeypress="return IsNumeric(event);" value="${purchaseOrder.total}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label">Tax</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <input step="any" type="number" class="form-control" id="tax" placeholder="Tax" onkeypress="return IsNumeric(event);" value="0">
                                        <div class="input-group-addon">%</div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label">Tax Amount:</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input step="any" type="number" class="form-control" id="tax" name="tax" placeholder="Tax" onkeypress="return IsNumeric(event);" value="${purchaseOrder.tax}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label">Total:</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input step="any" type="number" class="form-control" id="totalAftertax" placeholder="Total" onkeypress="return IsNumeric(event);" value="${purchaseOrder.total + purchaseOrder.tax}">
                                    </div>
                                </div>
                            </div>
                            <!--                       <div class="form-group">
                                                            <label for="name" class="col-sm-4 control-label">Amount Paid:</label>
                                                            <div class="col-sm-8">
                                                                <div class="input-group">
                                                                    <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                                                    <input step="any" type="number" class="form-control" id="amountPaid" placeholder="Amount Paid" onkeypress="return IsNumeric(event);" >
                                                                </div>
                                                            </div>
                                                        </div>
                            
                                                        <div class="form-group">
                                                            <label for="name" class="col-sm-4 control-label">Amount Due:</label>
                                                            <div class="col-sm-8">
                                                                <div class="input-group">
                                                                    <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                                                    <input step="any" type="number" class="form-control amountDue" id="amountDue" placeholder="Amount Due" onkeypress="return IsNumeric(event);" >
                                                                </div>
                                                            </div>                                        
                                                        </div>-->

                        </div>
                    </div>
                    <div class='form-group'>
                        <div class='col-sm-12'>
                            <h2><label for="description">Description:</label></h2>
                            <textarea class="form-control" rows='5' id="description" name="description" placeholder="Your Description"></textarea>
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