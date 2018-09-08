<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="wrapper">
		<div class="">
			<div class="bb-alert callout callout-danger" style="display: none;">
				<span id="messageFH">${message}</span>
    		</div>
			<section class="content">
				<div class="row">
					<c:url var="loginPointOfSale" value="/inventory/sales/pointOfSale/login.htm" />
					<form:form method="get" class="form-horizontal" action="${loginPointOfSale}" modelAttribute="">
        				<div class="row" style="padding-top: 10%;">
							<div class="col-xs-12 form-group">
								<input type="text" id="field0" name="field0" class="search_field">
    							<div id="keyboard"></div>
							</div>
						</div>	
					</form:form>
				</div>
			</section>
		</div>
	</div>
