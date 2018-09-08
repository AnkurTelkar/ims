/**
 * 
 */

$( "#barcode" ).on( "blur", function() {
	var barcodeLength = parseInt( $( this ).val().length );
	if( barcodeLength < 4 ) {
		return;
	}
	var appendCharLength = 14 - barcodeLength;
	$( this ).val( new Array( appendCharLength ).join( "0" ) + $( this ).val() );
	$( "#code" ).val( $( this ).val().substring( 7, 12 ) );
} );

/*$( "#cost, #gst" ).on( "blur", function() {
	var gstPercent = parseFloat( $( "#gst" ).val() );
	var cost = parseFloat( $( "#cost" ).val() );
	var extendedCost = cost;
	if( gstPercent > 0 ) {
		extendedCost += ( cost * gstPercent / 100 );
	}
	$( "#extendedCost" ).val( extendedCost )
} );*/

$( "#discount, #retailPrice" ).on( "blur", function() {
	var discount = parseFloat( $( "#discount" ).val() );
	var retailPrice = parseFloat( $( "#retailPrice" ).val() );
	var sellingPrice = retailPrice;
	if( isNaN( discount ) || discount <= 0 ) {
		discount = 1;
	}
	
	if( isNaN( retailPrice ) ) {
		retailPrice = 0;
	}
	
	sellingPrice = parseFloat( retailPrice / discount ).toFixed( 2 );
	$( "#sellingPrice" ).val( sellingPrice )
} );