<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/inc-import.jsp"%>

<c:set var="stage" value="${param.stage}" />
<c:set var="type" value="${param.type}" />

<!-- custom -->
<style type="text/css">
	#runGameForm label.input-group-addon {
		background-color: #d9edf7;
	}
</style>

<script id="run-game-tmpl" type="text/x-jsrender">
{{if status == 'INIT' }}
		<form role="form"  id="runGameForm"  method="POST" action="" enctype="multipart/form-data"  onsubmit="return false;" >
			<div class="row">
				<div class="col-lg-3">
					<div class="input-group">
                      <label class="input-group-addon" >엑셀 파일 선택:</label>
                      <input type="file" name="excelFile" class="form-control" aria-describedby="basic-addon3" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
					</div>
				</div>
				<div class="col-lg-3">
					<div class="input-group">
                      <label class="input-group-addon">사용자 이름:</label>
                      <input type="text" name="username" class="form-control" value="" />
					</div>
				</div>
				<div class="col-lg-1">
					<div class="input-group">
						<button type="button" name="btnRun" onclick="runBtnOnClick();" class="form-control btn btn-outline btn-primary" >실행</button>
					</div>
				</div>
			</div>
		</form>
{{else status == 'RUNNING' }}
	progress: {{:~formatPercent(doneTask/totalTask)}} ---- ( {{:~formatNumber(doneTask)}} / {{:~formatNumber(totalTask)}} ) 
{{else}}
	실행 완료
{{/if}}
</script>

<script>
	/* ============ RunGameComponent ============ */
	function RunGameComponent(id, stage, type) {
		this.id = id;
		this.stage = stage;
		this.type = type;
		//
		this.objBody = $(this.id);
		//
		this.progress = null;
		//
		this.pollingTimerInteval = 1*1000; // 1s
		this.pollingTimer = null;
	}
	
	RunGameComponent.prototype.fetchProgress = function(callbackFunc) {
		AjaxUtils.ajax({
			url: '/ajax/run/{stage}/{type}/fetchProgress'
				.replace('{stage}', this.stage)
				.replace('{type}', this.type)
			,data:{}
	        ,success : function(_data){
	        	console.log('>> fetchProgress');
		    	var progress = _data.progress;
		    	if(typeof callbackFunc != 'undefined') {
		    		callbackFunc(progress);
		    	}
			}
		});
	}
	
	RunGameComponent.prototype.setProgress = function(progress) {
		this.progress = progress;
		console.log(this.progress);
		
		this.renderUI(this.progress);
		
		if(this.progress.status == 'RUNNING') {
			this.startPolling();
		} else if(this.progress.status == 'COMPLETE') {
			this.stopPolling();
			this.reset();
		}
	}
	
	RunGameComponent.prototype.renderUI = function(progress) {
		var html = $.templates('#run-game-tmpl').render(progress, JsrenderHelper);
		this.objBody.html(html);
	}
	
	RunGameComponent.prototype.runSimulator = function() {
		var me = this;
		if(!runGameComponentValidate(me.id)) {
			return;
		}
		
		disableRunGameComponent(me.id);
		
		AjaxUtils.ajaxForm('#runGameForm', {
			url: '/ajax/run/{stage}/{type}/runSimulator'
				.replace('{stage}', this.stage)
				.replace('{type}', this.type)
			,data: $('#runGameForm').serialize()
	        ,success : function(_data){
		    	console.log('>> runSimulator success');
		    	me.startPolling();
			}
		});
	}
	
	RunGameComponent.prototype.startPolling = function() {
		var me = this;
		if(me.pollingTimer == null) {
			me.pollingTimer = setInterval(function(){
				me.fetchProgress(function(progress){
					me.setProgress(progress);
				});
			}, me.pollingTimerInteval);
		} else {
			console.debug(' pollingTimer already started. ');
		}
	}
	
	RunGameComponent.prototype.stopPolling = function() {
		var me = this;
		if(me.pollingTimer != null) {
			clearInterval(me.pollingTimer);
			me.pollingTimer = null;
		} else {
			console.debug(' pollingTimer already stopped. ');
		}
	}
	
	RunGameComponent.prototype.reset = function() {
		console.log('>> reset');
		
		this.renderUI({
			status: 'COMPLETE'
			,doneTask: 0
			,totalTask: 1
		});
		
		// set status  
		AjaxUtils.ajax({
			url: '/ajax/run/{stage}/{type}/reset'
				.replace('{stage}', this.stage)
				.replace('{type}', this.type)
			,data:{}
	        ,success : function(_data){
		    	// page reload
				window.location.reload();
			}
		});
	}
	
	function disableRunGameComponent(id) {
		var myForm = $(id);
		myForm.find(':input').not('.btn').prop('readonly',true);
		myForm.find('.btn').prop('disabled',true);
	}
	
	function enableRunGameComponent(id) {
		var myForm = $(id);
		myForm.find(':input').not('.btn').prop('readonly',false);
		myForm.find('.btn').prop('disabled',false);
	}
	
	function runGameComponentValidate(id) {
		var myForm = $(id);
		var excelFile = myForm.find('[name="excelFile"]');
		if( excelFile.val() == '' ) {
			alert('엑셀 파일 선택하세요.');
			return false;
		}
		
		var username = myForm.find('[name="username"]');
		if( username.val() == '' ) {
			alert('username 입력하세요.');
			return false;
		}
		return true;
	}
	
	/* ============ ]]RunGameComponent ============ */
	
	
	
	/* ============ 실행 ============ */
	var runGameComponent = null;
	
	$(function(){
		runGameComponent = new RunGameComponent('#runGameComponent', '${stage}', '${type}');
		runGameComponent.fetchProgress(function(progress){
			runGameComponent.setProgress(progress);
		});
		
	});
	
	function runBtnOnClick() {
		runGameComponent.runSimulator();
	}
	
	
	
</script>