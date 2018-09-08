var i = $('#customerContactDetails').children().length;
$("#add-customer-contact-detail")
		.on(
				'click',
				function() {
					var html = '<div class="row">' +
            				'<input type="hidden" name="customerDetails[' + i + '].id" id="id_' + i + '" value="0">' +
            				'<label for="contactPerson" class="col-xs-1 control-label"><i class="fa fa-user"></i></label>' +
            				'<div class="col-xs-3">' +
            					'<form:errors path="customerDetails[' + i + '].contactPerson" cssClass="text-red" />' +
                    			'<input type="text" name="customerDetails[' + i + '].contactPerson" id="contact_person_' + i + '" class="form-control" autocomplete="off" value="" maxlength="30" placeholder="Contact Person">' +
							'</div>' +
                            '<label for="email" class="col-xs-1 control-label"><i class="fa fa-envelope" aria-hidden="true"></i> </label>' +
                            '<div class="col-xs-3">' +
                            '<form:errors path="customerDetails[' + i + '].email" cssClass="text-red" />' +
                                '<input type="email" name="customerDetails[' + i + '].email" id="email_' + i + '" class="form-control" autocomplete="off" value="" maxlength="50" placeholder="Email: Ex. 123@example.com">' +
                            '</div>' +
                            '<label for="phoneNo" class="col-xs-1 control-label"><i class="fa fa-phone" aria-hidden="true"></i></label>' +
                            '<div class="col-xs-3">' +
                            	'<form:errors path="customerDetails[' + i + '].phoneNo" cssClass="text-red" />' +
                                '<input type="number" name="customerDetails[' + i + '].phoneNo" id="phone_no_' + i + '" class="form-control" autocomplete="off" value="" maxlength="11" placeholder="Phone #: Ex.7772863424">' +
                            '</div>' +
           				'</div>';
					$('#customerContactDetails').append(html);
					i++;
				});

$("#add-customer-contact-detail").click();