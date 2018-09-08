/* global parseFloat */

/**
 * Site : http:www.smarttutorials.net
 * @author muni
 */

//adds extra table rows
var i = $('table tr').length;
$(".add-invoice-detail")
		.on(
				'click',
				function() {
					var html = '<tr id='
							+ i
							+ '>'
							+ '<td><input class="case" type="checkbox"/></td>'
							+ '<td>'
							+ '<input type="hidden" name="invInvoiceDetailsList['
							+ i
							+ '].itemSkuId" id="sku_id_'
							+ i
							+ '" value="0" class="skuIds">'
							+ '<input type="text" name="invInvoiceDetailsList['
							+ i
							+ '].barcode" id="barcode_'
							+ i
							+ '" class="barcode form-control" autocomplete="off"" maxlength="13">'
							+ '<td><input type="text" data-type="sku" name="sku[]" id="sku_'
							+ i
							+ '" class="form-control autocomplete_txt" autocomplete="off"></td>'
							+
							/*'<td><input type="text" name="invInvoiceDetailsList[' + i + '].description" id="description_' + i + '" class="form-control" autocomplete="off"></td>' +*/
							'<td><input step="any" type="number" name="invInvoiceDetailsList['
							+ i
							+ '].quantity" id="quantity_'
							+ i
							+ '" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);"></td>'
							+
							/*'<td><input step="any" type="number" name="invInvoiceDetailsList[' + i + '].cost" id="cost_' + i + '" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" readonly></td>' +*/
							'<td><input step="any" type="number" name="invInvoiceDetailsList['
							+ i
							+ '].price" id="price_'
							+ i
							+ '" class="form-control changesNo" autocomplete="off" onkeypress="return IsNumeric(event);" ></td>'
							+
							/*'<td><input step="any" type="number" name="extcost[]" id="extcost_' + i + '" class="form-control" autocomplete="off" onkeypress="return IsNumeric(event);" readonly></td>' +*/
							'<td><input step="any" type="number" name="extprice[]" id="extprice_'
							+ i
							+ '" class="form-control extendedPrice" autocomplete="off" onkeypress="return IsNumeric(event);" readonly></td>'
							+ '<td><input step="any" type="number" name="availableqty[]" id="availableqty_'
							+ i
							+ '" class="form-control" autocomplete="off" onkeypress="return IsNumeric(event);" readonly></td>'
							+ '</tr>';
					$('table').append(html);
					$('#barcode_' + i).focus();
					i++;
				});

//to check all checkboxes
$(document).on('change', '#check_all', function() {
	$('input[class=case]:checkbox').prop("checked", $(this).is(':checked'));
});

//deletes the selected table rows
$(".delete").on('click', function() {
	$('.case:checkbox:checked').parents("tr").remove();
	$('#check_all').prop("checked", false);
	calculateTotal();
});

//autocomplete script

$(document).on('focus', '.autocomplete_txt', function() {
	applyAutocomplete(this);
});

function applyAutocomplete(obj) {
	var goBack = "../";
	goBack += $("#id").val() > 0 ? "../" : "";
	$(obj).autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "GET", //Made Change
				contentType : "application/json",
				dataType : 'json',
				url : goBack + "itemSkus/getItemSkuDetails.htm",
				data : {
					"input" : request.term
				},
				success : function(data) {
					//alert(data);
					var array = $.map(data, function(item) {
						return {
							//label: item[ "sku_details" ],
							value : item["sku_details"],
							data : item
						}
					});
					//call the filter here
					response(array);
				},
				failure : function() {
					alert("Cannot find records");
				}
			});
		},
		autoFocus : true,
		minLength : 2,
		select : function(event, ui) {
			var data = ui.item.data;
			setPopulatedData(event, data, this);
		}
	});
}

function setPopulatedData(event, data, obj) {
	id_arr = $(obj).attr('id');
	id = id_arr.split("_");
	$('#barcode_' + id[1]).val(data["barcode"]);
	$('#sku_id_' + id[1]).val(data["sku_id"]);
	$('#sku_' + id[1]).val(data["sku_details"]);
	$('#quantity_' + id[1]).val(1);
	$('#cost_' + id[1]).val(parseFloat(data["cost"]).toFixed(2));
	$('#price_' + id[1]).val(parseFloat(data["price"]).toFixed(2));
	$('#availableqty_' + id[1]).val(parseFloat(data["quantity"]).toFixed(2));

	quantity = $('#quantity_' + id[1]).val();
	costEach = $('#cost_' + id[1]).val();
	priceEach = $('#price_' + id[1]).val();

	$('#extcost_' + id[1]).val(
			(parseFloat(quantity) * parseFloat(costEach)).toFixed(2));
	$('#extprice_' + id[1]).val(
			(parseFloat(quantity) * parseFloat(priceEach)).toFixed(2));
	calculateTotal();
	$(".add-invoice-detail").trigger('click');
}

