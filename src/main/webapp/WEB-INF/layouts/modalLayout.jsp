<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">  

<%@page pageEncoding="UTF-8" %>
  
    <head>  
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
    </head>  

    <div style="">  
        <tiles:insertAttribute name="body" />
    </div>
    
<script>
$(function () {
    $.widget.bridge('uibutton', $.ui.button);
    $('a').tooltip();   
    $("#invSkuDetailsTable").DataTable();
    $( ".dataTables_wrapper" ).width( "99%" );
//    $('#example1').DataTable({
//      "paging": true,
//      "lengthChange": false,
//      "searching": true,
//      "ordering": true,
//      "info": true,
//      "autoWidth": false,
//      :
//    });

   
  });
    </script>