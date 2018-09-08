$('#keyboard').jkeyboard({
    layout: "english",
    input: $('#field0')
});

$( ".wrapper" ).css( "background", "#367fa9" );
$( ".wrapper" ).height( $( document ).height() - ( $( document ).height()*6/100 ) );
$( ".return" ).html( "Login" );

$( "#field0" ).on( "keyup", function( e ) {
	if( e.keyCode == 13 ) {
		$( ".return" ).click();
	}
} );