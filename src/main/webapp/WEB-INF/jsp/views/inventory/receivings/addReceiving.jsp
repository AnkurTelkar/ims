<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.ims.ApplicationConstants"%>

<c:choose>
    <c:when test="${receivingDto.receivingId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Receiving</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-cart-arrow-down"></i> Receiving</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Receiving</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">Receiving</h3>
            </div>
            <c:url var="addReceiving" value="/inventory/receivings/addReceiving.htm"/>
            <form:form method="post" class="form-horizontal" action="${addReceiving}" modelAttribute="receivingDto" >
                <div class="box-body">
                    <div class="form-group">
                        <div class='col-sm-8'>
                            <div class="form-group">
                                <h5><label for="receivingNo" class="col-sm-2">Rec. #:</label></h5>
                                <div class="col-sm-6">
                                	<input type="hidden" class="form-control" id="receivingId" name="receivingId" value="${receivingDto.receivingId}">
                                    <input step="any" type="number" class="form-control" id="receivingNo" name="receivingNo" placeholder="Rec. No" value="${receivingDto.receivingNo}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <h5><label for="invoiceDate" class="col-sm-2 ">Invoice Date:</label></h5>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <input type="text" readonly class="form-control myDatePicker" id="invoiceDate" name="invoiceDate" value="<fmt:formatDate pattern = "dd/MM/yyyy" value = "${receivingDto.invoiceDate}" />">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
		                    	<h5><label for="invoiceNo" class="col-sm-2">Invoice #:</label></h5>
		                        <div class="col-sm-6">
		                            <input step="any" type="text" class="form-control" id="invoiceNo" name="invoiceNo" placeholder="Invoice No" value="${receivingDto.invoiceNo}">
		                        </div>
		                    </div>
                        </div>
                        <div class="col-sm-4">
                            <h3><label class="">Vendor:</label></h3>
                            <select class="form-control js-example-basic-single" id="vendorId" name="vendor.id" >
                                <option value="-1">None</option>
                                <c:forEach items="${vendorDtoList}" var="vendor">
                                    <option value="${vendor.id}" ${vendor.id eq receivingDto.vendor.id ? 'selected' : ''}>${vendor.getDisplayName()}</option>
                                </c:forEach>
                            </select>
                            <h5><label for="status">Status:</label></h5>
	                        <select class="form-control js-example-basic-single" id="status" name="status" >
                                <option value="${ApplicationConstants.STATUS_FINALIZE }" ${receivingDto.status eq ApplicationConstants.STATUS_FINALIZE ? 'selected' : ''}>Finalize</option>
                                <option value="${ApplicationConstants.STATUS_RETURN }" ${receivingDto.status eq ApplicationConstants.STATUS_RETURN ? 'selected' : ''}>Debit Note</option>
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
                                            <th width="1%"><input id="check_all" type="checkbox" ${receivingDto.receivingId gt 0 ? 'disabled' : '' }/></th>
                                            <th width="12%">Barcode</th>
                                            <th width="15%">Particular</th>
                                            <th width="8%">Qty</th>
                                            <th width="8%">Cost Each</th>
                                            <th width="8%">MRP</th>
                                            <th width="8%">Selling Price</th>
                                            <th width="6%">GST(%)</th>
                                            <th width="8%">GST</th>
                                            <th width="6%">Disc.(%)</th>
                                            <th width="8%">Disc.</th>
                                            <th width="10%">Ext. Cost</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${receivingDto.receivingItems}" var="receivingItem" varStatus="status">
                                        <tr>
                                            <td>
                                            	<input class="case" type="checkbox" ${receivingDto.receivingId gt 0 ? 'disabled' : '' }/>
                                            	<input type="hidden" name="receivingItems[${status.count-1}].receivingItemId" id="receiving_item_id_${status.count-1}" value="${receivingItem.receivingItemId}">
                                                <%-- <input type="hidden" name="receivingItems[${status.count-1}].receiving.receivingId" id="receiving_id_${status.count-1}" value="${receivingItem.receivingId}"> --%>
                                                <input type="hidden" name="receivingItems[${status.count-1}].sku.skuId" id="sku_id_${status.count-1}" value="${receivingItem.sku.skuId}" class="skuIds">
                                            </td>
                                            <td><input type="text" name="receivingItem[${status.count-1}].sku.barcode" id="barcode_${status.count-1}" class="form-control barcode" autocomplete="off" value="${receivingItem.sku.barcode}" maxlength="13"></td>
											<td>
                                                <input type="text" data-type="sku" name="sku[]" id="sku_${status.count-1}" class="form-control autocomplete_txt" autocomplete="off" value="${receivingItem.getSkuDisplayName()}">
                                            </td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].quantity" id="quantity_${status.count-1}" class="form-control changesNo quantity isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="${receivingItem.quantity}"></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].cost" id="cost_${status.count-1}" class="form-control changesNo isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="${receivingItem.cost}" readonly></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].sku.retailPrice" id="retailprice_${status.count-1}" class="form-control isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="${receivingItem.sku.retailPrice}" readonly></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].sku.sellingPrice" id="sellingprice_${status.count-1}" class="form-control isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="${receivingItem.sku.sellingPrice}" readonly></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].gst" id="gst_${status.count-1}" class="form-control changesNo isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="${receivingItem.gst}" readonly></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].gst_amount" id="gst_amount_${status.count-1}" class="form-control changesNo gst" autocomplete="off" onkeypress="return IsNumeric(event);" value="" readonly></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].discount" id="discount_${status.count-1}" class="form-control changesNo isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="${receivingItem.discount}" readonly></td>
                                            <td><input step="any" type="number" name="receivingItems[${status.count-1}].discount_amount" id="discount_amount_${status.count-1}" class="form-control discount-amount isEditable" autocomplete="off" onkeypress="return IsNumeric(event);" value="" readonly></td>
                                            <td><input step="any" type="number" name="extcost[]" id="extcost_${status.count-1}" class="form-control extendedCost" autocomplete="off" onkeypress="return IsNumeric(event);" readonly value=""></td>
                                        </tr>
									</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class='col-sm-12'>
                            	<div class="text-right">
	                            	<i><b>Note:</b>You can Click MRP, Selling Price, Cost, GST or Discount to modify its value.</i>
                            	</div>
                    		</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class='col-sm-6'>
                            <button class="btn btn-danger delete" type="button"><i class="fa fa-remove"></i> Delete</button>
                            <button class="btn btn-success add-receiving-detail" type="button"><i class="fa fa-plus"></i> Add More</button>
                        </div>
                        <div class='col-sm-6'>
                            <div class="form-group">
                                <label for="name" class="col-sm-6 control-label">Total:</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[0].id" value="${receivingDto.recivingAmountDetails[0].id}">
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[0].type" value="${ApplicationConstants.TOTAL }">
                                        <input step="any" type="number" class="form-control" id="recivingAmountDetails_0_amount" name="recivingAmountDetails[0].amount" placeholder="Total" onkeypress="return IsNumeric(event);" value="${receivingDto.recivingAmountDetails[0].amount}" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-6 control-label">Cash Discount</label>
                                <!-- <div class="col-sm-4">
                                    <div class="input-group">
                                    	
                                        <input step="any" type="number" class="form-control" name="recivingAmountDetails[1].cash_discount_p" id="recivingAmountDetails_1_cash_discount_p" placeholder="Discount" onkeypress="return IsNumeric(event);" value="0">
                                        <div class="input-group-addon">%</div>
                                    </div>
                                </div> -->
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[1].id" value="${receivingDto.recivingAmountDetails[1].id}">
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[1].type" value="${ApplicationConstants.CASH_DISCOUNT }">
                                        <input step="any" type="number" class="form-control changesNo" name="recivingAmountDetails[1].amount" id="cashDiscount" placeholder="Discount" onkeypress="return IsNumeric(event);" value="${receivingDto.recivingAmountDetails[1].amount * -1}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group hide">
                                <label for="name" class="col-sm-6 control-label">Display Discount</label>
                                <!-- <div class="col-sm-4">
                                    <div class="input-group">
                                        <input step="any" type="number" class="form-control" id="discount" placeholder="Discount" onkeypress="return IsNumeric(event);" value="0">
                                        <div class="input-group-addon">%</div>
                                    </div>
                                </div> -->
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[2].id" value="${receivingDto.recivingAmountDetails[2].id}">
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[2].type" value="${ApplicationConstants.DISPLAY_DISCOUNT }">
                                        <input step="any" type="number" class="form-control changesNo" name="recivingAmountDetails[2].amount" id="displayDiscount" placeholder="Discount" onkeypress="return IsNumeric(event);" value="${receivingDto.recivingAmountDetails[2].amount * -1}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-6 control-label">CGST</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[3].id" value="${receivingDto.recivingAmountDetails[3].id}">
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[3].type" value="${ApplicationConstants.CGST }">
                                        <input step="any" type="number" class="form-control cgst" name="recivingAmountDetails[3].amount" id="cgst" placeholder="0" readonly value="${receivingDto.recivingAmountDetails[3]}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-6 control-label">SGST</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[4].id" value="${receivingDto.recivingAmountDetails[4].id}">
                                        <input type="hidden" class="form-control" name="recivingAmountDetails[4].type" value="${ApplicationConstants.SGST }">
                                        <input step="any" type="number" class="form-control sgst" name="recivingAmountDetails[4].amount" id="sgst" placeholder="0" readonly value="${receivingDto.recivingAmountDetails[4]}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-6 control-label">Gross Total:</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input step="any" type="number" class="form-control" id="grossTotal" placeholder="Gross Total" onkeypress="return IsNumeric(event);" value="${receivingDto.total}" readonly>
                                    </div>
                                </div>
                            </div>
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