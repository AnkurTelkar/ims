function toggleStatus(userId, allObj) {
	$('.user_right_' + userId).prop("checked", $(allObj).is(':checked'));
}

function getValues(userId, btnObj){

	var rights = new Array();
	var userObject = new Object();
	userObject.userId=userId;

	$('.user_right_'+userId).each(function(){
		if ($(this).is(':checked')) {
			rights.push($(this).val());		
		}			
	});
	userObject.userRightIds = rights;
	console.log(rights);
	console.log(JSON.stringify(userObject));
	$( btnObj ).attr("disabled", "disabled");
	$.ajax({
		type: "POST",
		contentType : 'application/json',
		dataType : 'json',
		url: "updateUserRights.htm",
		data: JSON.stringify(userObject), // Note it is important
		success :function(result) {
			$( ".message_" + userId ).parent().show();
			if( result ) {
				$( ".message_" + userId ).html( "Rights updated successfully" );
			} else {
				$( ".message_" + userId ).html( "Coult not save rights" );
			}

			setTimeout( function(){
				$( ".message_" + userId ).parent().fadeOut("slow");
			} , 1000);
			$( btnObj ).removeAttr("disabled");
		},

		error : function(result){
			$( ".message_" + userId ).parent().show();
			$( ".message_" + userId ).html( "Coult not save rights" );
			setTimeout( function(){
				$( ".message_" + userId ).parent().fadeOut("slow");
			} , 1000);
			$( btnObj ).removeAttr("disabled");
		}
	});
}