<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${categoryDto.categoryId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Category</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Category</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Category</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Category</h3>
            </div>
                <c:url var="addCategory" value="/inventory/categories/addCategory.htm"/>
                <form:form method="post" class="form-horizontal" action="${addCategory}" modelAttribute="category" >
                    <div class="box-body">
                        <div class="form-group">
                            <label for="categoryCode" class="col-sm-2 control-label">Code</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="categoryId" name="categoryId" value="${categoryDto.categoryId}">
                                <form:errors path="categoryCode" cssClass="text-red" />
                                <input type="text" class="form-control" id="categoryCode" name="categoryCode" value="${categoryDto.categoryCode}" placeholder="Code" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="categoryName" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-4">
                                <form:errors path="categoryName" cssClass="text-red" />
                                <input type="text" class="form-control" id="categoryName" name="categoryName" value="${categoryDto.categoryName}" placeholder="Name" maxlength="20">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-sm-2 control-label">Description</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="description" name="description" value="${categoryDto.description}" placeholder="Description" maxlength="50">
                            </div>
                        </div>
                    </div>
                    <div class="box-footer text-right">
                        <button type="submit" class="btn btn-info">${label}</button>
                        <button type="submit" class="btn btn-default">Cancel</button>
                    </div>
                </form:form>
            </div>
    </section>
</div>
