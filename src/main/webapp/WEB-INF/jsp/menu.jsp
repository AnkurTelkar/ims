<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <aside class="main-sidebar">
    
    <section class="sidebar">
<!--      <div class="sidebar-form input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="button" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>-->
      <ul class="sidebar-menu">

        <li class="active">
            <a href="<c:url value='/postLogin.htm' />">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
            <span class="pull-right-container">
            </span>
          </a>
        </li>
        <li class="treeview" style="display: none;">
          <a href="#">
            <i class="fa fa-book"></i> <span>Accounting</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
            <ul class="treeview-menu">
                <li>
                    <a href="#"><i class="fa fa-bank"></i>Banks
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-plus-circle"></i> Add Bank</a></li>
                        <li><a href="#"><i class="fa fa-list"></i> View Banks</a></li>
                        <li><a href="#"><i class="fa fa-search"></i> Search Banks</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-credit-card"></i>Payment Types
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-plus-circle"></i> Add Payment Type</a></li>
                        <li><a href="#"><i class="fa fa-list"></i> View Payment Types</a></li>
                        <li><a href="#"><i class="fa fa-search"></i> Search Payment Types</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-rupee"></i>Payments
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-plus-circle"></i> Add Payment</a></li>
                        <li><a href="#"><i class="fa fa-list"></i> View Payments</a></li>
                        <li><a href="#"><i class="fa fa-search"></i> Search Payments</a></li>
                    </ul>
                </li>
            </ul>
        </li>
        <li>
          <a href="#">
            <i class="fa fa-check"></i> <span>Check Availability</span>
            <span class="pull-right-container">
            </span>
          </a>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-cubes"></i> <span>Inventory</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
            <ul class="treeview-menu">
                <li>
                    <a href="#"><i class="fa fa-adjust"></i>Adjust Items
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-plus-circle"></i> Add Adjustment</a></li>
                        <li><a href="#"><i class="fa fa-list"></i> View Adjustments</a></li>
                        <li><a href="#"><i class="fa fa-search"></i> Search Adjustments</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-rocket"></i>Category
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="<c:url value='/inventory/categories/addCategory.htm'/>"><i class="fa fa-plus-circle"></i> Add Category</a></li>
                        <li><a href="<c:url value='/inventory/categories/listCategories.htm'/>"><i class="fa fa-list"></i> View Categories</a></li>
<!--                        <li><a href="#"><i class="fa fa-search"></i> Search Category</a></li>-->
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-bold"></i>Brands
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="<c:url value='/inventory/brands/addBrand.htm'/>"><i class="fa fa-plus-circle"></i> Add Brand</a></li>
                        <li><a href="<c:url value='/inventory/brands/listBrands.htm'/>"><i class="fa fa-list"></i> View Brands</a></li>
<!--                        <li><a href="#"><i class="fa fa-search"></i> Search Brands</a></li>-->
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-globe"></i>Units
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="<c:url value='/inventory/units/addUnit.htm'/>"><i class="fa fa-plus-circle"></i> Add Unit</a></li>
                        <li><a href="<c:url value='/inventory/units/listUnits.htm'/>"><i class="fa fa-list"></i> View Units</a></li>
<!--                        <li><a href="#"><i class="fa fa-search"></i> Search Units</a></li>-->
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-cube"></i>Items
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="<c:url value='/inventory/items/addItem.htm'/>"><i class="fa fa-plus-circle"></i> Add Item</a></li>
                        <li><a href="<c:url value='/inventory/items/listItems.htm'/>"><i class="fa fa-list"></i> View Items</a></li>
<!--                        <li><a href="#"><i class="fa fa-search"></i> Search Items</a></li>-->
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-cubes"></i>Item SKU
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="<c:url value='/inventory/skus/addSku.htm'/>"><i class="fa fa-plus-circle"></i> Add SKU</a></li>
                        <li><a href="<c:url value='/inventory/skus/listSkus.htm'/>"><i class="fa fa-list"></i> View SKUs</a></li>
