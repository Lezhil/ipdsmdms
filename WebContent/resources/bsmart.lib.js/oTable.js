
 var TableEditable1 = function () {
				 return{
			
					 oTable : function(){
						
						 var oTable = $('#sample_editable_1').dataTable({
				                "aLengthMenu": [
				                    [20, 50, 100, -1],
				                    [20, 50, 100, "All"] // change per page values here
				                ],
				                // set the initial value
				                "iDisplayLength": 20,
				                
				                "sPaginationType": "bootstrap",
				                "oLanguage": {
				                    "sLengthMenu": "_MENU_ records",
				                    "oPaginate": {
				                        "sPrevious": "Prev",
				                        "sNext": "Next"
				                    }
				                },
				                "aoColumnDefs": [{
				                        'bSortable': false,
				                        'aTargets': [0]
				                    }
				                ]
				            });
						  
					 }
					 
				 }

 }();
