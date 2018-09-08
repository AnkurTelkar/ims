<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <div class="content-wrapper">

    <section class="content-header">
        <h1>Dashboard</h1>
        <ol class="breadcrumb">
            <li><a href="<c:url value='/postLogin.htm'/>"><i class="fa fa-home"></i> Home</a></li>
            <li class="active"><i class="fa fa-dashboard"></i> <span>Dashboard</span></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <!-- Small boxes (Stat box) -->
      <div class="row">
        <!-- <div class="col-xs-4">
          small box
          <div class="small-box bg-aqua">
            <div class="inner">
              <h3>150</h3>

              <p>Pending Orders</p>
            </div>
            <div class="icon">
              <i class="ion ion-bag"></i>
            </div>  
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div> -->
        <!-- ./col -->
        <div class="col-xs-4">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3>${dashboardDto.salesToday }</h3>

              <p>Sales Today</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-xs-4">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3>${dashboardDto.purchasesToday }</h3>

              <p>Purchases Today</p>
            </div>
            <div class="icon">
              <i class="fa fa-cart-plus"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-xs-4">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h3>${dashboardDto.outOfStock }</h3>

              <p>Out of Stock</p>
            </div>
            <div class="icon">
              <i class="fa fa-cube"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
      </div>
      <!-- /.row -->
      <!-- Main row -->
      <div class="row">
        <!-- Left col -->
        <section class="col-sm-12">
          <!-- TO DO List -->
          <div class="box box-primary">
            <div class="box-header">
              <i class="ion ion-clipboard"></i>
              <h3 class="box-title">Top Selling Items</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <c:forEach items="${dashboardDto.topSkus}" var="topSku"  varStatus="status">
            <ul class="todo-list">
                <li>
                  <span class="text">${topSku[0] } <span class="badge">Sold: ${topSku[1] } </span></span>
                </li>
              </ul>
            </c:forEach>
            </div>
          </div>

        </section>
      </div>
      
    </section>
    <!-- /.content -->
  </div>



