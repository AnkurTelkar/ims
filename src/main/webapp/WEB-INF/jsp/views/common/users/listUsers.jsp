<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%-- <c:choose>
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
 --%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Users List</h1>
		<ol class="breadcrumb">
			<li><a href="<c:url value='/postLogin.htm'/>"><i
					class="fa fa-home"></i> Home</a></li>
			<li><a href="#"><i class="fa fa-user-secret"></i> User</a></li>
			<li class="active"><i class="fa fa-list"></i> <span>Show
					All Users</span></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
					<!-- /.box-header -->
					<div class="box-body table-responsive">
						<table id="example1"
							class="table table-bordered table-striped ellipsis">
							<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th>Login Id</th>
									<th>Phone</th>
									<th>User Code</th>
									<th>Email</th>
									<th>Role</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="user" varStatus="status">
									<tr>
										<td align="center">${status.count}</td>
										<td>${user.firstName}${user.lastName}</td>
										<td>${user.loginId}</td>
										<td>${user.phoneNumber}</td>
										<td>${user.userCode}</td>
										<td>${user.email}</td>
										<td><c:choose>
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
											${role}
										</td>

										<td><c:choose>
												<c:when test="${user.status eq 1}">
													<c:set var="isActive" value="Active" />
												</c:when>
												<c:when test="${user.status eq 0}">
													<c:set var="isActive" value="Inactive" />
												</c:when>
											</c:choose>
											${isActive}</td>

										<td class="text-center"><a
											title="View User: ${user.firstName}"
											href="<c:url value='/users/${user.userId}/viewUser.htm'/>"
											class="btn btn-primary" role="button"><i
												class="fa fa-list" aria-hidden="true"></i></a> <a
											title="Edit User: ${user.firstName}"
											href="<c:url value='/users/${user.userId}/editUser.htm'/>"
											class="btn btn-info" role="button"><i class="fa fa-edit"
												aria-hidden="true"></i></a></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th>Login Id</th>
									<!-- <th>Password</th> -->
									<th>Phone</th>
									<th>User Code</th>
									<!-- <th>Date of Birth</th> -->
									<th>Email</th>
									<th>Role</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
							</tfoot>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->
</div>