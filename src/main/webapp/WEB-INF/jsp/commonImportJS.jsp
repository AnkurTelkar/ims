<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>



<%--
<script src="${pageContext.servletContext.contextPath}/resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script> 
<script src="${pageContext.servletContext.contextPath}/resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/sparkline/jquery.sparkline.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/knob/jquery.knob.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/daterangepicker/moment.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/daterangepicker/daterangepicker.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/fastclick/fastclick.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/dist/js/app.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/dist/js/pages/dashboard.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/dist/js/demo.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/plugins/select2/select2.full.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/invoiceUtil.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/validate.min.js"></script> --%>
<tiles:importAttribute name="javascripts" />
<c:forEach var="javscript" items="${javascripts}">
	<script src="<c:url value="${javscript}"/>"></script>
</c:forEach>

<script>
	$(document).submit(function(e) {
		var pageURL = $(location).attr("href");
		var splits = pageURL.split("/");
		if (splits[splits.length - 1].indexOf("InvUnit.htm") >= 0) {
			var amount = $("#amount").val();
			if (isNaN(amount) || (parseFloat(amount) > parseFloat(1000000))) {
				e.preventDefault();
				$("#amountErr").html("Amount is either invalid or too large.");
				$("#amountErr").removeClass("hidden");
				return;
			}
		}
		$("button").attr("disabled", true);
		$('select').removeAttr('disabled');
	});

	function openModal(obj, url, title) {
		$("#myModal").modal();
		$("#modalTitle").html(title);
		$("#modalBody").load(url);
	}

	$(document).ready(function() {
		$(window).keydown(function(event) {
			if (event.keyCode == 13) {
				event.preventDefault();
				return false;
			}
		});
		
		$("button").each(function() {
			if ($(this).html() == "Cancel") {
				$(this).hide();
			}
		});

		$("[data-phoneno]").inputmask();
		showHideMessage();
	});
	
	function showHideMessage() {
		if( $( "#messageFH" ).html() && $( "#messageFH" ).html().length > 0 ) {
			$("#messageFH").closest( ".bb-alert" ).show();
			$("#messageFH").closest( ".bb-alert" ).fadeOut(10000);
		}
	}

	$(function() {
		$.widget.bridge('uibutton', $.ui.button);
		applyToolTip();
		applySlimScroll();
		applyDataTable();
		applyJqueryCombobox();
		applyDatePicker();
	});

	// It restrict the non-numbers
	var specialKeys = new Array();
	specialKeys.push(8, 46); // Backspace
	function IsNumeric(e) {
		var keyCode = e.which ? e.which : e.keyCode;
		console.log(keyCode);
		var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys
				.indexOf(keyCode) != -1);
		return ret;
	}

	function applyToolTip() {
		$('a').tooltip();
		$("[data-toggle='tooltip']").tooltip();
	}

	function applySlimScroll() {
		$('.scrollable').slimScroll({
			height : '70px'
		});
	}

	function applyDataTable() {
		$("#example1, #invSkuDetailsTable").DataTable();
		$( ".dataTables_length" ).parent( "div" ).removeClass( "col-sm-6" ).addClass( "col-xs-5" );
		/* $( ".dataTables_length" ).find( "label" ).html( $( ".dataTables_length" ).find( "select" ) ); */
		$( ".dataTables_filter" ).parent( "div" ).removeClass( "col-sm-6" ).addClass( "col-xs-7" );
		 $( ".dataTables_filter" ).find( "input" ).attr("placeholder", "Search");
		/*$( ".dataTables_filter" ).find( "label" ).html( $( ".dataTables_filter" ).find( "input" ) ); */
	}

	function applyJqueryCombobox() {
		$("select")
				.not(
						'[aria-controls="example1"], [aria-controls="invSkuDetailsTable"]')
				.combobox();
		$("a.custom-combobox-toggle")
				.removeClass("ui-corner-right")
				.removeAttr("data-original-title")
				.html(
						'<i class="fa pull-right fa-chevron-down fa-2x" aria-hidden="true"></i>');
		setTimeout(function() {
			$("input:visible:first").focus();
			$("input:visible:first").select();
		}, 100);
	}
	
	function applyDatePicker() {
		var date= new Date();
		$.fn.datepicker.defaults.format = "dd/mm/yyyy";
		/* $('.myDatePicker').each( function() {
			if( $(this).val() ) {
				date = new Date( $(this).val() );
				$( this ).datepicker( "setDate", date );
			} else {
				$( this ).datepicker();
			}
		} ); */
	}
	
	function showAlert( msg ) {
		bootbox.alert( msg );
	}
	
	function showAlertSmall( msg ) {
		bootbox.alert({
		    message: msg,
		    size: 'small'
		});
	}
	
	function showDialog( msg ) {
		var dialog = bootbox.dialog({
			message: "<h4><p class='text-center'>" + msg + "</p></h4>",
			closeButton: false
		});
		return dialog;
	}
	
	function showConfirm( title, msg ) {
		bootbox.confirm({
		    title: title,
		    message: msg,
		    buttons: {
		        cancel: {
		            label: '<i class="fa fa-times"></i> Cancel'
		        },
		        confirm: {
		            label: '<i class="fa fa-check"></i> OK'
		        }
		    },
		    callback: function (result) {
		    	callBackConfirm( result );
		    }
		});
	}
	
	function showKeyPad( title, selector, value ) {
		if( isNaN( value ) ) {
			value = "";
		}
		bootbox.confirm({
		    title: title,
		    message: '<div class="form-group rcorners1 keypadDiv" style="background: #367fa9; padding: 1%;">'
				+ '<input type="text" id="keypadValue" name="keypadValue" class="search_field" value="' + value + '" autofocus>'
				+ '<div id="keypad"></div></div>',
				size: 'small',
		    buttons: {
		        cancel: {
		            label: '<i class="fa fa-times"></i> Cancel'
		        },
		        confirm: {
		            label: '<i class="fa fa-check" id="okBtn"></i> OK'
		        }
		    },
		    callback: function (result) {
		    	if( result ) {
		    		callBackKeypad( selector, $( "#keypadValue" ).val() );	
		    	}
		    }
		});
		$('#keypad').jkeyboard({
		    layout: "float_numbers_only",
		    input: $('#keypadValue')
		});
		$( ".bootbox-body" ).css( "height", "22em" );
		setTimeout(function(){
			$('#keypadValue').focus();
			$('#keypadValue').select();
			$('#keypadValue').on( "keydown", function( event ) {
				if (event.keyCode == 13) {
					$("#okBtn").closest("button").click()
				}
			} );
        }, 1000);
		/* $( "#keypadValue" ).click( function() {
			$(this).select();
		} ); */
		//$('#keypadValue').focus();
		//$('#keypadValue').select();
	}
	
	function validateEmail( value ) {
		var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		return email_regex.test( value );
	}
	
	$('#daterange-btn').daterangepicker(
	        {
	          ranges: {
	            'Today': [moment(), moment()],
	            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	            'This Month': [moment().startOf('month'), moment().endOf('month')],
	            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	          },
	          startDate: moment().subtract(0, 'days'),
	          endDate: moment()
	        },
	        function (start, end) {
	          $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	          postChangeDate( start._d, end._d );
	        }
	    );
	
	function getBaseURL() {
		var newURL = window.location.protocol + "//" + window.location.host + "/";
		newURL += window.location.pathname.split('/')[1];
		return newURL;
	}
</script>