//price change
$(document).on(
		'change keyup blur',
		'.changesNo',
		function() {
			id_arr = $(this).attr('id');
			id = id_arr.split("_");
			quantity = $('#quantity_' + id[1]).val();
			priceEach = $('#price_' + id[1]).val();
			$('#extprice_' + id[1]).val(
					(parseFloat(quantity) * parseFloat(priceEach)).toFixed(2));
			calculateTotal();
		});

$(document).on('change keyup blur', '#totalTax', function() {
	calculateTotal2();
});

$(document).on('change keyup blur', '#tax', function() {
	calculateTotal();
});

function calculateTotal2() {
	subTotal = 0;
	total = 0;
	$('.extendedPrice').each(function() {
		if ($(this).val() != '')
			subTotal += parseFloat($(this).val());
	})
	totalTax = $('#totalTax').val();
	if (totalTax != '' && typeof (totalTax) != "undefined" && subTotal != 0) {
		tax = parseFloat(totalTax) * 100 / subTotal;
		$('#tax').val(tax.toFixed(2));
		total = parseFloat(subTotal) + parseFloat(totalTax);
	} else {
		$('#tax').val(0);
		total = parseFloat(subTotal);
	}
	$('#totalAftertax').val(total.toFixed(2));
}
//total price calculation 
function calculateTotal() {
	subTotal = 0;
	total = 0;
	$('.extendedPrice').each(function() {
		if ($(this).val() != '')
			subTotal += parseFloat($(this).val());
	});
	$('#subTotal').val(subTotal.toFixed(2));
	tax = $('#tax').val();
	if (tax != '' && typeof (tax) != "undefined") {
		totalTax = subTotal * (parseFloat(tax) / 100);
		$('#totalTax').val(totalTax.toFixed(2));
		total = subTotal + totalTax;
	} else {
		$('#totalTax').val(0);
		total = subTotal;
	}
	$('#totalAftertax').val(total.toFixed(2));
	//calculateAmountDue();
}
$("form").on("submit", function(e) {
	$(".skuIds").each(function() {
		if (parseFloat($(this).val()) == 0) {
			$(this).closest('tr').remove();
		}
	});

	//$( document ).submit();
});
//$(document).on('change keyup blur','#amountPaid',function(){
//	calculateAmountDue();
//});

//due amount calculation
//function calculateAmountDue(){
//	amountPaid = $('#amountPaid').val();
//	total = $('#totalAftertax').val();
//	if(amountPaid != '' && typeof(amountPaid) != "undefined" ){
//		amountDue = parseFloat(total) - parseFloat( amountPaid );
//		$('.amountDue').val( amountDue.toFixed(2) );
//	}else{
//		total = parseFloat(total).toFixed(2);
//		$('.amountDue').val( total);
//	}
//}

//It restrict the non-numbers
var specialKeys = new Array();
specialKeys.push(8, 46); //Backspace
function IsNumeric(e) {
	var keyCode = e.which ? e.which : e.keyCode;
	console.log(keyCode);
	var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys.indexOf(keyCode) != -1);
	return ret;
}

//datepicker
$(function() {
	$.fn.datepicker.defaults.format = "dd/mm/yyyy";
	var currentDate = new Date();
	$('.myDatePicker').datepicker({
		//startDate: '-3d',
		autoclose : true,
		clearBtn : true,
		todayHighlight : true
	});
	$(".myDatePicker").datepicker("setDate", currentDate);
	$(".add-invoice-detail").trigger('click');
	calculateTotal2();
});

$(document).on('keyup', '.barcode', function(e) {
	if( $(this).val() == '' ) {
		return;
	}
	if (e.keyCode == 13) {
		var obj = this;
		console.log("search now");
		var goBack = "../";
		goBack += $("#id").val() > 0 ? "../" : "";

		$.ajax({
			type : "GET", //Made Change
			contentType : "application/json",
			dataType : 'json',
			url : goBack + "itemSkus/getItemSkuDetailsByBarcode.htm",
			data : {
				"input" : $(this).val()
			},
			success: function( data ){
				setPopulatedData( event, data[0], obj );
			},
			failure : function() {
				alert("Cannot find records");
			}
	    });
	}
});