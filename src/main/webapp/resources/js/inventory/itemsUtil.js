$( ".sidebar-mini" ).addClass( "sidebar-collapse" );


var addedRows = 0;
var itemId = 0;
$( "#add-sku-row" ).click( function() {
	console.log( "Adding new sku row" );
	
	populateSkuCode( addedRows );
} );

$( "#update" ).click( function() {
	var skuDtoList = new Array();
	
	console.log( "Add/Update SKUs" );
	$( "#skuListDiv" ).find( ".form-group" ).each( function() {
		var skuDto = new Object();
		$( this ).find( "input" ).each( function() {
			if( $(this).val().length == 0 && $( this ).data( "identifier" ) != "description" ) {
				$(this).val( 0.00 );
			}
			skuDto[ $( this ).data( "identifier" ) ] = $(this).val();
			
			
		} );
		skuDtoList.push( skuDto );
	} );
	updateSkus(skuDtoList);
} );

function populateSkuCodeCallBack( code ) {
	var $skuRow = $( "#skuListDiv" ).find( ".form-group" ).last().clone();
	$skuRow.find( "input" ).val( "" );
	$skuRow.find( ".skuCode" ).val( code );
	$skuRow.find( ".barcode" ).val( code );
	$( "#skuListDiv" ).append( $skuRow );
	addedRows++;
}

function populateSkuCode( addedRows ) {
	
	$.ajax({
		type : "GET", // Made Change
		contentType : "application/json",
		dataType : 'json',
		url : "../../skus/getNextSkuCode.htm",
		data : {
			"input" : addedRows 
		},
		success : function(data) {
			populateSkuCodeCallBack( data.skuCode );
		},
		failure : function(data) {
			showAlert( "Unable to assign sku Code" );
		},
		error : function(data) {
			showAlert( "Unable to assign sku code due to some error!" );
		}
	});
	
}

function updateSkus( skuDtoList ) {
	var input = JSON.stringify( skuDtoList );
	$.ajax({
		type : "GET", // Made Change
		contentType : "application/json",
		dataType : 'json',
		url : "../../skus/updateSkus.htm",
		data : {
			"input" : input,
			"itemId": $( "#itemId" ).val()
		},
		success : function(data) {
			bootbox.alert("Items Updated Successfully!", function(){ 
				if( data.result == "true" ) {
					location.href = "../" + $( "#itemId" ).val() + "/viewItem.htm";
				} 
			});
		},
		failure : function(data) {
			showAlert( data.message );
		},
		error : function(data) {
			showAlert( data.message );
		}
	});
	
}
function populateSellingPrice( obj ) {
	var discount = parseFloat( $( obj ).val() ).toFixed( 2 );
	var retailPrice = parseFloat( $( obj ).closest( ".form-group" ).find( ".retailPrice" ).val() ).toFixed( 2 );
	if( isNaN( retailPrice ) ) {
		retailPrice = 0;
	}
	
	var sellingPrice = retailPrice;
	if( discount > 0 ) {
		sellingPrice = retailPrice / discount;
	}
	sellingPrice = parseFloat( sellingPrice ).toFixed( 2 );
	$( obj ).closest( ".form-group" ).find( ".sellingPrice" ).val( sellingPrice );
}