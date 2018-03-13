<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/inc-import.jsp"%>

<!DOCTYPE html>
<html lang="ko">
  <head>
  	<title>vSlot-Simulator</title>
  	<jsp:include page="../common/inc-header.jsp" />
  </head>



  <body>
    <div id="wrapper">
		<div id="page-wrapper" >
		
			<!-- header -->
	        <div class="row">
	            <div class="col-lg-12">
	                <h1 class="page-header">MAIN</h1>
	            </div>
	            <!-- /.col-lg-12 -->
	        </div>
	        
        	<!-- contents -->
        	<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Basic
                        </div>
                        <div class="panel-body">
							<a href="/list/BASIC/SIMULATOR" class="btn btn-outline btn-primary">simulator</a>
                        </div>
                    </div>
                </div>
	        </div>
	        
        	<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Stage01
                        </div>
                        <div class="panel-body">
							<a href="/list/STAGE01/BONUS" class="btn btn-outline btn-primary">bonus</a>
							<a href="/list/STAGE01/FREESPIN" class="btn btn-outline btn-primary">freespin</a>
							<a href="/list/STAGE01/SIMULATOR" class="btn btn-outline btn-primary">simulator</a>
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