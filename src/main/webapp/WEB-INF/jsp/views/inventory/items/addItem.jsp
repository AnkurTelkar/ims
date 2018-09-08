<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${itemDto.itemId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Item</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Item</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Item</span></li>
        </ol>
    </section>
    <c:if test="${not empty message}">
        <div class="pad margin no-print">
            <div class="callout callout-warning" style="margin-bottom: 0!important;">
                <h5><i class="fa fa-exclamation-triangle" aria-hidden="true"></i> <em>${message}</em></h5>
            </div>
        </div>
    </c:if>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Item</h3>
            </div>
                <c:url var="addItem" value="/inventory/items/addItem.htm"/>
                <form:form method="post" class="form-horizontal" action="${addItem}" modelAttribute="item" >
                    <div class="box-body">
                        <div class="form-group">
                            <label for="itemCode" class="col-sm-2 control-label">Code</label>
                            <div class="col-sm-4">
                                <form:errors path="itemCode" cssClass="text-red" />
                                <input type="hidden" id="itemId" name="itemId" value="${itemDto.itemId}">
                                <input type="text" class="form-control" id="itemCode" name="itemCode" value="${itemDto.itemCode}" placeholder="Item Code" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="categoryId" class="col-sm-2 control-label">Category</label>
                            <div class="col-sm-4">
                                <select class="form-control js-example-basic-single" id="category.categoryId" name="category.categoryId" >
                                    <c:forEach items="${invCategories}" var="category">
                                        <option value="${category.categoryId}" ${category.categoryId eq itemDto.category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="brandId" class="col-sm-2 control-label">Brand</label>
                            <div class="col-sm-4">
                                <select class="form-control js-example-basic-single" id="brand.brandId" name="brand.brandId" >
                                    <c:forEach items="${invBrands}" var="brand">
                                        <option value="${brand.brandId}" ${brand.brandId eq itemDto.brand.brandId ? 'selected' : ''}>${brand.brandName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                        	<label for="name" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-4">
                                <form:errors path="itemName" cssClass="text-red" />
                                <input type="text" class="form-control" id="itemName" name="itemName" value="${itemDto.itemName}" placeholder="Name" maxlength="20">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="trackUnit" class="col-sm-2 control-label">Track Unit</label>
                            <div class="col-sm-4">
                                <select class="form-control text-capitalize" id="trackUnit.unitId" name="trackUnit.unitId">
                                    <c:forEach items="${invUnits}" var="trackUnit">
                                        <option value="${trackUnit.unitId}" ${trackUnit.unitId eq itemDto.trackUnit.unitId ? 'selected' : ''}>${trackUnit.unitName}</option>
                                    </c:forEach>
                                </select>
                            </div> 
                            <%-- <label for="packUnit" class="col-sm-2 control-label">Pack Unit</label>
                            <div class="col-sm-4">
                            	<select class="form-control text-capitalize" id="packUnit" name="packUnit">
                                    <c:forEach items="${invUnits}" var="packUnitId">
                                        <option value="${packUnit.unitId}" ${packUnit.unitId eq itemDto.packUnit.packUnitId ? 'selected' : ''}>${packUnit.unitName}</option>
                                    </c:forEach>
                                </select>
                            </div> --%>
                        </div>                        
                        <div class="form-group">
                            <label for="status" class="col-sm-2 control-label">Status</label>
                            <div class="col-sm-4">
                                <select class="form-control text-capitalize" id="status" name="status" >
                                	<option value="1" ${itemDto.status eq 1 ? 'selected' : ''}>Active</option>
                                    <option value="0" ${itemDto.status eq 0 ? 'selected' : ''}>Inactive</option>
                                </select>
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
