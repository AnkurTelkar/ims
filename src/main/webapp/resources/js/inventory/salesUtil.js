function updateBillCallback( data, rowId ) {
	$( "#" + rowId ).find('.paid').html( $( "#" + rowId ).find('.balance').html() );
	$( "#" + rowId ).find('.balance').html( 0.00 );
	$( "#" + rowId ).find('.action').html( data.message );
	$( "#" + rowId ).find('.statusStr').html( "<span class='label label-info'>Finalize</span>" );
}

function updateBill( paymentType, rowId, billId ) {
	
	$.ajax({
		type : "GET", // Made Change
		contentType : "application/json",
		dataType : 'json',
		url : "../finalizeBill.htm",
		data : {
			"paymentType" : paymentType,
			"billId" : billId
		},
		success : function(data) {
			updateBillCallback( data, rowId );
		},
		failure : function(data) {
			showMesasge( "Unable to Update Bill. Process Failed!" );
		},
		error : function(data) {
			showMesasge( "Unable to Update Bill due to some error!" );
		}
	});
	
}