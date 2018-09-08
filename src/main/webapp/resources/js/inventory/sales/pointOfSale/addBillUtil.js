$('#keyboard_numbers_only').jkeyboard({
    layout: "float_numbers_only",
    input: $('#field0')
});

$( ".jkey" ).click( function( e ) {
	if( $( this ).html() == "." ) {
		if( $( "#field0" ).val().split('.').length > 2 ) {
			$( "#field0" ).val( $( "#field0" ).val().substring( 0, $( "#field0" ).val().length - 1 ) );
		}
	}
} );

$( "#field0" ).click( function() {
	$(this).select();
} );
$( "#field0" ).on( "keypress change paste", function( e ) {
	var preventDefault = false;
	if( e.which != 46 ) {
		if( isNaN( String.fromCharCode( e.which ) ) ){
			preventDefault = true;
		}
		
		if( $(this).val() != "." && $(this).val().length > 0 && !$.isNumeric( $(this).val() ) ) {
			preventDefault = true;
		}
	} else if( $(this).val().indexOf( "." ) >= 0 ) {
			preventDefault = true;
		}
	
	
	if( preventDefault ) {
		e.preventDefault();	
	}
	
});

$(document).on('keydown', '.barcode', function(e) {
	if ($(this).val() == '') {
		return;
	}
	
	var keyCode = e.which ? e.which : e.keyCode;
	if ( keyCode == 9 || keyCode == 13 ) {
		//var goBack = $("#billId").val() > 0 ? "../" : "";
		var obj = this;
		console.log("search now");
		$.ajax({
			type : "GET", // Made Change
			contentType : "application/json",
			dataType : 'json',
			url : "../getItemSkuDetailsByBarcode.htm",
			data : {
				"input" : $(this).val()
			},
			success : function(data) {
				setPopulatedData( data, obj);
				$( ".barcode" ).val( "" );
				e.preventDefault();
			},
			failure : function() {
				alert("Cannot find records");
			},
			error : function() {
				alert("Cannot find records");
			}
		});
	}
});

function getItemRowIndex( skuId ) {
	var index = -1;
	$( ".bill-item" ).each( function() {
		if( $(this).data( "skuid" ) == skuId ) {
			index = $(this).closest("tr").index();
			return index;
		}
	} );
	return index;
}

function setPopulatedData(data, obj) {
	var skuId = data["skuId"];
	if( skuId == undefined || skuId <= 0 ) {
		return;
	}
	var row;
	var table = document.getElementById("myTable");
	var i = getItemRowIndex( skuId );
	if( i >= 0 ) {
		row = $( table ).find('tr').eq(i);		
		data["qty"] = parseFloat( $( row ).find('td').eq( 0 ).find( ".qty" ).html() ) + 1;
		data["sellingPrice"] = $( row ).find('td').eq( 2 ).find( ".price" ).html();
	} else {
		row = addRow( table );
		i = $( row ).index();
		data["qty"] = 1;
	}
	
	var qty = parseFloat( data["qty"] );
	var price = parseFloat(data["priceWithoutGst"]).toFixed(2);
	var gst = parseFloat(data["gst"]).toFixed(2);
	var desc = data["skuShortDetails"];
	
	var billItem = $( row ).find('td').eq( 0 ).find( ".bill-item" );
	billItem.data( "skuid", skuId );
	billItem.data( "qty", qty );
	billItem.data( "price", price );
	billItem.data( "gst", gst );
	billItem.data( "itemdiscount", 0 );
	$( row ).find( ".itemDiscountDisplay" )
	.removeClass( "show" )
	.addClass( "hide" )
	.html( "" );
	billItem.data( "soldby", getUserId() );
	$( row ).find('td').eq( 0 ).find( ".qty" ).html( qty );
	$( row ).find('td').eq( 1 ).find( ".desc" ).html( desc );
	$( row ).find('td').eq( 2 ).find( ".price" ).html( parseFloat( price ).toFixed(2) );
	$( row ).find('td').eq( 3 ).find( ".total-price" ).html( parseFloat( qty * price ).toFixed(2) );
	$( row ).addClass( "item-row-selected" );
	if( $(obj).attr("class").indexOf( "autocomplete_txt" ) >= 0 ) {
		deselectAll();
		$(row).addClass( "item-row-selected");
		editQty();
	} else {
		$(row).removeClass( "item-row-selected", 1000 );
		$(".alert").bind('closed.bs.alert', function(){
			calculateTotals();
		});
	}
	
	calculateTotals();
}

