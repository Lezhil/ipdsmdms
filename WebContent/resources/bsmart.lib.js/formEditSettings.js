 
	    $.mockjaxSettings.responseTime = 500;

	    var log = function (settings, response) {}
	    var initAjaxMock = function () {
	         $.mockjax({
	            url: '/post',
	            response: function (settings) {
	                log(settings, this);
	            }
	        });
 
	    }