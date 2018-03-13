
var AjaxUtils = {
		/**
		 * common ajax
		 */
		ajax : function(option){
			var newOption = this.getDefaultAjaxOption(option);
			return $.ajax(newOption);
		}
		,ajaxForm : function(myFormSel, option){
			var newOption = this.getDefaultAjaxOption(option);
			return $(myFormSel).ajaxSubmit(newOption);
		}
		,getDefaultAjaxOption : function(option) {
			return $.extend({
				type: "post"
				,data: {}
		        ,cache: false
		        ,error : function(_data) {
					console.log(_data);
					alert("오류가 발생 하였습니다.<br/>관리자에게 문의 하세요.");
				}
			}, option);
		}
		/**
		 * 파일 확장자 체크
		 */
		,acceptFileSuffix : function(fileName, acceptSuffixes) {
			if(fileName == '') {
				return true;
			}
			
			var suffix = fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);
			var accepts = acceptSuffixes.split(',');
			
			for( var i = 0; i < accepts.length; i++ ) {
				var accept = accepts[i].trim();
				if( suffix.toLowerCase() == accept.toLowerCase() ) {
					return true;
				}
			}
			return false;
		}
};



