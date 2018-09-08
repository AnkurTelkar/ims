<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${brandDto.brandId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Brand</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Brand</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Brand</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Brand</h3>
            </div>
                <c:url var="addBrand" value="/inventory/brands/addBrand.htm"/>
                <form:form method="post" class="form-horizontal" action="${addBrand}" modelAttribute="brand" >
                    <div class="box-body">
                        <div class="form-group">
                            <label for="brandCode" class="col-sm-2 control-label">Code</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="brandId" name="brandId" value="${brandDto.brandId}">
                                <form:errors path="brandCode" cssClass="text-red" />
                                <input type="text" class="form-control" id="brandCode" name="brandCode" value="${brandDto.brandCode}" placeholder="brandCode" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="brandName" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-4">
                                <form:errors path="brandName" cssClass="text-red" />
                                <input type="text" class="form-control" id="brandName" name="brandName" value="${brandDto.brandName}" placeholder="brandName" maxlength="20" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-sm-2 control-label">Description</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="description" name="description" value="${brandDto.description}" placeholder="Description" maxlength="50" autocomplete="off">
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