function calculateTotals() {
	var discount = parseFloat($( ".discount" ).html());
	var subTotal = 0.00;
	var gstTotal = 0.00;
	$( ".bill-item" ).each( function() {
		var $priceWithDiscount = parseFloat( $(this).data( "price" ) ) - parseFloat( $(this).data( "itemdiscount" ) );
		var itemTotal = parseFloat( $(this).data( "qty" ) ) * parseFloat( $priceWithDiscount );
		subTotal += itemTotal;
		gstTotal += itemTotal * $(this).data("gst") /100;
	} );
	$( ".sub-total" ).html( parseFloat( subTotal ).toFixed( 2 ) );
	$( ".CGST" ).html( parseFloat( gstTotal/2 ).toFixed( 2 ) );
	$( ".SGST" ).html( parseFloat( gstTotal/2 ).toFixed( 2 ) );
	var billTotal = subTotal + gstTotal + discount;
	//
	var roundedBillTotal = $("#bill" ).data( "status" ) == STATUS_FINALIZE ? parseFloat( billTotal ).toFixed( 2 ) : Math.round( billTotal );
	$( ".total" ).html( roundedBillTotal );
	calculatePayments();
}

function calculatePayments() {
	var paidAmount = 0.00;
	$( ".paid-amount" ).each( function() {
		paidAmount += parseFloat( $( this ).html() );
	} );
	$( ".paid" ).html( parseFloat( paidAmount ).toFixed(2) );
	var total = parseFloat( $(".total").html() );
	var balance = Math.round( total - paidAmount );
	$( ".balance" ).html( parseFloat( balance ).toFixed(2) );
	$( "#field0" ).val( $( ".balance" ).html() );
}

function settlePayment( id ) {
	var amount = parseFloat( $( "#" + id ).html() );
	var field0 = parseFloat( $( "#field0" ).val() );
	if( isNaN( parseFloat( field0 ) ) ) {
		field0 = 0.00;
	}
	amount += field0;
	$( "#" + id ).html( parseFloat(amount).toFixed( 2 ) );
	$( "#field0" ).val( "" );
	calculatePayments();
}

function deletePayment( id ) {
	var amount = parseFloat( $( "#" + id ).html() );
	var field0 = parseFloat( $( "#field0" ).val() );
	if( isNaN( parseFloat( amount ) ) ) {
		amount = 0.00;
	}
	field0 += amount;
	$( "#" + id ).html( 0.0 );
	$( "#field0" ).val( field0 );
	calculatePayments();
}

function addRow( table ) {
	var i = $('#myTable tbody tr').length;
	var row = table.insertRow(0);
	$(row).addClass( "alert alert-dismissible-justified" );
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	$( cell0 ).html( "<input type='hidden' class='bill-item' id='bill_item_" + i + "' name='billItems[" + i + "].billItemId'>" );
	$( cell0 ).addClass( "col-xs-2" ).html( $( cell0 ).html() + '<span class="badge"><i><span class="qty"></span></i></span>' );
	$( cell1 ).addClass( "col-xs-5" ).html( '<span class="desc" ></span><br><span class="itemDiscountDisplay hide"></span>' );
	$( cell2 ).addClass( "col-xs-2" ).html( '<span class="badge"><i><span class="price"></span</i></span>' );
	$( cell3 ).addClass( "col-xs-2" ).html( '<span class="badge"><i><span class="total-price"></span></i></span>' );
	$( cell4 ).addClass( "col-xs-1" ).html( '<a href="#" data-dismiss="alert" class="close"> <i class="fa fa-trash"></i></a>' );
	selectUnselectRowEvent( row );
	$(".alert").bind('closed.bs.alert', function(){
		calculateTotals();
	});
	return row;
}

function selectUnselectRowEvent( row ) {
	$( row ).click( function() {
		var selectRowClass = "item-row-selected";
		if( $( this ).hasClass( selectRowClass ) ) {
			$( this ).removeClass( selectRowClass );
		} else {
			$( this ).addClass( selectRowClass );
		}
	} );
}

$(document).on('focus', '.autocomplete_txt', function() {
	applyAutocomplete(this);
});

function applyAutocomplete(obj) {
	//var goBack = $("#billId").val() > 0 ? "../" : "";
	$(obj).autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "GET", // Made Change
				contentType : "application/json",
				dataType : 'json',
				url : "../getItemSkuDetailsByDesc.htm",
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
			setPopulatedData( data, this);
			setTimeout(function() {
				$( ".autocomplete_txt" ).val( "" );	
			}, 500);
			
		}
	});
}

function selectAll() {
	$("#myTable tr").addClass( "item-row-selected" );
}