<!--                        <li><a href="#"><i class="fa fa-search"></i> Search SKU</a></li>-->
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-tag"></i>Types
                      <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                      </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-plus-circle"></i> Add Type</a></li>
                        <li><a href="#"><i class="fa fa-list"></i> View Types</a></li>
                        <li><a href="#"><i class="fa fa-search"></i> Search Types</a></li>
                    </ul>
                </li>
                <li><a href="#"><i class="fa fa-cube"></i> Out Of Stock</a></li>
            </ul>
        </li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-cart-arrow-down"></i>
            <span>Receivings</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
          	<li><a href="<c:url value='/inventory/receivings/addReceiving.htm'/>"><i class="fa fa-plus-circle"></i> Create Receiving</a></li>
          	<li><a href="<c:url value='/inventory/receivings/listReceivings.htm'/>"><i class="fa fa-list"></i> View Receiving</a></li>
            <li><a href="<c:url value='/inventory/receivings/searchReceiving.htm'/>"><i class="fa fa-search"></i> Search Receiving</a></li>
          </ul>
        </li>
        
        <%-- <li class="treeview">
          <a href="#">
            <i class="fa fa-bar-chart-o"></i>
            <span>Sales</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<c:url value='/inventory/invoices/addInvoice.htm'/>"><i class="fa fa-plus-circle"></i> Create Invoice</a></li>
            <li><a href="<c:url value='/inventory/invoices/listInvoices.htm'/>"><i class="fa fa-list"></i> View Invoices</a></li>
            <li><a href="<c:url value='/inventory/invoices/searchInvoice.htm'/>"><i class="fa fa-search"></i> Search Invoice</a></li>
          </ul>
        </li> --%>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-users"></i>
            <span>Customer Mgmt</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<c:url value='/customers/addCustomer.htm'/>"><i class="fa fa-plus-circle"></i> Add Customer</a></li>
            <li><a href="<c:url value='/customers/listCustomers.htm'/>"><i class="fa fa-list"></i> View Customers</a></li>
<!--            <li><a href="#"><i class="fa fa-search"></i> Search Customers</a></li>-->
          </ul>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-user"></i>
            <span>User Mgmt</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<c:url value='/users/addUser.htm'/>"><i class="fa fa-plus-circle"></i> Add User</a></li>
            <li><a href="<c:url value='/users/listUsers.htm'/>"><i class="fa fa-list"></i> View Users</a></li>
			<li><a href="<c:url value='/users/listUsersRoles.htm'/>"><i class="fa fa-list"></i> Users Rights</a></li>
          </ul>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-user-secret"></i>
            <span>Vendor Mgmt</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
              
            <li><a href="<c:url value='/vendors/addVendor.htm'/>"><i class="fa fa-plus-circle"></i> Add Vendor</a></li>
            <li><a href="<c:url value='/vendors/listVendors.htm'/>"><i class="fa fa-list"></i> View Vendors</a></li>
<!--            <li><a href="#"><i class="fa fa-search"></i> Search Vendors</a></li>-->
          </ul>
        </li>
        <li class="treeview">
          <a href="<c:url value='/inventory/sales/pointOfSale/login.htm' />">
            <i class="fa fa-desktop"></i>
            <span>Point Of Sale</span>
          </a>
        </li>
        <li class="treeview">
          <a href="<c:url value='/inventory/sales/pointOfSale/saleSummary.htm' />">
            <i class="ion ion-stats-bars"></i>
            <span>Sales Summary</span>
          </a>
        </li>
        <jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate var="formattedDate" pattern="MM/dd/yyyy" value="${now}" />
		<jsp:useBean id="firstDayOfMonth" class="java.util.GregorianCalendar">${firstDayOfMonth.set(5,1)}</jsp:useBean>
		<fmt:formatDate var="formattedFirstDayOfMonth" pattern="MM/dd/yyyy" value="${firstDayOfMonth.time}"/>
        <li class="treeview">
          	<a href="<c:url value='/inventory/sales/pointOfSale/itemSaleDetails.htm?start=${formattedDate}&end=${formattedDate}' />">
            	<i class="ion ion-stats-bars"></i>
            	<span>Item Sale Details</span>
          	</a>
        </li>
        <li class="treeview">
          	<a href="<c:url value='/inventory/sales/pointOfSale/taxReport.htm?start=${formattedFirstDayOfMonth}&end=${formattedDate}' />">
            	<i class="ion ion-stats-bars"></i>
            	<span>Tax Report</span>
          	</a>
        </li>
      </ul>
      </section>
  </aside>