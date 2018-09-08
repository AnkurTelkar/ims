<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:choose>
	<c:when test="${user.userRole eq 1}">
		<c:set var="role" value="Admin" />
	</c:when>

	<c:when test="${user.userRole eq 2}">
		<c:set var="role" value="Employee" />
	</c:when>

	<c:when test="${user.userRole eq 3}">
		<c:set var="role" value="Manager" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${user.status eq 0}">
		<c:set var="status" value="Inactive" />
	</c:when>
	<c:otherwise>
		<c:set var="status" value="Active" />
	</c:otherwise>
</c:choose>


<div class="content-wrapper" style="min-height: 450px;">
	<section class="content-header">
		<h1>View User</h1>
		<ol class="breadcrumb">
			<li><a href="<c:url value='/postLogin.htm'/>"><i
					class="fa fa-home"></i> Home</a></li>
			<li><a href="#"><i class="fa fa-user-secret"></i> User</a></li>
			<li class="active"><i class="fa fa-plus"></i> <span>View
					User</span></li>
		</ol>
	</section>
	<c:if test="${not empty message}">
		<div class="pad margin no-print">
			<div class="callout callout-info"
				style="margin-bottom: 0 !important;">
				<h5>
					<i class="fa fa-check"></i> <em>${message}</em>
				</h5>
			</div>
		</div>
	</c:if>

	<section class="content">
		<div class="box box-primary">
			<div class="box-header">
				<h2 class="page-header">
					<i class="fa fa-user-secret"></i> User Id: ${user.userCode}
				</h2>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="col-sm-6">
					<b>Login:</b> ${user.loginId}<br><br> 
					<b>First Name: </b>${user.firstName} <br><br> 
					<b>Phone :</b> ${user.phoneNumber} <br><br> 
					<b>Status:</b> ${status} <br><br>
					<b>Date of Birth:</b> <fmt:formatDate type="date" dateStyle="medium" timeStyle="medium" value="${user.dateOfBirth}" /><br> <br><br>
					
				</div>
				<div class="col-sm-6">
					<b>User Code:</b> ${user.userCode}<br><br> 
					<b>Last Name:</b> ${user.lastName} <br><br>
					<b>Email:</b> ${user.email}<br><br> 
					<b>Role:</b> ${role} <br><br>
					<b>Address:</b> ${user.address} <br><br>
				</div>
			</div>
				<div class="box-header with-border">
					<h3 class="box-title">Rights</h3>
				</div>
			<div class="box-body">
			<c:if test="${empty rights}">
				<h4>
						<em>This user have no rights.</em>
				</h4>
			</c:if>
					<c:forEach items="${rights}" var="rights" varStatus="status">
						<c:set var="checked" value="" />
						<c:set var="userRightId" value="" />
						<c:forEach items="${user.userRights}" var="userRight">
							<c:choose>
								<c:when test="${userRight.right.id eq rights.id}">
									<c:set var="checked" value="checked" />
									<c:set var="userRightId" value="${userRight.id}" />
								</c:when>
							</c:choose>
						</c:forEach>
						<div class="btn-group" data-original-title="${rights.right.label}"
								data-container="body" data-toggle="tooltip"
								data-placement="bottom" title="">
								<label
									class="btn btn-xs btn-default active custom-label">
									${rights.right.label} </label>
							</div>
					</c:forEach>
				</div>

			<div class="box-footer text-right">
				<a href="<c:url value='/users/addUser.htm' />"
					class="btn btn-primary" role="button">Add New</a> <a
					href="<c:url value='/users/${user.userId}/editUser.htm' />"
					class="btn btn-info" role="button">Edit</a>
				<!--                <button type="button" class="btn btn-default">Cancel</button>-->
			</div>
		</div>
	</section>
</div>