<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header" style="margin: 10px 25px;">
        <h1>
            Invoice
            <small>${invoice.bookNo} - ${invoice.invoiceNumber}</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-bar-chart-o"></i> Invoice</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>View Invoice</span></li>
        </ol>
    </section>
    <c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-info" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-check"></i> <em>${message}</em></h5>
            </div>
        </div>
    </c:if>

    <section class="invoice">
        <div class="row">
            <div class="col-sm-12">
                <h2 class="page-header">
                    <i class="fa fa-globe"></i> SBS Solutions, Inc.
                    <small class="pull-right"><a href="invoice-print.html" target="_blank" class="btn btn-default btn-xs"><i class="fa fa-print"></i> Print</a>
                <button type="button" class="btn btn-primary btn-xs" style="margin-right: 5px;">
                    <i class="fa fa-download"></i> PDF
                </button>
                </small>
<!--                    <small class="pull-right">Date: <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${invoice.createdAt}" /></small>-->
                </h2>
                
            </div>
            <div class="col-sm-4">
                    From
                    <address>
                        <strong>SBS Solutions</strong><br>
                        SBS Solutions<br>
                        Phone: (777) 286-3424<br>
                        Email: solutions52@gmail.com
                    </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4">
                    To
                    <address>
                        <strong>${invoice.customer.getDisplayName()}</strong><br>
                        ${invoice.customer.address}<br>
                        Phone: ${invoice.customer.phoneNo}<br>
                        Email: ${invoice.customer.email}
                    </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4">
                    <b>Invoice #: </b>${invoice.bookNo} - ${invoice.invoiceNumber}<br>
                    <b>Invoice Date:</b> <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${invoice.createdAt}" /><br>
<!--                    <b>Sub Total:</b> ${invoice.subTotal}<br>
                    <b>Tax:</b> ${invoice.totalTax}<br>-->
                    <b>Grand Total:</b> ${invoice.subTotal + invoice.totalTax}

                </div>
        </div>
        <div class="row">
            <div class="col-sm-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th style="width: 10%;">Qty</th>
                            <th style="width: 40%;">Particular</th>
                            <th style="width: 10%;">Retail Price</th>
                            <th style="width: 10%;">Selling Price</th>
                            <th style="width: 10%;">Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${invoice.invInvoiceDetails}" var="invoiceDetail"> 
                            <tr>
                                <td>${invoiceDetail.quantity}</td>
                                <td>${invoiceDetail.getItemSkuDisplayName()}</td>
                                <td>${invoiceDetail.getRetailPrice()}</td>
                                <td>${invoiceDetail.price}</td>
                                <td>${invoiceDetail.quantity * invoiceDetail.price}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <!-- accepted payments column -->
            <!-- /.col -->
            <div class="col-sm-offset-8 col-sm-4">
                <div class="table-responsive">
                    <table class="table text-right">
                        <tr>
                            <th style="width:50%">Subtotal:</th>
                            <td>${invoice.subTotal}</td>
                        </tr>
                        <tr>
                            <th>Tax (${invoice.getTaxPercentage()}%)</th>
                            <td>${invoice.totalTax}</td>
                        </tr>
                        <tr>
                            <th>Total:</th>
                            <td>${invoice.subTotal + invoice.totalTax}</td>
                        </tr>
                    </table>
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <!-- this row will not appear when printing -->
        <div class="box-footer text-right" style="padding-left: 0px; padding-right: 0px;">
            <a href="<c:url value='/inventory/invoices/addInvoice.htm'/>" class="btn btn-primary" role="button">Add New</a>
            <a href="<c:url value='/inventory/invoices/${invoice.id}/editInvoice.htm'/>" class="btn btn-info" role="button">Edit</a>
<!--            <button type="button" class="btn btn-default">Cancel</button>-->
        </div>
    </section>


</div>