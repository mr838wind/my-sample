
var Utils = {
		/**
		 * common
		 */
		goDownload : function(dbPath, filename){
			var url = "/download?dbPath={dbPath}&filename={filename}"
				.replace('{dbPath}',dbPath)
				.replace('{filename}',filename);
			window.open(url, "_blank");
		}

};

var JsrenderHelper = {
		// <<DO NOT USE "this" in function body>>
		formatPercent: function(val) {
	    	var percent = val * 100;
	    	return JsrenderHelper.formatNumber(percent) + '%';
	    }
		,formatNumber: function(val) {
			return val.toLocaleString(undefined, {maximumFractionDigits:2}) // "1,234.57" 
		}
};

