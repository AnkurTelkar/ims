
$(function() {
	$('#demo').num2words();
	$("#num").trigger( "change" );
	$("#amountInWords2").html( "<b>" + $("#amountInWords").html() + "</b>" );
	if( $("#num").val() <= 0 ) {
		$("#amountInWordDiv").hide();
	}
	//window.print();
});

window.onload = function () {
	window.print();
	setTimeout(function(){window.close();}, 1);
}