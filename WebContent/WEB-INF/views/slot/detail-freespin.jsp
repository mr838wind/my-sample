<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/inc-import.jsp"%>

<!DOCTYPE html>
<html lang="ko">
  <head>
  	<title>vSlot-Simulator</title>
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
	                <h1 class="page-header">DETAIL: ${stage} / ${type} / ${seq}</h1>
	            </div>
	            <!-- /.col-lg-12 -->
	        </div>
	        
	        <jsp:include page="../common/inc-back-button.jsp" />
        
        	<!-- contents -->
        	<div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-info">
                        <div class="panel-heading">
							결과
                        </div>
                        <div class="panel-body">
                               <div class="row">
                                   <label class="col col-lg-3">seq</label>
                                   <div class="col col-lg-3 text-right">${result.seq}</div>
                                   <label class="col col-lg-3" >stage</label>
                                   <div class="col col-lg-3" >${result.stage}</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-3">시간</label>
                                   <div class="col col-lg-3 ">${func:formatDateTime(result.registerTime)}</div>
                                   <label class="col col-lg-3" >사용자</label>
                                   <div class="col col-lg-3" >${result.username}</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-3">실행 횟수</label>
                                   <div class="col col-lg-3  text-right">${func:formatNumber(result.playGameCount)}</div>
                                   <label class="col col-lg-3" >배팅금액</label>
                                   <div class="col col-lg-3 text-right" >${func:formatNumber(result.totalBet)}</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-3" >지급액</label>
                                   <div class="col col-lg-3 text-right" >${func:formatNumber(result.payout)}</div>
                                   <label class="col col-lg-3" >freespin 추가수</label>
                                   <div class="col col-lg-3 text-right" >${func:formatNumber(result.freespinScatterCountTotal)}</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-3">지급률(%)</label>
                                   <div class="col col-lg-3  text-right">${func:formatPercent(result.payoutRate)}</div>
                                   <label class="col col-lg-3" >승률(%)</label>
                                   <div class="col col-lg-3 text-right" >${func:formatPercent(result.winGameRate)}</div>
                               </div>
                               
                        </div>
                    </div>
                </div>
	        </div>
        	
        	
        	
        	<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
							입력
                        </div>
                        <c:set var="input" value="${result.inputVO}" />
                        <div class="panel-body">
                               <div class="row">
                                   <label class="col col-lg-2">엑셀 다운로드</label>
                                   <div class="col col-lg-4">
                                   		<a href="javascript:Utils.goDownload('${input.dbPath}','${input.filename}');">${input.filename} &nbsp;</a>
                                   </div>
                                   <label class="col col-lg-2" >&nbsp;</label>
                                   <div class="col col-lg-4" >&nbsp;</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-2">seq</label>
                                   <div class="col col-lg-4 text-right">${input.seq}</div>
                                   <label class="col col-lg-2" >stage</label>
                                   <div class="col col-lg-4" >${input.stage}</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-2">시간</label>
                                   <div class="col col-lg-4 ">${func:formatDateTime(input.registerTime)}</div>
                               </div>
                               
                               <div class="row">
                                   <label class="col col-lg-2" >json 데이터</label>
                                   <div class="col col-lg-10" > ${fn:replace(input.jsonData, CR, BR)}  </div>
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