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
	                <h1 class="page-header">LIST: ${stage} / ${type}</h1>
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
                                        <th>베팅금액</th>
                                        <th>지급액</th>
                                        <th>지급률(%)</th>
                                        <th>승률(%)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach var="item" items="${result}">
                                    	<c:url var="detailUrl" value="/detail/${stage}/${type}/${item.seq}" />
                                    
	                                    <tr onclick="goUrl('${detailUrl}');" >
	                                        <td>${func:formatDateTime(item.registerTime)}</td>
	                                        <td>${item.username}</td>
	                                        <td class="text-right">${func:formatNumber(item.playGameCount)}</td>
	                                        <td class="text-right">${func:formatNumber(item.totalBet)}</td>
	                                        <td class="text-right">${func:formatNumber(item.payout)}</td>
	                                        <td class="text-right">${func:formatPercent(item.payoutRate)}</td>
	                                        <td class="text-right">${func:formatPercent(item.winGameRate)}</td>
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
    
    <!-- excel upload -->
    <jsp:include page="./inc-run-game-component-js.jsp" >
    	<jsp:param name="stage" value="${stage}" />
    	<jsp:param name="type" value="${type}" />
    </jsp:include>
	
  </body>
</html>