function deselectAll() {
	$("#myTable tr").removeClass( "item-row-selected" );
}

function deleteSelected() {
	$( ".item-row-selected" ).fadeOut(500, function() {
		$(this).remove();
		calculateTotals();
	});
}

function editQty() {
	var length = $('#myTable tbody tr').length;
	if( length <= 0 ) {
		showAlertSmall( "No Item added!" );	
	} else if($( ".item-row-selected" ).length <= 0) {
		showAlertSmall( "No Item selected!" );
	} else {
		showKeyPad( "Change Qty", "qty" );
	}
}

function editPrice() {
	var length = $('#myTable tbody tr').length;
	if( length <= 0 ) {
		showAlertSmall( "No Item added!" );	
	} else if($( ".item-row-selected" ).length <= 0) {
		showAlertSmall( "No Item selected!" );
	} else {
		showKeyPad( "Change Price", "price" );
	}
}

function callBackKeypad( selector, value ) {
	if( isNaN( parseFloat( value ) ) ) {
		return;
	}
	
	var value = parseFloat(value).toFixed(2);
	if( selector == "discount" ) {
		if( value >= 0 ) {
			var $discount = parseFloat( $( ".sub-total" ).html() ) * value /100 * -1;
			$( "." + selector ).html( $discount.toFixed( 2 ) );
		}
	} else if( selector == "itemdiscount" ) {
		$( ".item-row-selected" ).each( function() {
			var price = parseFloat( $(this).find( ".bill-item" ).data( "price" ) ).toFixed( 2 );
			var discount = parseFloat( price * parseFloat(value)/100 ).toFixed( 3 );
			$(this).find( ".bill-item" ).data( "itemdiscount", discount );
			var priceAfterDiscount = parseFloat( price - discount ).toFixed( 2 );
			$(this).find( ".price" ).html( priceAfterDiscount );
			var totalPrice = $(this).find( ".bill-item" ).data( "qty" ) * priceAfterDiscount;
			$(this).find( ".total-price" ).html( parseFloat( totalPrice ).toFixed( 2 ) );
			if( discount > 0 ) {
				var discountPercent = isNaN( priceAfterDiscount ) || priceAfterDiscount == 0 ? 0 : value; 
				var discountDisplay = "<strong><em>Price:" + price + ", Disc(" + discountPercent + "%):" + discount + " @Each</em></strong>";
				$( this ).find( ".itemDiscountDisplay" )
				.removeClass( "hide" )
				.addClass( "show" )
				.html( discountDisplay );
			} else {
				$( this ).find( ".itemDiscountDisplay" )
				.removeClass( "show" )
				.addClass( "hide" )
				.html( "" );
			}
		} );
	} else {
		$( ".item-row-selected" ).each( function() {
			$(this).find( ".bill-item" ).data( selector, value );
			$(this).find( "." + selector ).html( parseFloat(value) );
			var price = parseFloat( $(this).find( ".bill-item" ).data( "price" ) );
			var totalPrice = $(this).find( ".bill-item" ).data( "qty" ) * price;
			$(this).find( ".total-price" ).html( parseFloat( totalPrice ).toFixed( 2 ) );
		} );
		setTimeout(function() {
			$("input.autocomplete_txt").focus();	
		}, 500);
	}
	calculateTotals();
}

function refundItems() {
	var length = $('#myTable tbody tr').length;
	if( length <= 0 ) {
		showAlertSmall( "No Item added!" );	
	} else if($( ".item-row-selected" ).length <= 0) {
		showAlertSmall( "No Item selected!" );
	} else {
		$( ".item-row-selected" ).each( function() {
			var itemQty = $(this).find( ".bill-item" ).data( "qty" ) * -1;
			$(this).find( ".bill-item" ).data( "qty", itemQty );
			$(this).find( ".qty" ).html( parseFloat( itemQty ) );
			var price = parseFloat( $(this).find( ".bill-item" ).data( "price" ) );
			var totalPrice = $(this).find( ".bill-item" ).data( "qty" ) * price;
			$(this).find( ".total-price" ).html( parseFloat( totalPrice ).toFixed( 2 ) );
		} );
		calculateTotals();
	}
}

function addDiscount() {
	var length = $('#myTable tbody tr').length;
	if( length <= 0 ) {
		showAlertSmall( "No Item added!" );	
	} else {
		var value = parseFloat( $( ".discount" ).html() ).toFixed(2);
		if( value < 0 ) {
			value = value *-1;
			value = ( value * 100 ) / parseFloat( $( ".sub-total" ).html() );
			value = value.toFixed( 2 );
		}
		
		showKeyPad( "Discount", "discount", value );
	}
}

