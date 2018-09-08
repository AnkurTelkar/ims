var url = "";
var customerInfo = "";
function newBill( url ) {
	this.url = url;
	var id = $( "#customer-info-hidden" ).data( "id" );
	var phoneNo = $( "#customer-info-hidden" ).data( "phoneno" );
	var customerName = $( "#customer-info-hidden" ).data( "customerName" );
	var email = $( "#customer-info-hidden" ).data( "email" );
	var customerInfo = $.trim( $( "#customerInfo" ).val() );
	if( id > 0 ) {
		customerInfo = id;
		$( "#infoType" ).val(0);
	} else if( phoneNo != undefined && phoneNo.length > 0 && phoneNo.length > 9 && phoneNo.length <= 13 && parseInt( phoneNo ) > 0 ) {
		customerInfo = phoneNo;
		customerInfo = customerInfo.substring( customerInfoLength - 10, customerInfoLength );
		$( "#infoType" ).val(1);
	} else if( email != undefined && validateEmail( email ) ) {
		customerInfo = email;
		$( "#infoType" ).val(2);
	} else if( customerName != undefined && customerName.length > 0 ) {
		customerInfo = customerName;
		$( "#infoType" ).val(3);
	} else {
		var customerInfoLength = customerInfo.length;
		if( customerInfoLength > 9 && customerInfoLength <= 13 && parseInt( customerInfo ) > 0 ) {
			customerInfo = phoneNo;
			$( "#infoType" ).val(1);
		} else if( validateEmail( customerInfo ) ) {
			customerInfo = email;
			$( "#infoType" ).val(2);
		} else {
			showConfirm( "Create New Bill", "Invalid Customer Info. Do you want to continue?" );
			return;
		}
	}
	$( "#customerInfoSubmit" ).val( customerInfo );
	submit();
}

function callBackConfirm( result ) {
	if( result ) {
		submit();
	} else {
		$( "#customerInfo" ).val( "" );
	}
}

function submit() {
	$( "#postLoginForm" ).attr( "action", url );
	$( "#postLoginForm" ).submit();
}

function openBill( url ) {
	this.url = url;
	var billNo = $.trim( $( "#billNo" ).val() );
	$( "#billNo" ).val( billNo );
	if( billNo.length == "" || isNaN( billNo ) ) {
		showAlert( "Not a valid Number" );
	} else {
		submit();
	} 
}

$( "#billNo" ).on( "keyup", function( e ) {
	if( e.keyCode == 13 ) {
		$( "#openBill" )[0].click();
	}
} );

/*$( "#customerInfo" ).on( "keyup", function( e ) {
	if( e.keyCode == 13 ) {
		$( "#newBill" )[0].click();
	}
} );*/

function postChangeDate( start, end ) {
	$('#daterange-btn span').html(start.toDateString() + ' - ' + end.toDateString());
	$.ajax({
		type : "GET", // Made Change
		contentType : "application/json",
		dataType : 'json',
		url : "findBills.htm",
		data : {
			"start" : start,
			"end" : end, 
		},
		success : function(data) {
			populateBills( data );
		},
		failure : function(data) {
			showAlert( "Unable to find bills. Process Failed!" );
		},
		error : function(data) {
			showAlert( "Unable to find bills due to some error!" );
		}
	});
}

function populateBills( data ) {
	console.log( data );
	var tableHtml = '<table id="example1" class="table table-bordered table-striped ellipsis">';
	tableHtml += '<thead><tr>';
	tableHtml += '<th>Bill #</th>';
	tableHtml += '<th>Status</th>';
	tableHtml += '<th>Customer</th>';
	tableHtml += '<th>Amount</th>';
	tableHtml += '</tr></thead>'; 
	tableHtml += '<tbody>';
	
	$.each( data, function( i, v ) {
		var rowHtml = "<tr role='row'>";
		var url = "/" + window.location.pathname.split("/")[1] + "/inventory/sales/pointOfSale/" + v.billId + "/openBill.htm";
		rowHtml += "<td><a href='" + url + "'> " + v.billNo + "</a></td>";
		var className = v.status == STATUS_VOID ? 'label-danger' : (v.status == STATUS_DRAFT ? 'label-warning' : (v.status == STATUS_REFUND ? 'label-success' : 'label-info')) ;
		rowHtml += "<td><span class='label " + className + "'>" + v.statusStr + "</span></td>";
		rowHtml += "<td>" + v.customerDisplayName + "</td>";
		rowHtml += "<td>" + v.total + "</td>";
		rowHtml += "</tr>";
		
		tableHtml += rowHtml; 
	} );
	
	tableHtml += '</tbody>';
	tableHtml += '<tfoot><tr>';
	tableHtml += '<th>Bill #</th>';
	tableHtml += '<th>Status</th>';
	tableHtml += '<th>Customer</th>';
	tableHtml += '<th>Amount</th>';
	tableHtml += '</tr></tfoot>';
	tableHtml += '</table>';
    
    $( "#bill-div" ).html( tableHtml );
	applyDataTable();
	
}

$(document).on('keydown', '#customerInfo', function() {
	findCustomerInfo(this);
});

function findCustomerInfo(obj) {
	
	if ($(obj).val() == '') {
		return;
	}
	$(obj).autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "GET", // Made Change
				contentType : "application/json",
				dataType : 'json',
				url : "findCustomerInfo.htm",
				data : {
					"input" : request.term
				},
				success : function(data) {
					$( ".ui-front" ).css( "font-size", 'x-large' );
					var array = $.map(data, function(item) {
						return {
							value : item["displayName"],
							data : item
						}
					});
					// call the filter here
					response(array);
				},
				failure : function() {
					showAlert("Cannot find records");
				}
			});
		},
		autoFocus : true,
		minLength : 1,
		select : function(event, ui) {
			var data = ui.item.data;
			setPopulatedCustoemrInfo(data, this);
		}
	});
}

function setPopulatedCustoemrInfo(data, obj) {
	var id = data["id"];
	if( id == undefined || id <= 0 ) {
		$( "#customerInfo" ).val( "" );
		return;
	}
	$( "#customer-info-hidden" ).data( "id", data["id"] );
	$( "#customer-info-hidden" ).data( "customername", data["customerName"] );
	$( "#customer-info-hidden" ).data( "phoneno", data["phoneNo"] );
	$( "#customer-info-hidden" ).data( "email", data["email"] );
	$( "#customerInfo" ).val( data["displayName"] );
	
}

postChangeDate( new Date( $( "#findBillsStartDate" ).val() ), new Date( $( "#findBillsEndDate" ).val() ) );