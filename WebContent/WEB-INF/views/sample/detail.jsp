<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/inc-import.jsp"%>

<!DOCTYPE html>
<html lang="ko">
  <head>
  	<title>sample</title>
  	<jsp:include page="../common/inc-header.jsp" />
  	
  	<!-- custom css -->
  	<link href="/resources/css/detail.css" rel="stylesheet" />
  	
  </head>



  <body>
    <div id="wrapper">
		<div id="page-wrapper" >
		
			<!-- header -->
	        <div class="row">
	            <div class="col-lg-12">
	                <h1 class="page-header">DETAIL: test</h1>
	            </div>
	            <!-- /.col-lg-12 -->
	        </div>
	        
	        <jsp:include page="../common/inc-back-button.jsp" />
        
        	<!-- contents -->
        	<div class="row" >
                <div class="col-lg-6">
                    <div class="panel panel-info">
                        <div class="panel-heading">
							결과
                        </div>
                        <div class="panel-body">
                               <div class="row">
                                   <label class="col col-lg-3">id</label>
                                   <div class="col col-lg-3 text-right">${result.id}</div>
                                   <label class="col col-lg-3" >name</label>
                                   <div class="col col-lg-3" >${result.name}</div>
                               </div>
                               
                        </div>
                    </div>
                </div>
	        </div>
        	

        
        
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->



	<!-- scripts && footer -->
	<jsp:include page="../common/inc-footer.jsp" />
	
  </body>
</html>