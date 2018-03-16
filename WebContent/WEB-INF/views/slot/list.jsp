<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/inc-import.jsp"%>

<!DOCTYPE html>
<html lang="ko">
  <head>
  	<title>vSlot-Simulator</title>
  	<jsp:include page="../common/inc-header.jsp" />
  	
  	<!-- custom -->
  	<style type="text/css">
  		/* custom hover color && clickable css */
		.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
		  background-color: #d9edf7;
		  cursor:pointer;
		}
  	</style>
  </head>



  <body>
    <div id="wrapper">
		<div id="page-wrapper" >
		
			<!-- header -->
	        <div class="row">
	            <div class="col-lg-12">
	                <h1 class="page-header">LIST</h1>
	            </div>
	            <!-- /.col-lg-12 -->
	        </div>
	        
	        <jsp:include page="../common/inc-back-button.jsp" />
        
        	<!-- excel upload -->
        	<jsp:include page="./inc-run-game-component-ui.jsp" />
        
        	<!-- contents -->
        	<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            History
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-history">
                                <thead>
                                    <tr>
                                        <th>시간</th>
                                        <th>사용자</th>
                                        <th>실행횟수</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach var="item" items="${result}">
                                    	<c:url var="detailUrl" value="/detail" />
                                    
	                                    <tr onclick="goUrl('${detailUrl}');" >
	                                        <td>ddd</td>
	                                        <td>aaa</td>
	                                        <td class="text-right">ddd</td>
	                                    </tr>
                                    </c:forEach>
                               
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
	        
        
        
        
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->



	<!-- scripts && footer -->
	<jsp:include page="../common/inc-footer.jsp" />
	
    <!-- DataTables JavaScript -->
    <script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>
	
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-history').DataTable({
            responsive: true
            ,"order": [[ 0, "desc" ]]
        });
    });
    
    function goUrl(url) {
    	window.location.href = url;
    }
    
    </script>
	
  </body>
</html>