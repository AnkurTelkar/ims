<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${unitDto.unitId gt 0}"><c:set var="label" value="Update" /></c:when>    
    <c:otherwise><c:set var="label" value="Save" /></c:otherwise>
</c:choose>
<div class="content-wrapper" style="min-height: 450px;">
    <section class="content-header">
        <h1>${label} Unit</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li><a href="#"><i class="fa fa-user-secret"></i> Unit</a></li>
            <li class="active"><i class="fa fa-plus"></i> <span>${label} Unit</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Add Unit</h3>
            </div>
                <c:url var="addUnit" value="/inventory/units/addUnit.htm"/>
                <form:form method="post" class="form-horizontal" action="${addUnit}" modelAttribute="unitDto" >
                    <div class="box-body">
                        <div class="form-group">
                            <label for="unitName" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-4">
                                <form:errors path="unitName" cssClass="text-red" />
                                <input type="hidden" id="unitId" name="unitId" value="${unitDto.unitId}">
                                <input type="text" class="form-control" id="unitName" name="unitName" value="${unitDto.unitName}" placeholder="Name" maxlength="20">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="amount" class="col-sm-2 control-label">Amount</label>
                            <div class="col-sm-4">
                                <form:errors path="amount" cssClass="text-red" />
                                <span id="amountErr" class="text-red hidden" ></span>
                                <input type="number" class="form-control" id="amount" name="amount" value="${unitDto.amount}" placeholder="Amount" min="0" max="1000">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="measuringUnit.id" class="col-sm-2 control-label">Basic Unit</label>
                            <div class="col-sm-10">
                                <select class="form-control text-capitalize js-example-basic-single" id="measuringUnit.id" name="measuringUnit.id" >
                                    <c:forEach items="${measuringUnitDtoList}" var="measuringUnitDto">
                                        <option value="${measuringUnitDto.id}" ${measuringUnitDto.id eq unitDto.measuringUnit.id ? 'selected' : ''}>${measuringUnitDto.name}</option>
                                    </c:forEach>
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