function addItemDiscount() {
	var length = $('#myTable tbody tr').length;
	if( length <= 0 ) {
		showAlertSmall( "No Item added!" );	
	} else if($( ".item-row-selected" ).length <= 0) {
		showAlertSmall( "No Item selected!" );
	} else {
		showKeyPad( "Item Discount", "itemdiscount" );
	}
}

function reopenBill() {
	showConfirm( "Re-Open Bill", "Bill is already Finalized. Do you want to re-open?" );
}

function callBackConfirm( result ) {
	if( result ) {
		processBill( STATUS_DRAFT );
	}
}

function processBill( status ) {
	if( status == STATUS_FINALIZE ) {
		if( $( "#field0" ).val() != 0 ) {
			showAlertSmall( "Cannot Finalize Bill. Amount not balanced!" );
			return;
		} 
	}
	
	if( status == STATUS_REFUND ) {
		if( $( "#field0" ).val() != 0 ) {
			showAlertSmall( "Cannot Debit Bill. Amount not balanced!" );
			return;
		}
	}
	
	var billObj = fillBillObject( status );
	var input = JSON.stringify( billObj );
	$( ".process-bill" ).addClass( "disabled" );
	var dialog = showDialog( "Processing. Please Wait..." );
	$.ajax({
		type : "GET", // Made Change
		contentType : "application/json",
		dataType : 'json',
		url : "../updateBill.htm",
		data : {
			"input" : input 
		},
		success : function(data) {
			dialog.modal( 'hide' );
			showMesasge( data.message );
			processBillCallback(data.status);
		},
		failure : function(data) {
			dialog.modal( 'hide' );
			showMesasge( "Unable to Update Bill. Process Failed!" );
		},
		error : function(data) {
			dialog.modal( 'hide' );
			showMesasge( "Unable to Update Bill due to some error!" );
		}
	});
	
}

function processBillCallback( status ) {
	if( status == STATUS_FINALIZE || status == STATUS_REFUND ) {
		var statusStr = status == STATUS_FINALIZE ? "Finalize" : "Credit Note";
		$( "#statusStr" ).html( statusStr );
		$( ".btn-div" ).each( function() {
			$(this).find( "a" ).not( "#print" ).addClass( "disabled" );
		} );
		$( "input" ).attr( "disabled", "disabled" );
		$( "#reOpen" ).removeClass( "disabled" );
		$( ".process-bill" ).addClass( "disabled" );
	} else if( status == STATUS_VOID ) {
		$( "#statusStr" ).html( "Void" );
		$( ".btn-div" ).each( function() {
			$(this).find( "a" ).addClass( "disabled" );
		} );
		$( "input" ).attr( "disabled", "disabled" );
		$( ".process-bill" ).addClass( "disabled" );
	}else {
		$( "#statusStr" ).html( "Draft" );
		$( ".btn-div" ).each( function() {
			$(this).find( "a" ).removeClass( "disabled" );
		} );
		$( "#reOpen" ).addClass( "disabled" );
		$( "input" ).removeAttr( "disabled" );
		$( ".process-bill" ).removeClass( "disabled" );
	}
	
	
}

function showMesasge( message ) {
	$("#message").html( message );
	$("#message").closest( ".bb-alert" ).show();
	$("#message").closest( ".bb-alert" ).fadeOut(5000);
}

function fillBillObject( status ) {
	var bill = new Object();
	var customer = new Object();
	var user = new Object();
	var billAmountDetails = new Array();
	
	bill.customer = customer;
	bill.user = user;
	bill.billAmountDetails = billAmountDetails;
	bill.status = status;
	bill.total = parseFloat( $( ".total" ).html() ).toFixed( 2 );
	bill.billId = $( "#bill" ).data( "billid" );
	bill.customer.customerId = $( "#bill" ).data( "customerid" );
	bill.user.userId = getUserId();

	//Fill Amount Details
	fillAmountDetails( bill );
	
	//Fill Payment Details
	fillPaymentDetails( bill );
	
	//Fill Item Details
	fillItemDetails( bill );
	console.log( bill );
	return bill;
}

function getUserId() {
	var userId = $( "#bill" ).data( "userid" );
	if(userId <= 0 ) {
		userId = $( "#posUserDto" ).val();
	}
	return userId;
}

