<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.form-group input[type="checkbox"] {
	display: none;
}

.form-group input[type="checkbox"]+.btn-group>label span {
	width: 10px;
	min-height: 18px;
}

.form-group input[type="checkbox"]+.btn-group>label span:first-child {
	display: none;
}

.form-group input[type="checkbox"]+.btn-group>label span:last-child {
	display: inline-block;
}

.form-group input[type="checkbox"]:checked+.btn-group>label span:first-child
	{
	display: inline-block;
}

.form-group input[type="checkbox"]:checked+.btn-group>label span:last-child
	{
	display: none;
}

.custom-label {
	/* display:block;
    width:30px;
    word-wrap:break-word;
    width: 50%;*/
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}
</style>

<c:choose>
	<c:when test="${user.userId gt 0}">
		<c:set var="label" value="Update" />
	</c:when>
	<c:otherwise>
		<c:set var="label" value="Save" />
	</c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
	<section class="content-header">
		<h1>${label}User</h1>
		<ol class="breadcrumb">
			<li><a href="<c:url value='/postLogin.htm'/>"><i
					class="fa fa-home"></i> Home</a></li>
			<li class="active"><i class="fa fa-plus"></i> <span>${label}
					User </span></li>
		</ol>
	</section>
	<c:if test="${not empty message}">
		<div class="pad margin no-print">
			<div class="alert alert-success fade in">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				${message}
			</div>
		</div>
	</c:if>
	<section class="content">
		<div class="box box-info">
			<div class="box-header with-border">
				<!-- <h3 class="box-title">Add User</h3> -->
			</div>
			<c:url var="addUser" value="/users/addUser.htm" />
			<form:form method="post" class="form-horizontal" action="${addUser}"
				modelAttribute="user">
				<div class="box-body">

					<div class="form-group">

						<!-- <label for="userCode" class="col-sm-2 control-label">User Id</label> -->
						<%-- <div class="col-sm-4">							
							<form:errors path="userId" cssClass="text-red" /> --%>
						<input type="hidden" class="form-control" id="userId"
							name="userId" value="${user.userId}" readonly autocomplete="off">
						<!-- </div> -->


						<label for="userCode" class="col-sm-2 control-label">User
							Code</label>
						<div class="col-sm-4">
							<form:errors path="userCode" cssClass="text-red" />
							<input type="text" class="form-control" id="userCode"
								name="userCode" value="${user.userCode}" placeholder="User Code"
								readonly autocomplete="off">
						</div>

						<label for="loginId" class="col-sm-2 control-label">Login
							Id</label>
						<div class="col-sm-4">
							<form:errors path="loginId" cssClass="text-red" />
							<input type="text" class="form-control" id="loginId" min="5"
								maxlength="8" name="loginId" value="${user.loginId}"
								placeholder="Login Id" autocomplete="off">
						</div>
					</div>

					<div class="form-group">
						<label for="firstName" class="col-sm-2 control-label">First
							Name</label>
						<div class="col-sm-4">
							<form:errors path="firstName" cssClass="text-red" />
							<input type="text" class="form-control" id="firstName"
								name="firstName" value="${user.firstName}"
								placeholder="First Name" autocomplete="off" min="5"
								maxlength="25">
						</div>
						<label for="lastName" class="col-sm-2 control-label">Last
							Name</label>
						<div class="col-sm-4">
							<form:errors path="lastName" cssClass="text-red" />
							<input type="text" class="form-control" id="lastName"
								name="lastName" value="${user.lastName}" placeholder="Last Name"
								autocomplete="off" min="5" maxlength="25">
						</div>
					</div>

					<div class="form-group">
						<label for="dateOfBirth" class="col-sm-2 control-label">Date of Birth</label>
						<div class="col-sm-4">
							<form:errors path="dateOfBirth" cssClass="text-red" />
							<div class="input-group">
								<input type="text" readonly class="form-control myDatePicker" id="dateOfBirth" name="dateOfBirth" value="${user.dateOfBirth}">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
						<label for="phoneNumber" class="col-sm-2 control-label">Phone
							Number</label>
						<div class="col-sm-4">
							<form:errors path="phoneNumber" cssClass="text-red" />
							<input type="number" class="form-control" id="phoneNumber"
								name="phoneNumber" value="${user.phoneNumber}"
								placeholder="Phone Number" autocomplete="off" maxlength="11">
						</div>
					</div>

					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-4">
							<form:errors path="email" cssClass="text-red" />
							<input type="email" class="form-control" id="email" name="email"
								value="${user.email}" placeholder="Email" maxlength="50"
								autocomplete="off" min="5" maxlength="30">
						</div>


						<label for="password" class="col-sm-2 control-label">Password</label>
						<div class="col-sm-4">
							<%-- <form:errors path="phoneNumber" cssClass="text-red" /> --%>
							<input type="password" class="form-control" id="password"
								name="password" value="${user.password}" placeholder="Password"
								autocomplete="off" min="6" maxlength="6">
						</div>
					</div>

					<div class="form-group">
						<label for="status" class="col-sm-2 control-label">Status</label>
						<div class="col-sm-4">
							<select name="status" class="selectpicker">
								<option value=1>Active</option>
								<option value=0>Inactive</option>
							</select>
						</div>
						<label for="role" class="col-sm-2 control-label">Role</label>
						<div class="col-sm-4">
							<select name="userRole" class="selectpicker">
								<option value=1>Admin</option>
								<option value=2>Employee</option>
								<option value=3>Manager</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="address" class="col-sm-2 control-label">Address</label>
						<div class="col-sm-10">
							<form:errors path="address" cssClass="text-red" />
							<textarea class="form-control" id="address" name="address"
								placeholder="Address" maxlength="255" maxlength="60">${user.address}</textarea>
						</div>
					</div>
				</div>
				<div class="box-header with-border">
					<h3 class="box-title">Rights</h3>
				</div>
				<div class="box-body">
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
						<div class="col-sm-4 form-group">
							<input type="hidden" name="userRights[${status.count-1}].id"
								value="${userRightId}"> <input type="checkbox"
								id="userRights[${status.count-1}].right.id"
								name="userRights[${status.count-1}].right.id"
								value="${rights.id}" ${checked}>
							<div class="btn-group" data-original-title="${rights.label}"
								data-container="body" data-toggle="tooltip"
								data-placement="bottom" title="">
								<label for="userRights[${status.count-1}].right.id"
									class="btn btn-xs btn-primary"> <span
									class="glyphicon glyphicon-ok"></span> <span></span>
								</label> <label for="userRights[${status.count-1}].right.id"
									class="btn btn-xs btn-default active custom-label">
									${rights.label} </label>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="box-footer text-right">
					<button type="submit" class="btn btn-info" data-btnsaveorupdate>${label}</button>
					<!-- <button type="submit" class="btn btn-default">Cancel</button>-->
				</div>

			</form:form>
		</div>
	</section>
</div>