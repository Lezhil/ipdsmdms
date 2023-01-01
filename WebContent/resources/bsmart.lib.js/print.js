 
		function printcontent(content)
		 {
		     var mywindow = window.open('', '', '');
		      mywindow.document.write('<html><title>Print</title><body><div style="background-color:silver;">');
		     mywindow.document.write(content);
		     mywindow.document.write('</div></body></html>');
		     mywindow.document.close(); 
		     mywindow.print();
		     return true;
		 }
 