function fillAmountDetails( bill ) {
	var billAmountDetail = new Object();
	billAmountDetail.amount = parseFloat( $( ".sub-total" ).html() ).toFixed( 2 );
	if( billAmountDetail.amount != 0 ) {
		billAmountDetail.type = 1;
		var id = parseInt( $( ".sub-total" ).data( "id" ) );
		if( id > 0 ) {
			billAmountDetail.id = $( ".sub-total" ).data( "id" );	
		}
		bill.billAmountDetails.push( billAmountDetail );		
	}
	
	billAmountDetail = new Object();
	billAmountDetail.amount = parseFloat( $( ".discount" ).html() ).toFixed( 2 );
	if( billAmountDetail.amount != 0 ) {
		billAmountDetail.amount = billAmountDetail.amount;
		billAmountDetail.type = 2;
		id = parseInt( $( ".discount" ).data( "id" ) );
		if( id > 0 ) {
			billAmountDetail.id = id	
		}
		bill.billAmountDetails.push( billAmountDetail );	
	}
	
	
	billAmountDetail = new Object();
	billAmountDetail.amount = parseFloat( $( ".CGST" ).html() ).toFixed( 2 );
	if( billAmountDetail.amount != 0 ) {
		billAmountDetail.type = 4;
		id = parseInt( $( ".CGST" ).data( "id" ) );
		if( id > 0 ) {
			billAmountDetail.id = id	
		}
		bill.billAmountDetails.push( billAmountDetail );
	}
	billAmountDetail = new Object();
	billAmountDetail.amount = parseFloat( $( ".SGST" ).html() ).toFixed( 2 );
	if( billAmountDetail.amount != 0 ) {
		billAmountDetail.type = 5;
		id = parseInt( $( ".SGST" ).data( "id" ) );
		if( id > 0 ) {
			billAmountDetail.id = id	
		}
		bill.billAmountDetails.push( billAmountDetail );
	}
	
}

function fillPaymentDetails( bill ) {
	var billPayments = new Array();
	bill.billPayments = billPayments;
	$( ".paid-amount" ).each( function() {
		var billPayment = new Object();
		billPayment.amount = parseFloat( $(this).html() ).toFixed(2);
		if( billPayment.amount != 0 ) {
			var id = parseInt( $(this).data( "id" ) );
			if( id > 0 ) {
				billPayment.id = id;	
			}
			
			billPayment.type = $(this).data( "type" );
			bill.billPayments.push( billPayment );	
		}
	});
}

function fillItemDetails( bill ) {
	var billItems = new Array();
	bill.billItems = billItems;
	$( ".bill-item" ).each( function() {
		if( parseFloat( $(this).data( "qty" ) ) != 0 ) {
			var billItem = new Object();
			var sku = new Object();
			var soldBy = new Object();
			var billItemId = parseInt( $(this).data( "billitemid" ) );
			if( billItemId > 0 ) {
				billItem.billItemId = billItemId;	
			}
			billItem.sku = sku;
			billItem.soldBy = soldBy;
			billItem.sku.skuId = $(this).data( "skuid" );
			billItem.quantity = $(this).data( "qty" );
			billItem.price = $(this).data( "price" );
			billItem.discount = $(this).data( "itemdiscount" );
			billItem.soldBy.userId = $(this).data( "soldby" );
			bill.billItems.push( billItem );			
		}
	} );	
}

$(function() {
	$( ".return" ).hide();
	$( "#mainDiv" ).height( $( document ).height());
	calculateTotals();
	processBillCallback( $("#bill" ).data( "status" ) );
	$('#myTable tbody tr').each( function() {
		selectUnselectRowEvent( this );
	} );
});


function printBill() {
	var billId = parseInt( $( "#bill" ).data( "billid" ) );
	var url = getBaseURL() + "/inventory/sales/pointOfSale/" + billId +  "/printBill.htm";
	alert(url);
	$( "#printDiv" ).load( url );
	/*var billId = parseInt( $( "#bill" ).data( "billid" ) );
	console.log( "Going to print BillId: " + billId );
	if( isNaN( billId ) ) {
		showAlertSmall( "Error: Invalid Bill!" );
		return; 
	}
	$.ajax({
		type : "GET", // Made Change
		contentType : "application/json",
		dataType : 'json',
		url : "../printBill.htm",
		data : {
			"id" : billId 
		},
		success : function(data) {
			showMesasge( data.message );
		},
		failure : function(data) {
			showMesasge( "Unable to Print Bill. Process Failed!" );
		},
		error : function(data) {
			showMesasge( "Unable to Print Bill due to some error!" );
		}
	});*/
	
}

$(".alert").bind('closed.bs.alert', function(){
	calculateTotals();
});