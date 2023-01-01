(function($){
        $.fn.extend({
            tableExport: function(options) {
                var defaults = {
						separator: ',',
						ignoreColumn: [],
						tableName:'yourTableName',
						type:'csv',
						pdfFontSize:10,
						pdfLeftMargin:-30,
						escape:'true',
						htmlContent:'false',
						consoleLog:'false',
					    ownColWidth:'20',
					    fileName:'',
					    tableId:'',
					    divId:'',
					    condition:'default'
				};
                var options = $.extend(defaults, options);
	            //var doc = new jsPDF();	
	            var specialElementHandlers = {
	            		   '#editor': function (element, renderer) {
	            		       return true;
	            		   }
	            };
	   var table = tableToJson($('#'+defaults.tableId).get(0));
	  
	   var doc = new jsPDF('p','pt', 'a4', true);
	   doc.cellInitialize();	
	   doc.setFontSize(9);// by manja
	   if(defaults.condition=='custom')
		   {
		   doc.fromHTML(
				   $('#'+defaults.divId)[0], // HTML string or DOM elem ref.
	                0.5,    // x coord
	                0.5,    // y coord
	                {
	                    'width': 7.5 // max width of content on PDF
	                   // 'elementHandlers': specialElementHandlers//commented by manja
	                });
		   }
	   else
		   {
		   $.each(table, function (i, row){
	       console.debug(row);
	       $.each(row, function (j, cell){
	           doc.cell(0, 50,150, 20, cell, i);  // 2nd parameter=top margin,1st=left margin 3rd=row cell width 4th=Row height
	       })
	      })
	      
		   }
	   
       doc.output('save', defaults.fileName+'.pdf');	 
}});
    })(jQuery);
	function tableToJson(table) {
	var data = [];

	// first row needs to be headers
	var headers = [];
	for (var i=0; i<table.rows[0].cells.length; i++) {
	   headers[i] = table.rows[0].cells[i].innerHTML.toLowerCase().replace(/ /gi,'');
	}


	// go through cells
	for (var i=0; i<table.rows.length; i++) {

	   var tableRow = table.rows[i];
	   var rowData = {};

	   for (var j=0; j<tableRow.cells.length; j++) {

	       rowData[ headers[j] ] = tableRow.cells[j].innerHTML;

	   }

	   data.push(rowData);
	}       

	return data;
	}

