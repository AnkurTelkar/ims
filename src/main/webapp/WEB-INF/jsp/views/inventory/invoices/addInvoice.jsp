<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${invoice.id gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Invoice</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-bar-chart-o"></i> Invoice</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Invoice</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">Invoice</h3>
            </div>
            <c:url var="addInvoice" value="/inventory/invoices/addInvoice.htm"/>
            <form:form method="post" class="form-horizontal" action="${addInvoice}" modelAttribute="invoice" >
                <div class="box-body">
                    <div class="form-group">
                        <div class="col-sm-4 ui-widget">
                            <h3><label class="">To,</label></h3>
                            <select class="form-control js-example-basic-single" id="customerId" name="customerId" >
                                <option value="-1">None</option>
                                <c:forEach items="${customers}" var="customer">
                                    <option value="${customer.id}"${customer.id eq invoice.customerId ? 'selected' : ''}>${customer.getDisplayName()}</option>
                                </c:forEach>
                            </select>
                            <!--                            <textarea class="form-control" rows='3' id="companyAddress" placeholder="Address"></textarea>-->
                        </div>
                        <div class='col-sm-8'>
                            <h3><label class=""></label></h3>

                            <div class="form-group">
                                <h5><label class="col-sm-6 control-label">Invoice #:</label></h5>
                                <div class="col-sm-2">
                                    <input type="hidden" class="form-control" id="id" name="id" value="${invoice.id}">
                                    <input type="hidden" class="form-control" id="type" name="type" value="1">
                                    <input type="hidden" class="form-control" id="status" name="status" value="1">
                                    <input step="any" type="number" class="form-control" id="bookNo" name="bookNo" placeholder="Book No" value="${invoice.bookNo}">
                                </div>
                                <div class="col-sm-4">
                                    <input step="any" type="number" class="form-control" id="invoiceNumber" name="invoiceNumber" placeholder="Invoice No" value="${invoice.invoiceNumber}" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <h5><label for="invoiceDate" class="col-sm-6 control-label">Invoice Date:</label></h5>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <input type="text" readonly class="form-control myDatePicker" id="invoiceDate">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <hr>
                        <div class='col-sm-12'>
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th width="2%"><input id="check_all" type="checkbox" ${invoice.id gt 0 ? 'disabled' : '' }/></th>
                                            <th width="10%">Barcode</th>
                                            <th width="20%">Particular</th>
                                            <th width="8%">Quantity</th>
                                            <th width="8%">Price Each</th>
                                            <th width="8%">Ext. Price</th>
                                            <th width="8%">Avail. Qty.</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${invoice.invInvoiceDetails}" var="invoiceDetail" varStatus="status">
	                                        <tr>
	                                            <td><input class="case" type="checkbox" ${invoice.id ge 0 ? 'disabled' : '' }/></td>
	                                            <td>
	                                                <input type="hidden" name="invInvoiceDetailsList[${status.count}].id" id="id_${status.count}" value="${invoiceDetail.id}">
	                                                <input type="hidden" name="invInvoiceDetailsList[${status.count}].invoiceId" id="invoice_id_${status.count}" value="${invoiceDetail.invoiceId}">
	                                                <input type="hidden" name="invInvoiceDetailsList[${status.count}].itemSkuId" id="sku_id_${status.count}" value="${invoiceDetail.itemSkuId}" class="skuIds">
	                                                 <input type="text" name="invInvoiceDetailsList[${status.count}].barcode" id="barcode_${status.count}" class="form-control barcode" autocomplete="off" value="${invoiceDetail.getBarcode()}" maxlength="13">
												</td>
												<td>
	                                                <input type="text" data-type="sku" name="sku[]" id="sku_${status.count}" class="form-control autocomplete_txt" autocomplete="off" value="${invoiceDetail.getItemSkuDisplayName()}">
	                                            </td>
	                                            <td><input step="any" type="number" name="invInvoiceDetailsList[${status.count}].quantity" id="quantity_${status.count}" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" value="${invoiceDetail.quantity}"></td>
	                                            <td><input step="any" type="number" name="invInvoiceDetailsList[${status.count}].price" id="price_${status.count}" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" value="${invoiceDetail.price}"></td>
	                                            <td><input step="any" type="number" name="extprice[]" id="extprice_${status.count}" class="form-control extendedPrice" autocomplete="off" onkeypress="return IsNumeric(event);" readonly value="${invoiceDetail.quantity * invoiceDetail.price}"></td>
	                                            <td><input step="any" type="number" name="availableqty[]" id="availableqty_${status.count}" class="form-control" autocomplete="off" onkeypress="return IsNumeric(event);" readonly value="${invoiceDetail.getItemSku().getQuantity()}"></td>
	                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class='col-sm-6'>
                            <button class="btn btn-danger delete" type="button"><i class="fa fa-remove"></i> Delete</button>
                            <button class="btn btn-success add-invoice-detail" type="button"><i class="fa fa-plus"></i> Add More</button>
                        </div>

                        <div class='col-sm-6'>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label">Subtotal:</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input step="any" type="number" class="form-control" id="subTotal" name="subTotal" placeholder="Subtotal" onkeypress="return IsNumeric(event);" value="${invoice.subTotal}">
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
                                        <input step="any" type="number" class="form-control" id="totalTax" name="totalTax" placeholder="Tax" onkeypress="return IsNumeric(event);" value="${invoice.totalTax}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-4 control-label">Total:</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-rupee"></i></div>
                                        <input step="any" type="number" class="form-control" id="totalAftertax" placeholder="Total" onkeypress="return IsNumeric(event);" value="${invoice.subTotal + invoice.totalTax}">
                                    </div>
                                </div>
                            </div>
                            <!--                            <div class="form-group">
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