var Lock = function () {

    return {
        //main function to initiate the module
        init: function () {

             $.backstretch([
		        "resources/assets/img/bg/1.jpg",
		        "resources/assets/img/bg/2.jpg",
		        "resources/assets/img/bg/3.jpg",
		        "resources/assets/img/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		      });
        }

    };

}();