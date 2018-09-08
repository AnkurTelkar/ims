/* global parseFloat */

/**
 * Site : http:www.smarttutorials.net
 * 
 * @author muni
 */

// adds extra table rows
var i = $('table tbody tr').length;
$(".add-receiving-detail")
		.on(
				'click',
				function() {
					var html = '<tr>'
                        + '<td>'
                        + '<input class="case" type="checkbox"/>'
                        + '<input type="hidden" name="receivingItems[' + i + '].receivingItemId" id="receiving_item_id_' + i + '" value="">'
                        /*+ '<input type="hidden" name="receivingItems[' + i + '].receiving.receivingId" id="receiving_id_' + i + '" value="">'*/
                        + '<input type="hidden" name="receivingItems[' + i + '].sku.skuId" id="sku_id_' + i + '" value="0" class="skuIds">'
                        + '</td>'
                        + '<td><input type="text" name="receivingItem[' + i + '].sku.barcode" id="barcode_' + i + '" class="form-control barcode" autocomplete="off" value="" maxlength="13"></td>'
                        + '<td>'
                        + '<input type="text" data-type="sku" name="sku[]" id="sku_' + i + '" class="form-control autocomplete_txt" autocomplete="off" value="">'
                        + '</td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].quantity" id="quantity_' + i + '" class="form-control changesNo quantity isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="1"></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].cost" id="cost_' + i + '" class="form-control changesNo isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].sku.retailPrice" id="retailprice_' + i + '" class="form-control isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].sku.sellingPrice" id="sellingprice_' + i + '" class="form-control isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].gst" id="gst_' + i + '" class="form-control changesNo isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].gst_amount" id="gst_amount_' + i + '" class="form-control changesNo gst" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].discount" id="discount_' + i + '" class="form-control changesNo isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="receivingItems[' + i + '].discount_amount" id="discount_amount_' + i + '" class="form-control discount-amount isEditable" autocomplete="off" onkeypress="IsNumeric(event);" value="" readonly></td>'
                        + '<td><input step="any" type="number" name="extcost[]" id="extcost_' + i + '" class="form-control extendedCost" autocomplete="off" onkeypress="IsNumeric(event);" readonly value=""></td>'
                        + '</tr>';
					$('table').append(html);
					//$('#barcode_' + i).focus();
					i++;
					readOnlyClick();
				});

// to check all checkboxes
$(document).on('change', '#check_all', function() {
	$('input[class=case]:checkbox').prop("checked", $(this).is(':checked'));
});

// deletes the selected table rows
$(".delete").on('click', function() {
	$('.case:checkbox:checked').parents("tr").remove();
	$('#check_all').prop("checked", false);
	calculateTotal();
});

// autocomplete script

$(document).on('focus', '.autocomplete_txt', function() {
	applyAutocomplete(this);
});

function applyAutocomplete(obj) {
	var goBack = $("#receivingId").val() > 0 ? "../" : "";
	$(obj).autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "GET", // Made Change
				contentType : "application/json",
				dataType : 'json',
				url : goBack + "getItemSkuDetailsForReceiving.htm",
				data : {
					"input" : request.term
				},
				success : function(data) {
					// alert(data);
					var array = $.map(data, function(item) {
						return {
							value : item["skuDetails"],
							data : item
						}
					});
					// call the filter here
					response(array);
				},
				failure : function() {
					alert("Cannot find records");
				}
			});
		},
		autoFocus : true,
		minLength : 1,
		select : function(event, ui) {
			var data = ui.item.data;
			setPopulatedData(event, data, this);
		}
	});
}

function setPopulatedData(event, data, obj) {
	var trIndex = $(obj).closest("tr").index();
	
	$('#barcode_' + trIndex).val(data["barcode"]);
	$('#sku_id_' + trIndex).val(data["skuId"]);
	$('#sku_' + trIndex).val(data["skuDetails"]);
	//$('#quantity_' + trIndex).val(1);
	$('#gst_' + trIndex).val(parseFloat(data["gst"]).toFixed(0));
	$('#cost_' + trIndex).val(parseFloat(data["cost"]).toFixed(2));
	$('#retailprice_' + trIndex).val(parseFloat(data["retailPrice"]).toFixed(2));
	$('#sellingprice_' + trIndex).val(parseFloat(data["sellingPrice"]).toFixed(2));
	$('#discount_' + trIndex).val( 0 );
	$('#discount_amount_' + trIndex).val( 0 );

	quantity = getValidNum( $('#quantity_' + trIndex) );
	costEach = getValidNum( $('#cost_' + trIndex) );
	gstPercent = getValidNum( $('#gst_' + trIndex) );
	discountPercent = $('#discount_' + trIndex);
	totalCost = quantity * costEach;
	totalGst = totalCost * gstPercent / 100;
	$( "#gst_amount_" + trIndex ).val( totalGst );
	$('#extcost_' + trIndex).val( ( totalCost ).toFixed(2) );
	
	calculateTotal();
	//$(".add-receiving-detail").trigger('click');
	readOnlyClick();
}

function getValidNum( obj ) {
	val = parseFloat( obj.val() );
	if( isNaN( val ) ) {
		val = 0;
	}
	return val;
}

// price change
$(document).on( 'change keydown blur', '.changesNo', function(e) {
	populateValues( this, e );
});

