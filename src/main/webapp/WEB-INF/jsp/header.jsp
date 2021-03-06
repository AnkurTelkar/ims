<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true" %>

<header class="main-header">
        <a href="<c:url value='/postLogin.htm'/>" class="logo">
            <span class="logo-mini"><b>KNs</b></span>
            <span class="logo-lg"><b>KN</b>SALES</span>
        </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          
          <li class="dropdown notifications-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-bell-o"></i>
              <span class="label label-warning">10</span>
            </a>
            <ul class="dropdown-menu">
              <li class="header">You have 10 notifications</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-aqua"></i> 5 new members joined today
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the
                      page and may cause design problems
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-red"></i> 5 new members joined
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-user text-red"></i> You changed your loginId
                    </a>
                  </li>
                </ul>
              </li>
              <li class="footer"><a href="#">View all</a></li>
            </ul>
          </li>
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <%-- <img src="${request.contextPath}/resources/dist/img/user2-160x160.jpg" class="user-image" alt="User Image"> --%>
              <span class="hidden-xs text-capitalize"><c:out value="${loggedInUser.firstName}"></c:out> </span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
                <li class="user-header">
                    <%-- <img src="${request.contextPath}/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image"> --%>
                    <p>
                        <c:out value="${loggedInUser.loginId}"></c:out>
                        <small>
                            <span class="text-capitalize">
                                <c:out value="${loggedInUser.fullName}"></c:out>
                            </span>
                        </small>
                    </p>
                </li>
             
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Account</a>
                </div>
                <div class="pull-right">
                  <a href="<c:url value='/logout.htm' />" class="btn btn-default btn-flat">Sign out</a>
                </div>
              </li>
            </ul>
          </li>
          
        </ul>
      </div>
    </nav>
  </header>