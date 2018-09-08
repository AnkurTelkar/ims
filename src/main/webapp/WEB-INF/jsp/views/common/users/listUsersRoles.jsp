<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>

.form-group input[type="checkbox"] {
    display: none;
}

.form-group input[type="checkbox"] + .btn-group > label span {
    width: 10px;
    min-height: 18px;
}

.form-group input[type="checkbox"] + .btn-group > label span:first-child {
    display:none;
}
.form-group input[type="checkbox"] + .btn-group > label span:last-child {
    display: inline-block;   
}

.form-group input[type="checkbox"]:checked + .btn-group > label span:first-child {
    display: inline-block;
}
.form-group input[type="checkbox"]:checked + .btn-group > label span:last-child {
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

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Users with Rights</h1>
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
									<th width="70%">Rights</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach items="${users}" var="user" varStatus="status">
										<input type="hidden" name="userId" value="${user.userId}">
										<tr>
											<td align="center">${status.count}</td>
											<td data-original-title="${user.loginId }" data-container="body" data-toggle="tooltip" data-placement="bottom" title="">
												${user.firstName} ${user.lastName}</td>
											<td>
												<div class="alert alert-success message-dialog" style="display: none;">
													<span class="message_${user.userId}"></span>
												</div>
												<div class="form-group" >
													<input id="allowAll_${user.userId}" type="checkbox" name="allowAll" onchange="toggleStatus(${user.userId},this)" 
													${rights.size() eq user.userRights.size() ? "checked" : "" }>
													<div  class="btn-group">
	                                					<label for="allowAll_${user.userId}" class="btn btn-xs btn-default">
	                                    					<span class="glyphicon glyphicon-ok"></span>
	                                   						<span></span>
	                                					</label>
	                                					<label  for="allowAll_${user.userId}" class="btn btn-xs btn-default active custom-label">
	                                						Allow All
                                						</label>
					                                </div>
	                						    </div>
												<div class="scrollable">
												
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
														<div class="form-group" style="padding: 1px;">
														<input type="hidden" name="userRights[${status.count-1}].id" value="${userRightId}">
															<input id="user_right_${user.userId}_${status.count-1}" type="checkbox" name="userRights[${status.count-1}].right.id" class="user_right_${user.userId}"
															value="${rights.id}" 
															data-toggle="tooltip" data-placement="top"
															title="${rights.description}" ${checked}>
															<div  class="btn-group" data-original-title="${rights.label}" data-container="body" data-toggle="tooltip" data-placement="bottom" title="">
	                                							<label for="user_right_${user.userId}_${status.count-1}" class="btn btn-xs btn-primary">
	                                    							<span class="glyphicon glyphicon-ok"></span>
	                                   								<span></span>
	                                							</label>
	                                							 <label  for="user_right_${user.userId}_${status.count-1}" class="btn btn-xs btn-default active custom-label">
                                        							${rights.label}
                                								</label>
					                                		</div>
														</div>
													</c:forEach>
												</div>
											</td>
											<td class="text-center">
												<button type="button" title="update" onclick="getValues(${user.userId}, this);" class="btn btn-info" role="button">
													<i class="fa fa-refresh" aria-hidden="true"></i>
													<span class="hidden-xs">&nbsp;Update</span>
												</button>
											</td>
										</tr>
								</c:forEach>

							</tbody>
							<tfoot>
								<tr>
								<th>#</th>
									<th>Name</th>
									<th>Rights</th>
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