function populateValues( obj, event ) {
	var trIndex = $(obj).closest("tr").index();
	quantity = getValidNum( $('#quantity_' + trIndex) );
	costEach = getValidNum( $('#cost_' + trIndex) );
	gstPercent = getValidNum( $('#gst_' + trIndex) );
	
	totalCost = quantity * costEach;
	totalGst = totalCost * gstPercent / 100;
	
	discountPercent = getValidNum( $('#discount_' + trIndex) );
	totalDiscount = totalCost * discountPercent / 100;
	
	$( "#gst_amount_" + trIndex ).val( totalGst );
	$( "#discount_amount_" + trIndex ).val( totalDiscount );
	$('#extcost_' + trIndex).val( ( totalCost - totalDiscount ).toFixed(2) );
	
	calculateTotal();
	if( event ) {
		var keyCode = event.which ? event.which : event.keyCode;
		if( $(obj).hasClass( "quantity" ) && event.type == "keydown" && keyCode == 9 ) {
			event.preventDefault();
			$( "#barcode_" + (trIndex + 1) ).focus();	
		}	
	}
	
}

$( document ).on( 'change keydown blur', '.discount-amount', function( e ) {
	var trIndex = $(this).closest("tr").index();
	quantity = getValidNum( $('#quantity_' + trIndex) );
	costEach = getValidNum( $('#cost_' + trIndex) );
	totalDiscount = $( this ).val();
	discountEach = 0;
	if( quantity != 0 ) {
		discountEach = totalDiscount / quantity;	
	}
	
	discountPercent = 0;
	if( costEach > 0 ) {
		discountPercent = discountEach * 100 / costEach;	
	}
	$('#discount_' + trIndex).val( discountPercent );
	totalCost = quantity * costEach;
	totalGst = totalCost * gstPercent / 100;
	$('#extcost_' + trIndex).val( ( totalCost - totalDiscount ).toFixed(2) );
	calculateTotal();
} );


$(document).on('change keyup blur', '#tax', function() {
	calculateTotal();
});

// total calculation
function calculateTotal() {
	var total = 0;
	var grossTotal = 0;
	var gst =  0;
	$('.extendedCost').each(function() {
		if ($(this).val() != '')
			total+= parseFloat($(this).val());
	});
	
	$('.gst').each(function() {
		if ($(this).val() != '')
			gst+= parseFloat($(this).val());
	});
	
	$('#recivingAmountDetails_0_amount').val(total.toFixed(2));
	var cd = getValidNum( $("#cashDiscount") );
	var dd = getValidNum( $("#displayDiscount") );
	var halfGst = gst / 2;
	$( "#cgst" ).val( halfGst.toFixed(2) );
	$( "#sgst" ).val( halfGst.toFixed(2) );
	$('#grossTotal').val( (total - cd - dd + gst).toFixed(2) );
	/*tax = $('#tax').val();
	if (tax != '' && typeof (tax) != "undefined") {
		totalTax = subTotal * (parseFloat(tax) / 100);
		$('#totalTax').val(totalTax.toFixed(2));
		total = subTotal + totalTax;
	} else {
		$('#totalTax').val(0);
		total = subTotal;
	}
	$('#totalAftertax').val(total.toFixed(2));*/
	// calculateAmountDue();
}
$("form").on("submit", function(e) {
	$(".skuIds").each(function() {
		if (parseFloat($(this).val()) == 0) {
			$(this).closest('tr').remove();
		}
	});
	
	cd = getValidNum( $( "#cashDiscount" ) );
	dd = getValidNum( $( "#displayDiscount" ) );
	
	$( "#cashDiscount" ).val( cd );
	$( "#displayDiscount" ).val( dd );
	// $( document ).submit();
});


// datepicker
$(function() {
	$.fn.datepicker.defaults.format = "dd/mm/yyyy";
	var currentDate = new Date();
	if( $( "#receivingId" ).val() > 0 ) {
		currentDate = $("#invoiceDate").val();
	}
	$("#invoiceDate").datepicker( "setDate", currentDate );
	
	if( i == 0 ) {
		$(".add-receiving-detail").trigger('click');
	} else {
		$( ".quantity" ).each( function() {
			populateValues( this );	
		} );
	}
	if( $("#status").val() == STATUS_RETURN ) {
		var divFrom = $("#div-from").clone();
		$("#div-from").html( $("#div-to").clone().html() );
		$("#div-to").html( divFrom.html() );
	}
	readOnlyClick();
	//calculateTotal();
});

$(document).on('keydown', '.barcode', function(e) {
	if ($(this).val() == '') {
		return;
	}
	
	var keyCode = e.which ? e.which : e.keyCode;
	if ( keyCode == 9 || keyCode == 13 ) {
		var goBack = $("#receivingId").val() > 0 ? "../" : "";
		var obj = this;
		console.log("search now");
		$.ajax({
			type : "GET", // Made Change
			contentType : "application/json",
			dataType : 'json',
			url : goBack + "getItemSkuDetailsByBarcode.htm",
			data : {
				"input" : $(this).val()
			},
			success : function(data) {
				setPopulatedData(event, data, obj);
				e.preventDefault();
				var trIndex = $(obj).closest("tr").index();
				$( "#quantity_" + trIndex ).focus();
			},
			failure : function() {
				alert("Cannot find records");
			}
		});
	}
});

function readOnlyClick() {
	$('.isEditable').each(function() {
		$(this).on( "click", function() {
			$(this).attr("readonly", false);
		} );
	});
}

$( ".sidebar-mini" ).addClass( "sidebar-collapse" );
